import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class Server extends Thread{
    private DatagramSocket socket = null;
    private int portNumber = 11000;
    private String resiveMessage = "Hello word! - server";

    @Override
    public void run() {
        System.out.println("UDP server starting");
        try {
            socket = new DatagramSocket(portNumber);
            byte[] receiveBuffer = new byte[1024];
            while (true) {
                Arrays.fill(receiveBuffer, (byte) 0);
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);

                String msg = new String(receivePacket.getData());
                System.out.println("Server: " + msg);

                byte[] sendMessage = resiveMessage.getBytes();

                InetAddress address = receivePacket.getAddress();
                int port = receivePacket.getPort();
                DatagramPacket response
                        = new DatagramPacket(
                        sendMessage, sendMessage.length, address, port);

                socket.send(response);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}
