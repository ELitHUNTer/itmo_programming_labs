package lab3.enviroment;

public class Coordinates {

    private int x, y;

    public Coordinates(Coordinates coordinates){
        this(coordinates.x, coordinates.y);
    }

    public Coordinates(int x, int y){
        setX(x);
        setY(y);
    }

    public void setCoordinates(Coordinates coordinates){
        setCoordinates(coordinates.x, coordinates.y);
    }

    public void setCoordinates(int degrees, int minutes){
        setX(degrees);
        setY(minutes);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public static Coordinates of(int x, int y){
        return new Coordinates(x, y);
    }
}
