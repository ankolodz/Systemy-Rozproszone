import java.lang.reflect.Array;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class ServerUDP {
    public static void main(String args[])
    {
        System.out.println("Starting server");
        DatagramSocket socket = null;
        int portNumber = 11000;

        try{
            socket = new DatagramSocket(portNumber);
            byte[] receiveBuffer = new byte[4];

            while(true) {
                Arrays.fill(receiveBuffer, (byte)0);
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);

                byte[] message = receivePacket.getData();
                byte[] mes = new byte[4];
                System.out.println(message.length);
                for (int i=0;i<4;i++){
                    mes[i] = message[4-i-1];
                }


                int nb = ByteBuffer.wrap(mes).getInt();
                nb++;
                System.out.println("received msg: " + nb );

                byte[] sendMessage = ByteBuffer.allocate(4).putInt(nb).array();

                InetAddress address = receivePacket.getAddress();
                int port = receivePacket.getPort();
                DatagramPacket response
                        = new DatagramPacket(
                        sendMessage, sendMessage.length, address, port);

                socket.send(response);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}
