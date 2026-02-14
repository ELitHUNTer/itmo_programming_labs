package lab3.messages;

public class DefaultMessage implements Message {

    private final String content;

    public DefaultMessage(String content){
        this.content = content;
    }

    @Override
    public String getContent() {
        return content;
    }
}
