package lab2.attaks;

import ru.ifmo.se.pokemon.*;

import java.util.Random;

public final class Swagger extends StatusMove {

    public Swagger(){
        this.type = Type.NORMAL;
        this.accuracy = 85;
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        pokemon.addEffect(new Effect().turns(new Random().nextInt(1, 5)).stat(Stat.ATTACK, 2));
        pokemon.confuse();
    }

    @Override
    protected String describe() {
        return "использует Swagger";
    }
}
