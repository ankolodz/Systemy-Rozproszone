import java.io.*;
import java.net.Socket;

public class ClientSocket {
    private String serverAdress = "localhost";
    private int serverPort = 11000;
    private Socket server = null;


    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;
    private int userID;
    private String name;

    public String getMessage() throws IOException {
        try {
            Message message = (Message) in.readObject();
            return message.getFromID() + " " + message.getTextMesssage();
        } catch (ClassNotFoundException e) {
            return "Błąd wiadomości";
        }

    }

    public ClientSocket (String name) throws IOException, ClassNotFoundException {
            // create socket
            server = new Socket(serverAdress, serverPort);
            this.name = name;

            // in & out streams
            out = new ObjectOutputStream(server.getOutputStream());
            in = new ObjectInputStream(server.getInputStream());

            // send first message
            Message message = new Message(-1,MessageType.HELLO,name);
        System.out.println(message.getTextMesssage());
            out.writeObject(message);
            message = (Message) in.readObject();
            System.out.println(message.getTextMesssage());
            userID = Integer.parseInt(message.getTextMesssage());

            //outData.flush();
         System.out.println(userID);

    }

    public void sendBroadCast(String message){
        try {
            out.writeObject(new Message (userID,MessageType.BROADCAST,message));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  boolean isReady(){return server != null ? false : true;}

    public void closeConnect() throws IOException {
        if (server != null){
            server.close();
        }
    }
}
