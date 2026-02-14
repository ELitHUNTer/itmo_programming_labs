package lab3.messages;

public class RadioMessage implements Message {

    private float frequency;
    private String content;

    public RadioMessage(String content, float frequency) {
        this.content = content;
        this.frequency = frequency;
    }

    public float getFrequency(){
        return frequency;
    }

    @Override
    public String getContent() {
        return content;
    }
}
