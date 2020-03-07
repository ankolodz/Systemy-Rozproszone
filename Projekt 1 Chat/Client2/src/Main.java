import java.io.IOException;
import java.net.SocketException;

public class Main {


    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
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
            String text;
            text = clientSocket.getMessage();
            chatBox.newMessage(text,false);
            }
        }catch (SocketException e){
                    ChatBox.alert("Błąd krytyczny połączenia z serwerem");
                }

        catch (IOException e) {
                    e.printStackTrace();
                }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            chatBox.stop();
            clientSocket.closeConnect();
            multicastSocket.closeConnetion();
            multimediaSocket.closeConnetion();
            System.out.println("Multimedia socked stop");
            System.out.println("Multicast socked stop");

        }
//

    }
}
