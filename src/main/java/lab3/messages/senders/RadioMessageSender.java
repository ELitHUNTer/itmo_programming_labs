package lab3.messages.senders;

import lab3.messages.RadioMessage;
import lab3.messages.receivers.RadioMessageReceiver;

public interface RadioMessageSender {

    default void send(RadioMessage message, RadioMessageReceiver receiver){
        receiver.receive(message);
    }

}
