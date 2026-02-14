package lab3.transport;

import lab3.container.Containable;
import lab3.container.DefaultContainer;
import lab3.entities.Person;
import lab3.entities.Profession;
import lab3.entities.Worker;
import lab3.enviroment.Base;
import lab3.enviroment.ExplorableLocation;
import lab3.enviroment.Location;
import lab3.exceptions.InsufficientFuelException;

import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Transport {

    private int currentFuel, maxFuel, maxWeight, selfWeight, maxSpeed;
    private List<Person> crew;
    private Worker pilot = null;
    private DefaultContainer cargo;
    protected Location currentLocation;

    public Transport(int maxFuel, int maxWeight, int selfWeight, int maxSpeed, Location currentLocation) {
        this.maxWeight = maxWeight;
        this.maxSpeed = maxSpeed;
        this.selfWeight = selfWeight;
        this.maxFuel = maxFuel;
        this.currentFuel = maxFuel;
        this.currentLocation = currentLocation;
        crew = new ArrayList<>();
        cargo = new DefaultContainer();
    }

    public Worker getPilot() {
        return pilot;
    }

    public void setPilot(Worker pilot) {
        this.pilot = pilot;
    }

    public Period moveTo(Location location) {
        if (!canMove())
            throw new IllegalStateException("Transport can move only if the pilot is present");
        double distance = currentLocation.distance(location);
        double fuelConsumptionModifier = pilot.hasProfession(Profession.PILOT) ? 0.7 : 1.0;
        if (currentFuel < distance * fuelConsumptionModifier)
            throw new InsufficientFuelException(
                    String.format(
                    "Insufficient fuel %d, when moving from (%d, %d) to (%d, %d)",
                    currentFuel,
                    currentLocation.getCoordinates().getX(),
                    currentLocation.getCoordinates().getY(),
                    location.getCoordinates().getX(),
                    location.getCoordinates().getY()
            ));
        currentLocation = location;
        currentFuel -= (int)(distance * fuelConsumptionModifier);
        int days = (int) (distance / maxSpeed), years = days / 365, month = (days / 12) % 365;
        return Period.of(years, month, days);
    }

    protected boolean canMove(){
        return pilot != null;
    }

    public void addCargo(Containable... cargo) {
        for (Containable item : cargo)
            this.cargo.put(item);
    }

    public boolean canAddCargo(Containable item) {
        return maxWeight >= item.getWeight() + cargo.totalWeight();
    }

    public void addCrew(Person... crew) {
        this.crew.addAll(Arrays.asList(crew));
    }

    public void removeCargo(Containable... cargo) {
        for (Containable item : cargo)
            this.cargo.take(item);
    }

    public void unloadAllCargo() {
        if (currentLocation instanceof Base base)
            for (Containable item : cargo) {
                base.addToWarehouse(item);
                cargo.take(item);
            }
    }

    public void removeCrew(Person... crew) {
        this.crew.removeAll(Arrays.asList(crew));
    }

    public int getCurrentFuel() {
        return currentFuel;
    }

    public int getMaxFuel() {
        return maxFuel;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public List<Person> getCrew() {
        return crew;
    }

    public DefaultContainer getCargo() {
        return cargo;
    }
}
