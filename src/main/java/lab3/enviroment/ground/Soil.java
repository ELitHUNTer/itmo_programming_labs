package lab3.enviroment.ground;

import java.util.Collections;
import java.util.List;

public class Soil {

    private List<SoilLayer> layers;

    private Soil(SoilLayer... layers){
        this.layers = List.of(layers);
    }

    public List<SoilLayer> getLayers(){
        return layers;
    }

    public static Soil of(SoilLayer... layers){
        return new Soil(layers);
    }
}
