package lab3.enviroment;

import java.util.List;

public class SoilLayer {

    private Type soilType;
    private int width;
    private List<Fossil> content;


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
