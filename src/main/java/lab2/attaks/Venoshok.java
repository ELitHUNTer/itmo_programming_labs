package lab2.attaks;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Status;
import ru.ifmo.se.pokemon.Type;

public final class Venoshok extends SpecialMove {

    public Venoshok(){
        this.power = 65;
        this.accuracy = 100;
        this.type = Type.POISON;
    }

    @Override
    protected void applyOppDamage(Pokemon pokemon, double v) {
        int mult = 1;
        if (pokemon.getCondition() == Status.POISON)
            mult = 2;
        super.applyOppDamage(pokemon, mult * v);
    }

    @Override
    protected String describe() {
        return "использует Venoshok";
    }
}
