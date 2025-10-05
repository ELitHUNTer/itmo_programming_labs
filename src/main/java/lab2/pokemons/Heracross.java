package lab2.pokemons;

import lab2.attaks.ArmThrust;
import lab2.attaks.CloseCombat;
import lab2.attaks.RockSlide;
import lab2.attaks.Venoshok;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public final class Heracross extends Pokemon {

    public Heracross(){
        super("Heracross", 1);
    }

    public Heracross(String name, int lvl){
        super(name, lvl);
        setMove(new Venoshok(),
                new ArmThrust(),
                new RockSlide(),
                new CloseCombat());
        setType(Type.BUG, Type.FIGHTING);
        setStats(80, 125, 75, 40, 95, 85);
    }

}
