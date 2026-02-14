package lab3.messages.senders;

import lab3.messages.DefaultMessage;
import lab3.messages.receivers.DefaultMessageReceiver;

public interface DefaultMessageSender {

    default void send(DefaultMessage message, DefaultMessageReceiver receiver){
        receiver.receive(message);
    }

}
