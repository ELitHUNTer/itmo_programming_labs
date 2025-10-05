package lab2.pokemons;

import lab2.attaks.Confide;
import lab2.attaks.Swagger;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Seedot extends Pokemon {

    public Seedot(){
        super("Seedot", 1);
    }

    public Seedot(String name, int lvl){
        super(name, lvl);
        setMove(new Swagger(),
                new Confide());
        setType(Type.GRASS);
        setStats(40, 40, 50, 30, 30, 30);
    }

}
