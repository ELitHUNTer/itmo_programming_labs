package lab3.entities;

import lab3.messages.Message;

@FunctionalInterface
public interface MessageListener {

    void onMessageReceive(Message message);

}
