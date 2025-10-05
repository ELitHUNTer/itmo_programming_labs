package lab2.attaks;

import ru.ifmo.se.pokemon.*;

public final class Confide extends StatusMove {

    public Confide(){
        this.type = Type.NORMAL;
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        pokemon.addEffect(new Effect().stat(Stat.SPECIAL_ATTACK, -1).turns(-1));
    }

    @Override
    protected boolean checkAccuracy(Pokemon pokemon, Pokemon pokemon1) {
        return true;
    }

    @Override
    protected String describe() {
        return "использует Confide";
    }
}
