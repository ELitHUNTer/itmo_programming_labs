package lab3.enviroment;

public class Weather {

    private Temperature temperature;
    private WindType windType;
    private float windForce;

    public Weather(Temperature temperature, WindType windType, float windForce){
        this.temperature = temperature;
        setWind(windType, windForce);
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public WindType getWindType() {
        return windType;
    }

    public float getWindForce() {
        return windForce;
    }

    public void setWind(float force){
        if (force == 0)
            setWind(WindType.NONE, force);
        else
            setWind(windType, force);
    }

    public void setWind(WindType windType, float force){
        if (windForce < 0)
            throw new IllegalArgumentException("Wind force can't be negative");
        if (windType == WindType.NONE && windForce != 0
            || windType != WindType.NONE && windForce == 0)
            throw new IllegalArgumentException("Wind force can be 0 only if wind is WindType.NONE");
        this.windForce = force;
        this.windType = windType;
    }

    public enum WindType{
        NONE,
        NORTH,
        NORTH_WEST,
        NORTH_EAST,
        SOUTH,
        SOUTH_WEST,
        SOUTH_EAST,
        EAST,
        WEST;
    }

}
