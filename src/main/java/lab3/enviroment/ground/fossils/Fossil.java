package lab3.enviroment.ground.fossils;

import lab3.container.Containable;

public class Fossil implements Containable {

    private int volume;

    public Fossil(int volume){
        this.volume = volume;
    }

    @Override
    public int getWeight() {
        return volume;
    }
}
