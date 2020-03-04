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
            threadPool = Executors.newFixedThreadPool(200);

            Runnable service = () ->{
                
            }

        }
        catch (SocketException e) {
            e.printStackTrace();
        }
        finally{
            if (serverSocket!= null)
                serverSocket.close();
        }
    }

}
