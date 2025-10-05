package lab2.attaks;

import ru.ifmo.se.pokemon.*;

public final class Thunderbolt extends SpecialMove {

    public Thunderbolt(){
        this.type = Type.ELECTRIC;
        this.power = 90;
        accuracy = 100;
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        pokemon.addEffect(new Effect().turns(0).chance(0.1).condition(Status.PARALYZE));
    }

    @Override
    protected String describe() {
        return "использует Thunderbolt";
    }
}
