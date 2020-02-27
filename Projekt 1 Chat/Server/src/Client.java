import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    Socket clientSocket = null;
    PrintWriter out = null;
    BufferedReader in = null;

    public Client (Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }
    public void start() throws IOException {
        String msg = in.readLine();
        System.out.println("received msg: " + msg);
        out.println("Pong Java Tcp");
    }

}
