package lab2.attaks;

import ru.ifmo.se.pokemon.*;

public final class ChargeBeam extends SpecialMove {

    public ChargeBeam(){
        this.type = Type.ELECTRIC;
        this.accuracy = 90;
        this.power = 50;
    }

    @Override
    protected void applySelfEffects(Pokemon pokemon) {
        pokemon.addEffect(new Effect().
                chance(0.7)
                .turns(-1)
                .stat(Stat.SPECIAL_ATTACK, 1));
    }

    @Override
    protected String describe() {
        return "использует ChargeBeam";
    }
}
