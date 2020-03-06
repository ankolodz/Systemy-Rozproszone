
public class Main {
    public static void main(String[] args) throws InterruptedException {
        int portnumber = 11000;
        CleintsHandler clients = new CleintsHandler();

        TCPsocketServer tcpSocket = new TCPsocketServer(portnumber,clients);
        tcpSocket.start();

        System.out.println("Main preparing UDP");
        UDPsocketServer udpSocket = new UDPsocketServer(portnumber,clients);
        udpSocket.start();

        udpSocket.join();

    }
}
