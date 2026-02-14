package lab3.container;

import java.util.*;
import java.util.function.Consumer;

public class DefaultContainer implements Container, Iterable<Containable>{

    private List<Containable> items;

    public DefaultContainer(){
        items = new LinkedList<>();
    }

    @Override
    public void put(Containable item) {
        items.add(item);
    }

    @Override
    public boolean take(Containable item) {
        return items.remove(item);
    }

    @Override
    public int totalWeight() {
        int sum = 0;
        for (Containable item : items)
            sum += item.getWeight();
        return sum;
    }

    @Override
    public Iterator<Containable> iterator() {
        return items.iterator();
    }

    @Override
    public void forEach(Consumer<? super Containable> action) {
        items.forEach(action);
    }

    @Override
    public Spliterator<Containable> spliterator() {
        return items.spliterator();
    }
}
