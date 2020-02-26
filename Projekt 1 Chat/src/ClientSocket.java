import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocket {
    String serverAdress = "localhost";
    int serverPort = 11000;
    Socket server = null;
    boolean isWork = true;
    BufferedReader in = null;
    PrintWriter out = null;
    int userID;

    public Message getMessage() throws IOException {
        return new Message (userID,in.read(),in.readLine());
    }

    public ClientSocket (int userID) throws IOException {
        this.userID = userID;
        try {
            // create socket
            server = new Socket(serverAdress, serverPort);

            // in & out streams
            out = new PrintWriter(server.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(server.getInputStream()));

            // send msg, read response
            out.println(userID);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (server != null){
                server.close();
            }
        }
    }

    public void send(String message, int friendID){
        out.print(new Message (userID,friendID,message));
    }
}
