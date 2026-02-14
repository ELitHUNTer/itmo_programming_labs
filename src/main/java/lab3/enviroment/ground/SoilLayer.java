package lab3.enviroment.ground;

import lab3.enviroment.ground.fossils.Fossil;

import java.util.Collections;
import java.util.List;

public class SoilLayer {

    private Type soilType;
    private int depth;
    private List<Fossil> content;

    public SoilLayer(Type soilType, int depth, List<Fossil> content){
        this.soilType = soilType;
        this.depth = depth;
        this.content = content;
    }

    public Type getType(){
        return soilType;
    }

    public int getDepth(){
        return depth;
    }

    public List<Fossil> mine(int strength){
        if (strength >= soilType.toughness)
            return content;
        return Collections.emptyList();
    }

    public enum Type{
        ICE(10),
        CLAY(15),
        DIRT(30),
        ROCK(100);

        public final int toughness;

        private Type(int toughness){
            this.toughness = toughness;
        }
    }

}
