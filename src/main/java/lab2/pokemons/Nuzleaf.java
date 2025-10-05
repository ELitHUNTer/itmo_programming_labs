package lab2.pokemons;

import lab2.attaks.Pound;
import ru.ifmo.se.pokemon.Type;

public class Nuzleaf extends Seedot {

    public Nuzleaf(){
        super("Nuzleaf", 1);
    }

    public Nuzleaf(String name, int lvl){
        super(name, lvl);
        addMove(new Pound());
        addType(Type.DARK);
        setStats(70, 70, 40, 60, 40, 60);
    }

}
