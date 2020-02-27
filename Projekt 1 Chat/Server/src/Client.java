import java.io.*;
import java.net.Socket;

public class Client {
    Socket clientSocket = null;
    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;

    public Client (Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());
    }
    public void start() throws IOException {
        try {
            Message message = (Message) in.readObject();
            System.out.println(message.getFromID());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
