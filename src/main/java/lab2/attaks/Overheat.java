package lab2.attaks;

import ru.ifmo.se.pokemon.*;

public final class Overheat extends SpecialMove {

    public Overheat(){
        this.type = Type.FIRE;
        this.power = 130;
        this.accuracy = 90;
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        pokemon.addEffect(new Effect().turns(-1).stat(Stat.SPECIAL_ATTACK, -6));
    }

    @Override
    protected String describe() {
        return "использует Overheat";
    }

    @Override
    protected boolean checkAccuracy(Pokemon pokemon, Pokemon pokemon1) {
        return true;
    }
}
