package lab2;

import lab2.attaks.Overheat;
import lab2.pokemons.*;
import main.Solution;
import ru.ifmo.se.pokemon.Battle;
import ru.ifmo.se.pokemon.Move;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;

import java.util.LinkedList;
import java.util.List;

public class Lab2Main implements Solution {

    @Override
    public void solve() {
        Battle battle = new Battle();



        Pokemon attaker = new TempPokemon("Attaker", new Overheat());

        Pokemon reciver1 = new TempPokemon("Reciever 1");
        Pokemon reciver2 = new TempPokemon("Reciever 2");
        Pokemon reciver3 = new TempPokemon("Reciever 3");

        System.out.printf("Reciever1 SA: %f%nReciever2 SA: %f%nReciever3 SA: %f%n",
                reciver1.getStat(Stat.SPECIAL_ATTACK),
                reciver2.getStat(Stat.SPECIAL_ATTACK),
                reciver3.getStat(Stat.SPECIAL_ATTACK));

        attaker.prepareMove();
        attaker.attack(reciver1);
        attaker.prepareMove();
        attaker.attack(reciver2);
        attaker.prepareMove();
        attaker.attack(reciver3);

        System.out.printf("Reciever1 SA: %f%nReciever2 SA: %f%nReciever3 SA: %f%n",
                reciver1.getStat(Stat.SPECIAL_ATTACK),
                reciver2.getStat(Stat.SPECIAL_ATTACK),
                reciver3.getStat(Stat.SPECIAL_ATTACK));

        reciver1.turn();
        reciver2.turn();
        reciver3.turn();

//        battle.addAlly(new Heracross());
//        battle.addAlly(new Blitzle());
//        battle.addAlly(new Nuzleaf());
//
//        battle.addFoe(new Seedot());
//        battle.addFoe(new Shiftry());
//        battle.addFoe(new Zebstrika());

        //battle.go();
    }

    class TempPokemon extends Pokemon{

        static {
            System.out.println("Загрузка");
        }

        private TempPokemon(String name, Move... moves){
            super(name, 1);
            setStats(100, 100, 100, 100, 100, 100);
            setMove(moves);
            System.out.println("Создание объекта");
        }

        class Builder{
            List<Move> moves = new LinkedList<>();
            String name;

            public Builder addMove(Move move){
                moves.add(move);
                return this;
            }

            public Builder setName(String name){
                this.name = name;
                return this;
            }

            public TempPokemon build(){
                return new TempPokemon(name, moves.toArray(Move[]::new));
            }

        }
    }

}
