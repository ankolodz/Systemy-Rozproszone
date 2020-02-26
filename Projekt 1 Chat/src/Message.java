public class Message {
    private int fromID;
    private int toID;
    private String textMesssage;

    public Message (int id, int friendID,String message){
        this.fromID = id;
        this.toID = friendID;
        this.textMesssage = message;
    }
}
