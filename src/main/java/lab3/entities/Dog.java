package lab3.entities;

public class Dog extends Entity {

    private boolean isDead;

    {
        isDead = false;
    }

    public boolean isAlive(){
        return !isDead;
    }

    public void die(){
        isDead = true;
    }

}
