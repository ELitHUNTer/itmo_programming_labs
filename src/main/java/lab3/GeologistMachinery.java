package lab3;

import lab3.entities.Profession;
import lab3.entities.Worker;
import lab3.enviroment.ground.SoilLayer;
import lab3.enviroment.ground.fossils.Fossil;

import java.util.List;

public class GeologistMachinery {

    private int force;
    private Worker worker;

    public GeologistMachinery(int force){
        this.force = force;
        worker = null;
    }

    public void assignWorker(Worker worker){
        this.worker = worker;
    }

    public List<Fossil> apply(SoilLayer layer){
        double modifier = 1;
        if (worker.hasProfession(Profession.GEOLOGIST))
            modifier = 1.5;
        return layer.mine((int) (force * modifier));
    }

}
