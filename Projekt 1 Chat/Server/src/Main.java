import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedHashMap;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("JAVA TCP SERVER");
        int portNumber = 11000;
        ServerSocket serverSocket = null;

        try {
            // create socket
            serverSocket = new ServerSocket(portNumber);

            while(true){

                // accept client
                Socket clientSocket = serverSocket.accept();
                System.out.println("client connected");
                Client client = new Client( (clientSocket));
                client.start();

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
