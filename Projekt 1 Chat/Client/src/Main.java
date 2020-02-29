import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException,  ClassNotFoundException {
        ClientSocket clientSocket = new ClientSocket("Client1");
        Thread t1 = null;
        try {
            //ChatBox chatBox = new ChatBox(2, clientSocket);
           // System.out.println("Client was been starting");
            t1 = new Thread(() -> {
                try {
                    String text = null;
                    text = clientSocket.getMessage();
                    System.out.println(text);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            t1.start();
            Scanner in = new Scanner(System.in);
            while (true) {
                String text = in.nextLine();
                clientSocket.sendBroadCast(text);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            t1.stop();
            clientSocket.closeConnect();
        }
//

    }
}
