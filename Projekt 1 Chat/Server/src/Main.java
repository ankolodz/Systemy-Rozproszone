import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int portnumber = 11000;
        CleintsHandler clients = new CleintsHandler();

        TCPsocketServer tcpSocket = new TCPsocketServer(portnumber,clients);
        tcpSocket.start();

        UDPsocketServer udpSocket = new UDPsocketServer(portnumber,clients);
        udpSocket.start();

        tcpSocket.join();

    }
}
