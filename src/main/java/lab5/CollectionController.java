package lab5;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import lab5.collection_items.SpaceMarine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

public class CollectionController {

    private Stack<SpaceMarine> stack;
    private LocalDateTime initializationTime;
    Gson gson = new GsonBuilder()
            .setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return f.getAnnotation(Expose.class) != null;
                }

                @Override
                public boolean shouldSkipClass(Class<?> c) {
                    return false;
                }
            })
            .create();

    public CollectionController(){
        SpaceMarine[] list = gson.fromJson("output.json", SpaceMarine[].class);
        stack = new Stack<>();
        for (var x: list)
            stack.push(x);
        initializationTime = LocalDateTime.now();
    }

    public String getCollectionInfo(){
        return toString();
    }

    public List<SpaceMarine> getCollectionElements(){
        return stack.stream().toList();
    }

    public void addElement(SpaceMarine x){
        stack.addElement(x);
    }

    public void updateElement(int id, SpaceMarine element){
        stack.set(id, element);
    }

    public void removeById(int id){
        stack.stream()
                .filter(x -> x.getID().equals(id))
                .findFirst()
                .ifPresent(spaceMarine -> stack.remove(spaceMarine));
    }

    public void clear(){
        stack.clear();
    }

    public void save(String fileName){

        List<SpaceMarine> a = new ArrayList<>(stack);
        String toSave = gson.toJson(a);
        try (FileWriter fw = new FileWriter(fileName)){
            fw.write(toSave);
        } catch (IOException e) {
            System.err.println("Error occurred when saving to file");
        }
    }

    public void save(){
        save("output.json");
    }

    public void insertAt(int id, SpaceMarine element){
        stack.insertElementAt(element, id);
    }

    public void remove_last(){
        stack.pop();
    }

    public void removeGreater(SpaceMarine element){
        stack.removeAll(stack.stream().filter(x -> x.compareTo(element) > 0).toList());
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
