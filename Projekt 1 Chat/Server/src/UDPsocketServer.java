import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Collection;
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
    @Override
    public void run(){
        try {
            System.out.println("Start UDP");
            serverSocket = new DatagramSocket(port);
            byte[] incomingData = new byte[4096];

            threadPool = Executors.newFixedThreadPool(5);


            while (true){
                DatagramPacket incomingPacket = new DatagramPacket(incomingData,incomingData.length);
                serverSocket.receive(incomingPacket);
                byte[] data = incomingPacket.getData();

                ByteArrayInputStream byteIN = new ByteArrayInputStream(data);
                ObjectInputStream in = new ObjectInputStream(byteIN);
                System.out.println("Start listening UDP");
                try{
                    Message message = (Message) in.readObject();
                    System.out.println("Server have a message");
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
            Collection<Client> clientList = clients.getClientsList();
            try {
                ByteArrayOutputStream outData = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(outData);
                out.writeObject(message);
                byte[] data = outData.toByteArray();
                System.out.println("Workers is working");
            for (Client i :clientList) {
                if (i.getId() != message.getFromID() || i.getClientSocket() != null){
                    InetAddress address = i.getClientSocket().getInetAddress();
                    int port = i.getClientSocket().getPort();
                    System.out.println(address.getHostName()+ " " + port + " "+i.getClientName());
                    DatagramPacket datagramPacket = new DatagramPacket(data,data.length,address,port);
                    serverSocket.send(datagramPacket);

                }
            }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
