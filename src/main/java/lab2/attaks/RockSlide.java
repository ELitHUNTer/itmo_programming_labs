package lab2.attaks;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Type;

public final class RockSlide extends PhysicalMove {

    public RockSlide(){
        this.type = Type.ROCK;
        this.power = 75;
        this.accuracy = 90;
    }

    @Override
    protected String describe() {
        return "использует RockSlide";
    }
}
