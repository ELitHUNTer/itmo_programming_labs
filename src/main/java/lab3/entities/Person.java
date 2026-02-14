package lab3.entities;

import lab3.messages.DefaultMessage;
import lab3.messages.receivers.DefaultMessageReceiver;
import lab3.messages.senders.DefaultMessageSender;

import java.util.LinkedList;
import java.util.List;

public class Person extends Entity implements DefaultMessageSender, DefaultMessageReceiver {

    private String name;
    private List<MessageListener> listeners;

    public Person(String name){
        this.name = name;
        listeners = new LinkedList<>();
    }

    public void addListener(MessageListener listener){
        listeners.add(listener);
    }

    public void clearListeners(){
        listeners = new LinkedList<>();
    }

    public String getName(){
        return name;
    }

    @Override
    public void receive(DefaultMessage message) {
        System.out.printf("%s received %s%n" +
                "", name, message.getContent());
        listeners.forEach(x -> x.onMessageReceive(message));
    }
}
