public class Main {
    public static void main(String[] args) throws InterruptedException {
        Server server = new Server();
        Client client = new Client();

        server.start();
        while (true){
            client.send();
            Thread.sleep(200);
        }
    }
}
