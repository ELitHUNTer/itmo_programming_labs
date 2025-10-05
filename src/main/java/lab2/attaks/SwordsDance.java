package lab2.attaks;

import ru.ifmo.se.pokemon.*;

public final class SwordsDance extends StatusMove {

    public SwordsDance(){
        this.type = Type.NORMAL;
    }

    @Override
    protected boolean checkAccuracy(Pokemon pokemon, Pokemon pokemon1) {
        return true;
    }

    @Override
    protected void applySelfEffects(Pokemon pokemon) {
        pokemon.addEffect(new Effect().turns(-1).stat(Stat.ATTACK, 2));
    }

    @Override
    protected String describe() {
        return "использует SwordsDance";
    }
}
