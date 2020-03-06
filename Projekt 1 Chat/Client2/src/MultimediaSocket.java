import com.sun.xml.internal.ws.addressing.v200408.MemberSubmissionWsaServerTube;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.*;

public class MultimediaSocket extends Thread{
    private ChatBox chatBox;
    private int myPort;
    private String serverAdress = "localhost";
    private int serverPort = 11000;
    private int id;

    private DatagramSocket socket;

    public MultimediaSocket (ChatBox chatBox, int myPort,int id){
        this.chatBox = chatBox;
        this.myPort = myPort;
        this.id = id;
    }

    @Override
    public void run(){
        try{
            socket = new DatagramSocket(myPort);
            System.out.println("Port: " + myPort);
            byte[] response = new byte[4096];

            while (true){
                DatagramPacket incomingPackage = new DatagramPacket(response,response.length);
                System.out.println("Oczekiwanie na wiadomość");
                socket.receive(incomingPackage);
                System.out.println("Otrzymano wiadomość");
                byte[] data = incomingPackage.getData();

                ByteArrayInputStream byteIN = new ByteArrayInputStream(data);
                ObjectInputStream in = new ObjectInputStream(byteIN);

                Message message = null;
                message = (Message) in.readObject();


                chatBox.newMessage(message.getTextMesssage());
            }
        }
        catch (SocketException e) {
            System.out.println("Błąd utworzenia socketu multimediów");
        } catch (ClassNotFoundException e) {
            System.out.println("Błędny format wiadomości");
        }catch (IOException e) {
            e.printStackTrace();
            System.out.println("Błąd parsingu");
        }
    }

    public void send (String multimediaMessage){
        try {
            InetAddress addres = InetAddress.getByName(serverAdress);
            Message message = new Message(id, MessageType.BROADCAST,multimediaMessage);

            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(message);
            byte[] data = byteOut.toByteArray();

            DatagramPacket sendPackage = new DatagramPacket(data,data.length,addres,serverPort);
            socket.send(sendPackage);

        } catch (UnknownHostException e) {
            System.out.println("Błąd rzutowania adresu serwera");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
