package lab2.pokemons;

import lab2.attaks.ChargeBeam;
import lab2.attaks.ShockWave;
import lab2.attaks.Thunderbolt;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Blitzle extends Pokemon {

    public Blitzle(){
        this("Blitzle", 1);
    }

    public Blitzle(String name, int lvl){
        super(name, lvl);
        setMove(new ShockWave(),
                new ChargeBeam(),
                new Thunderbolt());
        setType(Type.ELECTRIC);
        setStats(45, 60, 32, 50, 32, 76);
    }

}
