import java.io.*;
import java.net.Socket;

public class ClientSocket {
    private String serverAdress = "localhost";
    private int serverPort = 11000;
    private Socket server = null;


    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;
    private int userID;

    public String getMessage() throws IOException {
       return in.readLine();
    }

    public ClientSocket () throws IOException, ClassNotFoundException {
            // create socket
            server = new Socket(serverAdress, serverPort);

            // in & out streams
            out = new ObjectOutputStream(server.getOutputStream());
            in = new ObjectInputStream(server.getInputStream());

            // send first message
            Message message = new Message(-1,MessageType.HELLO,"");
            out.writeObject(message);
            message = (Message) in.readObject();
            userID = Integer.parseInt(message.getTextMesssage());

            //outData.flush();
         System.out.println(userID);

    }

    public void send(String message, int friendID){
        //out.print(new Message (userID,friendID,message));
    }
    public  boolean isReady(){return server != null ? false : true;}
    public void closeConnect() throws IOException {
        if (server != null){
            server.close();
        }
    }
}
