import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPsocketServer extends Thread {

    private int portNumber = 11000;
    private ServerSocket serverSocket;
    private CleintsHandler clients;
    int nextClientID = 1;
    public TCPsocketServer (int portNumber, CleintsHandler clients){
        this.clients = clients;
        this.portNumber = portNumber;
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(portNumber);
            while (true) {
                // accept client
                Socket clientSocket = serverSocket.accept();
                System.out.println("new client connected");
                Client client = new Client(clientSocket, nextClientID, clients);
                client.start();
                clients.addClient(client, nextClientID);
                nextClientID++;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
