package lab2.pokemons;

import lab2.attaks.Overheat;

public final class Zebstrika extends Blitzle {

    public Zebstrika(){
        super("Zebstrika", 1);
    }

    public Zebstrika(String name, int lvl){
        super(name, lvl);
        addMove(new Overheat());
        setStats(75, 100, 63, 80, 63, 116);
    }

}
