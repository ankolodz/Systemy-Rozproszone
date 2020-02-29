import com.sun.jdi.event.ThreadDeathEvent;

import java.io.*;
import java.net.Socket;
import java.util.Collection;
import java.util.LinkedList;

public class Client extends Thread {
    private Socket clientSocket = null;
    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;
    private int ID;
    private String name;
    CleintsHandler clients;

    public Client (Socket clientSocket, int ID,CleintsHandler clients) throws IOException {
        this.clientSocket = clientSocket;
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());
        this.ID = ID;
        this.clients = clients;
    }
    @Override
    public void run() {
        try {
            init();
            messageHandler();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getClientName() {
        return name;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }
    public void sendTextMessage(int frinedID, String text) throws IOException {
        Message message = new Message(ID,frinedID,text);
        out.writeObject(message);
    }
    private void sendMessageBroadCast(String text) throws IOException {
        Collection<Client> clientsList = clients.getClientsList();
        for (Client i:clientsList) {
            i.sendTextMessage(ID,text);
        }
    }


    private void messageHandler () {
        try {
            Message message = (Message) in.readObject();
            switch (message.getType()) {
                case TEXT:
                    sendMessageBroadCast(message.getTextMesssage());
                    break;

                default:
                    System.out.println("Nieznane polecenie");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        private void  init() throws IOException, ClassNotFoundException {
            Message initialMessage = (Message) in.readObject();
            if (!initialMessage.getType().equals(MessageType.HELLO))
                throw new ExceptionInInitializerError();
            this.name = initialMessage.getTextMesssage();
            initialMessage = new Message(0,MessageType.HELLO, String.valueOf(ID));
            out.writeObject(initialMessage);
    }

}
