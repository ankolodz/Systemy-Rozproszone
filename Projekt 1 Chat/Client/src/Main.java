import java.io.IOException;

public class Main {


    public static void main(String[] args) throws IOException {

        ClientSocket clientSocket = new ClientSocket(1);
        ChatBox chatBox = new ChatBox(2, clientSocket);

        while (true){
            Message message = clientSocket.getMessage();
            System.out.println(message.getTextMesssage());
        }


    }
}
