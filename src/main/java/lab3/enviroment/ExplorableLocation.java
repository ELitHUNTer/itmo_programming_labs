package lab3.enviroment;

import lab3.enviroment.ground.Soil;

public class Location {

    private Coordinates latitude, longitude;
    private Weather weather;
    private Soil soil;

    public Location(Coordinates latitude, Coordinates longitude, Weather weather, Soil soil){
        this.latitude = latitude;
        this.longitude = longitude;
        this.weather = weather;
        this.soil = soil;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public Coordinates getLatitude() {
        return latitude;
    }

    public Coordinates getLongitude() {
        return longitude;
    }

    public Weather getWeather() {
        return weather;
    }

    public Soil getSoil() {
        return soil;
    }
}
