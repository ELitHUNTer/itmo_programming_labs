package lab3.enviroment;

public class Temperature {

    private float kelvinDegree;

    public Temperature(float value, Scale scale){
        setTemperature(value, scale);
    }

    public Temperature(Temperature temperature){
        setTemperature(temperature);
    }

    public void setTemperature(float value, Scale scale){
        kelvinDegree = convertToKelvin(value, scale);
    }

    public void setTemperature(Temperature temperature){
        kelvinDegree = temperature.kelvinDegree;
    }

    public float asKelvin(){
        return kelvinDegree;
    }

    public float asCelsius(){
        return kelvinDegree - 273.15F;
    }

    private float convertToKelvin(float value, Scale scale){
        return switch (scale){
            case KELVIN -> value;
            case CELSIUS -> (value + 273.15F);
        };
    }

    public enum Scale{
        KELVIN(0F),
        CELSIUS(-273.15F);

        private float minValue;
        private Scale(float minValue){
            this.minValue = minValue;
        }
    }

    public static Temperature of(float value, Scale scale){
        return new Temperature(value, scale);
    }

}
