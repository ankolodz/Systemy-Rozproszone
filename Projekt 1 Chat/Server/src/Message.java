import java.io.Serializable;

public class Message implements Serializable {
    private int fromID;
    private int toID;
    private MessageType type = MessageType.TEXT;
    private String textMesssage;
    private Object object;

    public Message (int id, int friendID,String message){
        this.fromID = id;
        this.toID = friendID;
        this.textMesssage = message;
    }
    public Message (int id, MessageType type,Object message){
        this.fromID = id;
        this.type = type;
        this.object = message;
    }

    public int getFromID() {
        return fromID;
    }

    public MessageType getType() {
        return type;
    }

    public String getTextMesssage() {
        return textMesssage;
    }

    public Object getObject(){ return object;}
}
