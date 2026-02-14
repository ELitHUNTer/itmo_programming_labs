package lab3.enviroment.ground.fossils;

import lab3.enviroment.Temperature;

public class PrimitiveLife extends Fossil{

    private Temperature existanceTemperature;
    private String name;

    public PrimitiveLife(String name, int volume, Temperature existanceTemperature) {
        super(volume);
        this.name = name;
        this.existanceTemperature = existanceTemperature;
    }

    public Temperature getExistanceTemperature() {
        return existanceTemperature;
    }

    public String getName() {
        return name;
    }
}
