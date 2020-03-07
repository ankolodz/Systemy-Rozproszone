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

    public int getID (){return this.ID;}
    public void sendTextMessage(int frinedID, String text) throws IOException {
        Message message = new Message(ID,frinedID,text);
        out.writeObject(message);
    }
    private void sendMessageBroadCast(String text) throws IOException {
        Collection<Client> clientsList = clients.getClientsList();
        for (Client i:clientsList) {
            if (!i.getClientName().equals(name) && i.getClientSocket()!= null)
                i.sendTextMessage(i.ID,name + ": " + text);
        }
    }


    private void messageHandler () throws IOException {
        try {
            while (true) {
                Message message = (Message) in.readObject();
                switch (message.getType()) {
                    case BROADCAST:
                        sendMessageBroadCast(message.getTextMesssage());
                        break;
                    case UDP:

                        break;
                    default:
                        System.out.println("Nieznane polecenie");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (!clientSocket.isClosed())
                clientSocket.close();
            clientSocket = null;
            System.out.println(name+" "+ID+" disconect");
        }
    }

        private void  init() throws IOException, ClassNotFoundException {
            //in.read();
            while (name == null){
                Message initialMessage = (Message) in.readObject();
                if (!initialMessage.getType().equals(MessageType.HELLO))
                    throw new ExceptionInInitializerError();
                this.name = initialMessage.getTextMesssage();
                System.out.println(name);
                initialMessage = new Message(0,MessageType.HELLO, String.valueOf(ID));
                out.writeObject(initialMessage);
            }

    }

}
