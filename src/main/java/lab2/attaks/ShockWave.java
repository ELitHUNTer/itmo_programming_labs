package lab2.attaks;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Type;

public final class ShockWave extends SpecialMove {

    public ShockWave(){
        this.power = 60;
        this.type = Type.ELECTRIC;
        this.accuracy = Double.MAX_VALUE;
    }

    // This attak has infinite accuracy. idk how this works suppose it hits every time
    @Override
    protected boolean checkAccuracy(Pokemon pokemon, Pokemon pokemon1) {
        return true;
    }

    @Override
    protected String describe() {
        return "использует ShockWave";
    }
}
