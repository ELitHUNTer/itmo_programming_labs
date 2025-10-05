package lab2.pokemons;

import lab2.attaks.SwordsDance;

public final class Shiftry extends Nuzleaf{

    public Shiftry(){
        super("Shiftry", 1);
    }

    public Shiftry(String name, int lvl){
        super(name, lvl);
        addMove(new SwordsDance());
        setStats(90, 100, 60, 90, 60, 80);
    }

}
