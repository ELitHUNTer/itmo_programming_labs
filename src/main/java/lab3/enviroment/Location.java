package lab3.enviroment;

public class Location {

    protected Coordinates coords;
    protected Weather weather;

    public Location(Coordinates coords, Weather weather){
        this.coords = coords;
        this.weather = weather;

    }

    public double distance(Location another) {
        return Math.sqrt(
                Math.pow(coords.getX() - another.coords.getX(), 2) + Math.pow(coords.getY() - another.coords.getY(), 2)
        );
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public Coordinates getCoordinates() {
        return coords;
    }

    public Weather getWeather() {
        return weather;
    }

}
