import javafx.print.Printer;

import javax.swing.*;
import java.io.IOException;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException,  ClassNotFoundException {
        String nick = ChatBox.getNick();
        ClientSocket clientSocket = new ClientSocket(nick);
        ChatBox chatBox = new ChatBox(clientSocket);
        MultimediaSocket multimediaSocket = new MultimediaSocket(chatBox,clientSocket.getMyPort(),clientSocket.getID());
        MulticastClientSocket multicastSocket = new MulticastClientSocket(chatBox,clientSocket.getID(),nick);

        chatBox.setMultimediaSocket(multimediaSocket);
        chatBox.setMulticastSocket(multicastSocket);
        multimediaSocket.start();
        multicastSocket.start();
        chatBox.start(nick);


        try {
            while (true) {
                try {
                    String text;
                    text = clientSocket.getMessage();
                    chatBox.newMessage(text,false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            clientSocket.closeConnect();
        }
//

    }
}
