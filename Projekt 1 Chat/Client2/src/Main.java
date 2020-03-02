import javafx.print.Printer;

import javax.swing.*;
import java.io.IOException;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException,  ClassNotFoundException {

        ClientSocket clientSocket = new ClientSocket(ChatBox.getNick());
        ChatBox chatBox = new ChatBox(clientSocket);
        chatBox.start();

        Thread t1 = null;
        try {

           // System.out.println("Client was been starting");
            t1 = new Thread(() -> {
                while(true){
                    try {
                        String text;
                        text = clientSocket.getMessage();
                        System.out.println(text);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            });
            t1.start();
            while (true) {
//                String text = in.nextLine();
//                clientSocket.sendBroadCast(text);
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
