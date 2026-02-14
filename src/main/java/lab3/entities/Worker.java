package lab3.entities;

import java.util.LinkedList;
import java.util.List;

public class Worker extends Person{

    private List<Profession> professions;

    public Worker(String name, Profession profession) {
        super(name);
        professions = new LinkedList<>();
        professions.add(profession);
    }

    public Worker(String name, List<Profession> profession) {
        super(name);
        professions = new LinkedList<>();
        professions.addAll(profession);
    }

    public List<Profession> getProfessions(){
        return professions;
    }

    public void addProfession(Profession profession){
        professions.add(profession);
    }

    public boolean hasProfession(Profession profession){
        return professions.contains(profession);
    }
}
