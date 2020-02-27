import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Main {


    public static void main(String[] args) throws IOException, InterruptedException {

        try {
            ClientSocket clientSocket = new ClientSocket();
            //ChatBox chatBox = new ChatBox(2, clientSocket);
           // System.out.println("Client was been starting");
            while (true) {
               // System.out.println("Try get messaage");
                String message = clientSocket.getMessage();
                System.out.println(message);
                //System.out.println("Message");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
//            if (clientSocket.isReady()){
//                clientSocket.closeConnect();
//            }
        }
//

    }
}
