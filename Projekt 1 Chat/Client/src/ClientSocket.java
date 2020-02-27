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

    public ClientSocket (int userID) throws IOException {
        this.userID = userID;
            // create socket
            server = new Socket(serverAdress, serverPort);

            // in & out streams
            out = new ObjectOutputStream(server.getOutputStream());
            in = new ObjectInputStream(server.getInputStream());

            // send first message
            Message message = new Message(userID,MessageType.HELLO,"");
            out.writeObject(message);
            //outData.flush();
         System.out.println(in.readLine());

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
