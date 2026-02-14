package lab3.entities;

import lab3.messages.*;
import lab3.messages.receivers.DefaultMessageReceiver;
import lab3.messages.receivers.RadioMessageReceiver;
import lab3.messages.senders.DefaultMessageSender;
import lab3.messages.senders.RadioMessageSender;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ShortWaveRadio implements DefaultMessageSender, RadioMessageSender, RadioMessageReceiver {

    private float frequency;
    private int volume;
    private List<DefaultMessageReceiver> receivers;

    public ShortWaveRadio(float frequency, int volume){
        this.frequency = frequency;
        this.volume = volume;
        receivers = new LinkedList<>();
    }

    public float getFrequency() {
        return frequency;
    }

    public int getVolume() {
        return volume;
    }

    public static ShortWaveRadio getDefault(){
        return new ShortWaveRadio(100F, 100);
    }

    public void addReceivers(DefaultMessageReceiver receiver){
        receivers.add(receiver);
    }

    public void removeReceivers(DefaultMessageReceiver receiver){
        receivers.remove(receiver);
    }

    // TODO
    @Override
    public void receive(RadioMessage message) {
        if (Float.compare(frequency, message.getFrequency()) == 0)
            receivers.forEach(x -> send(new DefaultMessage(message.getContent()), x));
    }
}
