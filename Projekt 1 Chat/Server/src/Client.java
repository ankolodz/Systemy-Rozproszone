import java.io.*;
import java.net.Socket;

public class Client {
    Socket clientSocket = null;
    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;
    private int ID;

    public Client (Socket clientSocket, int ID) throws IOException {
        this.clientSocket = clientSocket;
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());
        this.ID = ID;
    }
    public void start() throws IOException {
        try {
            init();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void  init() throws IOException, ClassNotFoundException {
        Message initialMessage = (Message) in.readObject();
        if (!initialMessage.getType().equals(MessageType.HELLO))
            throw new ExceptionInInitializerError();
        initialMessage = new Message(0,MessageType.HELLO, String.valueOf(ID));
        out.writeObject(initialMessage);
    }

}
