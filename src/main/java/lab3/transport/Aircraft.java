package lab3.transport;

import lab3.entities.ShortWaveRadio;
import lab3.enviroment.ExplorableLocation;
import lab3.enviroment.Location;

public class Aircraft extends Transport {

    private ShortWaveRadio radio;

    public Aircraft(int maxFuel, int maxWeight, int selfWeight, int maxSpeed, Location currentLocation) {
        super(maxFuel, maxWeight, selfWeight, maxSpeed, currentLocation);
        radio = ShortWaveRadio.getDefault();
    }

    public ShortWaveRadio getRadio() {
        return radio;
    }
}
