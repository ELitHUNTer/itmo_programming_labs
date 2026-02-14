package lab3;

import lab3.entities.Worker;
import lab3.enviroment.ExplorableLocation;
import lab3.transport.Transport;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Expedition {

    private LocalDate startTime, currentTime;
    private Optional<String> name;
    private int expeditionId;
    private List<ExplorableLocation> locations;
    private List<Worker> stuff;
    private List<Transport> transport;

    private Expedition(LocalDate startTime,
                       LocalDate currentTime,
                       Optional<String> name,
                       int expeditionId,
                       List<ExplorableLocation> locations,
                       List<Worker> stuff,
                       List<Transport> transport){
        if (startTime == null)
            throw new IllegalArgumentException("Expedition must have a start time");
        if (stuff.isEmpty())
            throw new IllegalArgumentException("Expedition must have at least 1 crew member");
        if (locations.isEmpty())
            throw new IllegalArgumentException("Expedition must have at least 1 explorable location");

        this.startTime = startTime;
        this.currentTime = currentTime;
        this.name = name;
        this.expeditionId = expeditionId;
        this.locations = locations;
        this.stuff = stuff;
        this.transport = transport;
    }

    public int getId() {
        return expeditionId;
    }

    public void startExpedition(){
        for (ExplorableLocation location : locations){
            Period mx = transport.stream().map(x -> x.moveTo(location)).max(
                    (a, b) -> {
                        if (a.getDays() != b.getDays())
                            return a.getDays() - b.getDays();
                        if (a.getMonths() != b.getMonths())
                            return a.getMonths() - b.getMonths();
                        else
                            return a.getDays() - b.getDays();
                    }
            ).orElse(Period.of(0, 0, 0));
            currentTime = currentTime.plus(mx);
            for (ExpeditionRunnable runnable : location.getDoBeforeExploration())
                runnable.run(this);
            location.explore(this);
            for (ExpeditionRunnable runnable : location.getDoAfterExploration())
                runnable.run(this);
        }
    }

    public Optional<String> getName(){
        return name;
    }

    public List<Worker> getStuff() {
        return stuff;
    }

    public List<Transport> getTransport() {
        return transport;
    }

    public static class Builder{
        private LocalDate startTime, currentTime;
        private Optional<String> name;
        private int expeditionId;
        private List<ExplorableLocation> locations;
        private List<Worker> stuff;
        private List<Transport> transport;

        public Builder(){
            this.startTime = null;
            this.currentTime = null;
            this.name = Optional.empty();
            this.expeditionId = ThreadLocalRandom.current().nextInt();
            this.locations = Collections.emptyList();
            this.stuff = Collections.emptyList();
            this.transport = Collections.emptyList();
        }

        public Builder setStartTime(LocalDate startTime) {
            this.startTime = startTime;
            if (currentTime == null)
                this.currentTime = startTime;
            return this;
        }

        public Builder setCurrentTime(LocalDate currentTime) {
            this.currentTime = currentTime;
            return this;
        }

        public Builder setName(String name) {
            this.name = Optional.of(name);
            return this;
        }

        public Builder setExpeditionId(int expeditionId) {
            this.expeditionId = expeditionId;
            return this;
        }

        public Builder setLocations(ExplorableLocation... locations) {
            this.locations = List.of(locations);
            return this;
        }

        public Builder setStuff(Worker... stuff) {
            this.stuff = List.of(stuff);
            return this;
        }

        public Builder setTransport(Transport... transport) {
            this.transport = List.of(transport);
            return this;
        }

        public Expedition build(){
            return new Expedition(startTime, currentTime, name, expeditionId, locations, stuff, transport);
        }
    }
}
