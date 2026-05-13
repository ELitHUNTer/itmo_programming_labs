package lab7.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lab7.collectionItems.SpaceMarine;
import lab7.collectionItems.utils.IdGenerator;
import lab7.collectionItems.utils.SpaceMarineAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class CollectionController implements Cloneable {

    private List<SpaceMarine> stack;
    private LocalDateTime initializationTime;
    private Gson gson;
    private Logger logger = LoggerFactory.getLogger(CollectionController.class);
    private final Object monitor = new Object();
    private final DataBaseManager dbManager;

    {
        gson = new GsonBuilder()
                .registerTypeAdapter(
                        LocalDate.class,
                        new TypeAdapter<LocalDate>(){

                            @Override
                            public void write(JsonWriter jsonWriter, LocalDate localDate) throws IOException {
                                jsonWriter.value(localDate.toString());
                            }

                            @Override
                            public LocalDate read(JsonReader jsonReader) throws IOException {
                                return LocalDate.parse(jsonReader.nextString());
                            }
                        }
                )
                .registerTypeAdapter(SpaceMarine.class, new SpaceMarineAdapter(IdGenerator.getInstance())).create();
    }

    public CollectionController(DataBaseManager dbManager){
        this.dbManager = dbManager;
        stack = Collections.synchronizedList(new Stack<>());
        readCollection();
        initializationTime = LocalDateTime.now();
    }

    private CollectionController(CollectionController copy){
        List<SpaceMarine> snapshot;
        synchronized (copy.monitor) {
            snapshot = new ArrayList<>(copy.stack);
        }
        this.stack = Collections.synchronizedList(new Stack<>());
        this.stack.addAll(snapshot);
        this.initializationTime = copy.initializationTime;
        this.gson = copy.gson;
        this.logger = copy.logger;
        this.dbManager = copy.dbManager;
    }

    public boolean containsElementWithId(int id){
        synchronized (monitor) {
            return stack.stream().filter(x -> x.getID() == id).count() == 1;
        }
    }

    /**
     * Загружает коллекцию из БД при старте программы.
     * После загрузки синхронизирует IdGenerator с максимальным id в БД,
     * чтобы новые объекты получали уникальные id.
     */
    private void readCollection(){
        List<SpaceMarine> marines = dbManager.loadAllSpaceMarines();
        synchronized (monitor) {
            stack.addAll(marines);
        }
        int maxId = dbManager.getMaxSpaceMarineId();
        IdGenerator.getInstance().setCurrentId(maxId);
        dbManager.syncIdSequence();
        logger.info("Коллекция загружена из БД: {} элементов, max_id={}.", marines.size(), maxId);
    }

    public String getCollectionInfo(){
        return toString();
    }

    /**
     * @return defensive copy (снимок) текущих элементов. Клиент может безопасно итерировать его без блокировок.
     */
    public List<SpaceMarine> getCollectionElements(){
        synchronized (monitor) {
            return new ArrayList<>(stack);
        }
    }

    /**
     * Добавить элемент: сначала в БД, при успехе — в память.
     */
    public void addElement(SpaceMarine x){
        if (dbManager.insertSpaceMarine(x)) {
            stack.add(x);
        } else {
            throw new RuntimeException("Не удалось добавить SpaceMarine в БД. Изменения в памяти отменены.");
        }
    }

    /**
     * Обновить элемент: сначала в БД, при успехе — в памяти.
     */
    public void updateElement(int id, SpaceMarine element){
        synchronized (monitor) {
            if (dbManager.updateSpaceMarine(element)) {
                for (int i = 0; i < stack.size(); i++) {
                    if (stack.get(i).getID() == id) {
                        stack.get(i).update(element);
                        break;
                    }
                }
            } else {
                throw new RuntimeException("Не удалось обновить SpaceMarine id=" + id + " в БД.");
            }
        }
    }

    /**
     * Удалить по id: сначала из БД, при успехе — из памяти.
     */
    public void removeById(int id){
        synchronized (monitor) {
            if (dbManager.deleteSpaceMarineById(id)) {
                stack.stream()
                        .filter(x -> x.getID().equals(id))
                        .findFirst()
                        .ifPresent(spaceMarine -> stack.remove(spaceMarine));
            } else {
                throw new RuntimeException("Не удалось удалить SpaceMarine id=" + id + " из БД.");
            }
        }
    }

    /**
     * Очистить коллекцию: сначала удалить все из БД, затем очистить память.
     */
    public void clear(){
        dbManager.deleteAllSpaceMarines();
        stack.clear();
    }

    public void save(String fileName){
        logger.info("save() вызван. При использовании БД коллекция сохраняется автоматически.");
    }

    public void save(){
        save(null);
    }

    /**
     * Вставить элемент на позицию index: сначала сдвинуть ordernum в БД,
     * затем вставить запись. При успехе — добавить в память.
     */
    public void insertAt(int index, SpaceMarine element){
        synchronized (monitor) {
            if (dbManager.insertSpaceMarine(element)) {
                stack.add(index, element);
            } else {
                throw new RuntimeException("Не удалось вставить SpaceMarine в БД на позицию " + index);
            }
        }
    }

    /**
     * Удалить последний элемент (pop): сначала из БД, при успехе — из памяти.
     */
    public SpaceMarine remove_last(){
        synchronized (monitor) {
            if (stack.isEmpty()) {
                throw new EmptyStackException();
            }
            SpaceMarine last = stack.get(stack.size() - 1);
            int deletedId = dbManager.deleteLastSpaceMarine();
            if (deletedId == last.getID()) {
                stack.remove(stack.size() - 1);
                return last;
            } else {
                throw new RuntimeException("Не удалось удалить последний элемент из БД.");
            }
        }
    }

    /**
     * Удалить все элементы, большие переданного (сравнение по id).
     * Сначала массовое удаление в БД, при успехе — удаление из памяти.
     */
    public void removeGreater(SpaceMarine element){
        synchronized (monitor) {
            int deleted = dbManager.deleteSpaceMarinesWithIdGreaterThan(element.getID());
            if (deleted >= 0) {
                List<SpaceMarine> toRemove = stack.stream()
                        .filter(x -> x.compareTo(element) > 0)
                        .toList();
                stack.removeAll(toRemove);
            } else {
                throw new RuntimeException("Не удалось удалить элементы из БД (removeGreater).");
            }
        }
    }

    public int getCollectionSize(){
        return stack.size();
    }

    @Override
    public Object clone(){
        return new CollectionController(this);
    }

    public void updateCollection(CollectionController other){
        List<SpaceMarine> snapshot;
        synchronized (other.monitor) {
            snapshot = new ArrayList<>(other.stack);
        }
        synchronized (this.monitor) {
            this.stack.clear();
            this.stack.addAll(snapshot);
        }
    }

    @Override
    public String toString() {
        return String.format("Type: %s%n" +
                        "Created at: %s%n" +
                        "Element count: %d",
                Stack.class,
                initializationTime.toString(),
                stack.size()
        );
    }
}