import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("JAVA TCP SERVER");
        int portNumber = 11000;
        ServerSocket serverSocket = null;
        CleintsHandler clients = new CleintsHandler();
        int nextClientID = 1;

        try {
            // create socket
            serverSocket = new ServerSocket(portNumber);

            while(true){

                // accept client
                Socket clientSocket = serverSocket.accept();
                System.out.println("client connected");
                Client client = new Client( clientSocket,nextClientID,clients);
                client.start();
                clients.addClient(client, nextClientID);
                nextClientID++;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            if (serverSocket != null){
                serverSocket.close();
            }
        }

    }
}
