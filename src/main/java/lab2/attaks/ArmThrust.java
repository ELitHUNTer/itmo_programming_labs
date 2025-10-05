package lab2.attaks;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Type;

import java.util.Random;

public final class ArmThrust extends PhysicalMove {

    public ArmThrust(){
        this.type = Type.NORMAL;
        this.power = 15;
        this.accuracy = 100;
        this.hits = new Random().nextInt(2, 6);
    }

    @Override
    protected String describe() {
        return "использует ArmThrust";
    }
}
