package lab2;

import lab2.pokemons.*;
import main.Solution;
import ru.ifmo.se.pokemon.Battle;

public class Lab2Main implements Solution {

    @Override
    public void solve() {
        Battle battle = new Battle();

        battle.addAlly(new Heracross());
        battle.addAlly(new Blitzle());
        battle.addAlly(new Nuzleaf());

        battle.addFoe(new Seedot());
        battle.addFoe(new Shiftry());
        battle.addFoe(new Zebstrika());


        battle.go();
    }

}
