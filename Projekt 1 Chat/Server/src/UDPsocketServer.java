import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UDPsocketServer extends Thread{
    DatagramSocket serverSocket;
    int port;
    CleintsHandler clients;
    ExecutorService threadPool;

    public UDPsocketServer (int port, CleintsHandler clients){
        this.port = port;
        this.clients = clients;
    }

    public void start(){
        try {
            serverSocket = new DatagramSocket(port);
            byte[] incomingData = new byte[4096];

            threadPool = Executors.newFixedThreadPool(200);


            while (true){
                DatagramPacket incomingPacket = new DatagramPacket(incomingData,incomingData.length);
                serverSocket.receive(incomingPacket);
                byte[] data = incomingPacket.getData();

                ByteArrayInputStream byteIN = new ByteArrayInputStream(data);
                ObjectInputStream in = new ObjectInputStream(byteIN);

                try{
                    Message message = (Message) in.readObject();

                    Runnable worker = new UDPworker(message,clients);
                    threadPool.execute(worker);
                }
                catch (ClassNotFoundException e) {
                    System.out.println("Błąd odebranej wiadomości");
                }

            }

        }
        catch (SocketException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        } finally{
            if (serverSocket!= null)
                serverSocket.close();
        }
    }
    private class UDPworker implements Runnable{
        Message message;
        CleintsHandler clients;

        public UDPworker (Message message, CleintsHandler clients) {
            this.clients = clients;
            this.message = message;
        }

        @Override
        public void run() {
            System.out.println("Do something");
        }
    }

}
