package lab3.enviroment;

import lab3.container.Containable;
import lab3.container.DefaultContainer;
import lab3.entities.ShortWaveRadio;
import lab3.entities.Worker;
import lab3.transport.Transport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Base extends Location{

    private DefaultContainer warehouse;
    private List<Worker> crew;
    private List<Transport> transport;
    private ShortWaveRadio radio;

    public Base(Coordinates coordinates, Weather weather) {
        super(coordinates, weather);
        crew = new ArrayList<>();
        transport = new ArrayList<>();
        radio = ShortWaveRadio.getDefault();
    }

    public void addCrewMember(Worker... workers){
        crew.addAll(Arrays.asList(workers));
        for (Worker w : workers)
            radio.addReceivers(w);
    }

    public ShortWaveRadio getRadio(){
        return radio;
    }

    public void addTransport(Transport... transports){
        transport.addAll(Arrays.asList(transports));
    }

    public void removeCrewMember(Worker... workers){
        crew.removeAll(Arrays.asList(workers));
    }

    public void removeTransport(Transport... transports){
        transport.removeAll(Arrays.asList(transports));
    }

    public void addToWarehouse(Containable... items){
        Arrays.stream(items).forEach(x -> warehouse.put(x));
    }
}
