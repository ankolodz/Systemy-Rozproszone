import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class ServerUDP {
    public static void main(String args[])
    {
        System.out.println("Starting server");
        DatagramSocket socket = null;
        int portNumber = 11000;

        try{
            socket = new DatagramSocket(portNumber);
            byte[] receiveBuffer = new byte[1024];

            while(true) {
                Arrays.fill(receiveBuffer, (byte)0);
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);
                String msg = new String(receivePacket.getData());
                System.out.println("received msg: " + msg);
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
