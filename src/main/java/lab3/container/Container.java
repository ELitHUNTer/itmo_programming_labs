package lab3.container;

public interface Container {
    void put(Containable item);
    boolean take(Containable item);
    int totalWeight();
}
