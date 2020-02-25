import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {

    private DatagramSocket socket = null;
    private int portNumber = 11000;
    private String message = "Hello word! - client";

    public void send(){
        System.out.println("Client send");
        try {
            socket = new DatagramSocket();
            InetAddress serverAdress = InetAddress.getByName("localhost");
            byte[] sendBuffer = message.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendBuffer,sendBuffer.length,serverAdress,portNumber);
            socket.send(sendPacket);

            byte[] buffer = new byte [1024];
            DatagramPacket receivePacket = new DatagramPacket( buffer,buffer.length);
            socket.setSoTimeout(1010);
            socket.receive(receivePacket);
            String msg = new String(receivePacket.getData());
            System.out.println("Client: " + msg);

        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            if (socket != null)
                socket.close();
        }
    }
}
