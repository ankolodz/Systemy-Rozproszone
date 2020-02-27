public class Message {
    private int fromID;
    private int toID;
    private MessageType type = MessageType.TEXT;
    private String textMesssage;

    public Message (int id, int friendID,String message){
        this.fromID = id;
        this.toID = friendID;
        this.textMesssage = message;
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
}
