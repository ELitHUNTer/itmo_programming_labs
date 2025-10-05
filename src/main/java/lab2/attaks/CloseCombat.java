package lab2.attaks;

import ru.ifmo.se.pokemon.*;

public final class CloseCombat extends PhysicalMove {

    public CloseCombat(){
        this.type = Type.FIGHTING;
        this.power = 120;
        this.accuracy = 100;
    }

    @Override
    protected void applySelfEffects(Pokemon pokemon) {
        pokemon.addEffect(new Effect()
                .turns(-1)
                .stat(Stat.DEFENSE, -1));
        pokemon.addEffect(new Effect()
                .turns(-1)
                .stat(Stat.SPECIAL_DEFENSE, -1));
    }

    @Override
    protected String describe() {
        return "использует CloseCombat";
    }
}
