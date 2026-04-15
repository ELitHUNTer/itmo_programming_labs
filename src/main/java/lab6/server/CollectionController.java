package lab6.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lab6.server.collection_items.SpaceMarine;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CollectionController implements Cloneable {

    private Stack<SpaceMarine> stack;
    private LocalDateTime initializationTime;
    private Gson gson;

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
                .registerTypeAdapter(SpaceMarine.class, new SpaceMarine.Adapter()).create();
    }

    public CollectionController(){
        stack = new Stack<>();
        readCollection();
        initializationTime = LocalDateTime.now();
    }

    private CollectionController(CollectionController copy){
        stack = (Stack<SpaceMarine>) copy.stack.clone();
        initializationTime = copy.initializationTime;
    }

    public boolean containsElementWithId(int id){
        return getCollectionElements().stream().filter(x -> x.getID() == id).count() == 1;
    }

    /**
     * reads collection from file collection.json if present
     */
    private void readCollection(){
        String fileName = System.getenv("FILE_PROG_LAB_5");
        if (fileName == null) {
            IOHelper.errOut.println("Please provide file name via FILE_PROG_LAB_5 environment variable");
            System.exit(0);
        }
        File collectionFile = new File(fileName);
        if (collectionFile.exists()) {
            Type type = new TypeToken<List<SpaceMarine>>(){}.getType();
            try {
                InputStreamReader reader = new InputStreamReader(new FileInputStream(collectionFile));
                List<SpaceMarine> list = gson.fromJson(reader, type);
                if (list == null) return;
                for (var x : list)
                    stack.push(x);
            } catch (FileNotFoundException e) {
            }
        }
    }

    /**
     * Summarizes info about current collection. Same as toString()
     * @return String equivalent of current collection
     */
    public String getCollectionInfo(){
        return toString();
    }

    /**
     *
     * @return List of current elements in collection
     */
    public List<SpaceMarine> getCollectionElements(){
        return stack.stream().toList();
    }

    /**
     * add element to collection
     * @param x element to add
     */
    public void addElement(SpaceMarine x){
        stack.addElement(x);
    }

    /**
     * updates element in collection by its inner id value
     * @param id SpaceMarine class field to identify what element to update
     * @param element new element value
     */
    public void updateElement(int id, SpaceMarine element){
        for (int i = 0; i < stack.size(); i++)
            if (stack.get(i).getID() == id) {
                stack.get(i).update(element);
                break;
            }
    }

    /**
     * removes element from collection by its inner id value
     * @param id SpaceMarine class field to identify what element to remove
     */
    public void removeById(int id){
        stack.stream()
                .filter(x -> x.getID().equals(id))
                .findFirst()
                .ifPresent(spaceMarine -> stack.remove(spaceMarine));
    }

    /**
     * removes all elements from collection
     */
    public void clear(){
        stack.clear();
    }

    /**
     * saves collection to file
     * @param fileName path to file for save
     */
    public void save(String fileName){
        List<SpaceMarine> a = new ArrayList<>(stack);
        String toSave = gson.toJson(a);
        try (FileWriter fw = new FileWriter(fileName)){
            fw.write(toSave);
        } catch (IOException e) {
            System.err.println("Error occurred when saving to file");
        }
    }

    /**
     * saves collection to file collectin.json
     */
    public void save(){
        save("collection.json");
    }

    /**
     * inserts element into collection at position
     * @param id element position
     * @param element element to insert
     */
    public void insertAt(int id, SpaceMarine element){
        stack.insertElementAt(element, id);
    }

    /**
     * removes last element from collection
     */
    public SpaceMarine remove_last(){
        return stack.pop();
    }

    /**
     * removes all elements from collection, that are greater than pivot element
     * @param element pivot to compare
     */
    public void removeGreater(SpaceMarine element){
        List<SpaceMarine> list = stack.stream().filter(x -> x.compareTo(element) > 0).toList();
        stack.removeAll(list);
    }

    /**
     *
     * @return current number of elements in collection
     */
    public int getCollectionSize(){
        return stack.size();
    }

    /**
     *
     * @return copy of current object
     */
    @Override
    public Object clone(){
        return new CollectionController(this);
    }

    /**
     * replaces elements in current collection with elements from new collection
     * @param other new elements for collection
     */
    public void updateCollection(CollectionController other){
        stack = (Stack<SpaceMarine>) other.stack.clone();
    }

    /**
     * Summarizes info about current collection. Same as getCollectionInfo()
     * @return String equivalent of current collection
     */
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
