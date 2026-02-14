package lab3.transport;

import lab3.entities.Dog;
import lab3.enviroment.Location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Ledges extends Transport{

    private List<Dog> dogs;

    public Ledges(int maxFuel, int maxWeight, int selfWeight, int maxSpeed, Location currentLocation) {
        super(maxFuel, maxWeight, selfWeight, maxSpeed, currentLocation);
        dogs = new ArrayList<>();
    }

    public void addDogs(Dog... dogs){
        this.dogs.addAll(Arrays.asList(dogs));
    }

    public void removeDogs(Dog... dogs){
        this.dogs.removeAll(Arrays.asList(dogs));
    }

    @Override
    protected boolean canMove() {
        return super.canMove() && dogs.stream().filter(Dog::isAlive).count() == 0;
    }
}
