import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class MulticastClientSocket extends Thread {

    private MulticastSocket socket= null;
    private ChatBox chatBox;
    private int id;
    private String nick;

    public MulticastClientSocket(ChatBox chatBox, int id, String nick){
        this.chatBox = chatBox;
        this.id = id;
        this.nick = nick;
    }

    @Override
    public void run(){
        try{
            socket = new MulticastSocket(8888);
            InetAddress adress = InetAddress.getByName("224.0.0.3");
            socket.joinGroup(adress);
            byte[] response = new byte[20000];

            while(true){
                DatagramPacket incomingPackage = new DatagramPacket(response,response.length);
                socket.receive(incomingPackage);
                byte[] data = incomingPackage.getData();

                ByteArrayInputStream byteIN = new ByteArrayInputStream(data);
                ObjectInputStream in = new ObjectInputStream(byteIN);

                Message message = (Message) in.readObject();
                if (this.id != message.getFromID())
                    chatBox.newMessage(message.getTextMesssage(),false);
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void send (String text){
        try {
            InetAddress adress = InetAddress.getByName("224.0.0.3");
            Message message = new Message(id, MessageType.BROADCAST, nick + "\n" + text);
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(message);
            byte[] data = byteOut.toByteArray();
            System.out.println(data.length);
            DatagramPacket sendPackage = new DatagramPacket(data, data.length,adress,8888);
            socket.send(sendPackage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
