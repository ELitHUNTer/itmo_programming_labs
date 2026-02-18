package lab5;

import lab5.collection_items.Chapter;
import lab5.collection_items.SpaceMarine;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class CollectionController {

    private Stack<SpaceMarine> stack;
    private LocalDateTime initializationTime;

    public CollectionController(){
        stack = new Stack<>();
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

    public void remove(int id){
        stack.remove(id);
    }

    public void clear(){
        stack.clear();
    }

    public void save(String fileName){
        // TODO
    }

    public void save(){
        save("output.txt");
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
