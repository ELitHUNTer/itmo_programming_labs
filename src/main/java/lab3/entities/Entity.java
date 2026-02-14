package lab3.entities;

public class Entity {

    private static int id = 0;

    {
        id++;
    }

    public int getId(){
        return id;
    }

}
