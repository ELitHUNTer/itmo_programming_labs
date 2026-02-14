package lab3.enviroment;

import lab3.Expedition;
import lab3.ExpeditionRunnable;
import lab3.GeologistMachinery;
import lab3.container.Containable;
import lab3.container.Container;
import lab3.entities.Profession;
import lab3.entities.Worker;
import lab3.enviroment.ground.Soil;
import lab3.enviroment.ground.SoilLayer;
import lab3.enviroment.ground.fossils.Fossil;
import lab3.transport.Transport;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class ExplorableLocation extends Location {

    private Soil soil;
    private List<ExpeditionRunnable> doBeforeExploration;
    private List<ExpeditionRunnable> doAfterExploration;

    public ExplorableLocation(Coordinates coordinates, Weather weather, Soil soil){
        super(coordinates, weather);
        this.soil = soil;
        this.doBeforeExploration = Collections.emptyList();
        this.doAfterExploration = Collections.emptyList();
    }

    public void setActionsBeforeExploration(ExpeditionRunnable... actions){
        this.doBeforeExploration = List.of(actions);
    }

    public void setActionsAfterExploration(ExpeditionRunnable... actions){
        this.doAfterExploration = List.of(actions);
    }

    public List<ExpeditionRunnable> getDoBeforeExploration() {
        return doBeforeExploration;
    }

    public List<ExpeditionRunnable> getDoAfterExploration() {
        return doAfterExploration;
    }

    public Soil getSoil() {
        return soil;
    }

    public void explore(Expedition expedition){
        Optional<Worker> geologist = getGeologist(expedition.getStuff());
        Worker miner = geologist.orElse(expedition.getStuff().get(0));
        Optional<GeologistMachinery> machinery = getMachinery(expedition.getTransport());
        machinery.ifPresent(geologistMachinery -> geologistMachinery.assignWorker(miner));
        for (SoilLayer layer : soil.getLayers()){
            List<Fossil> fossils;
            if (machinery.isPresent())
                fossils = machinery.get().apply(layer);
            else
                fossils = layer.mine(15);
            storeFossils(expedition.getTransport(), fossils);
        }
    }

    private Optional<Worker> getGeologist(List<Worker> workers){
        for (Worker w : workers){
            if (w.hasProfession(Profession.GEOLOGIST))
                return Optional.of(w);
        }
        return Optional.empty();
    }

    private Optional<GeologistMachinery> getMachinery(List<Transport> transports){
        for (Transport transport : transports)
            for (Containable item : transport.getCargo())
                if (item instanceof GeologistMachinery machine){
                    return Optional.of(machine);
                }
        return Optional.empty();
    }

    private void storeFossils(List<Transport> transports, List<Fossil> fossils){
        Iterator<Transport> transportIterator = transports.iterator();
        Transport transport = transportIterator.next();
        for (Fossil fossil : fossils){
            while (!transport.canAddCargo(fossil) && transportIterator.hasNext())
                transport = transportIterator.next();
            if (transport.canAddCargo(fossil))
                transport.addCargo(fossil);
        }
    }
}
