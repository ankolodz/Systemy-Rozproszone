import java.net.Socket;
import java.util.*;

public class CleintsHandler {
    private LinkedHashMap<Integer,Client> clients;

    public CleintsHandler() {
        this.clients = new LinkedHashMap<>();
    }

    public Collection<Client> getClientsList() {
        return clients.values();
    }

    public Socket getClientAddres (int id){
        if (clients.containsKey(id))
            return clients.get(id).getClientSocket();
        return null;
    }
    public void addClient (Client client, int id){
        clients.put(id,client);
    }
    public void deleteClient (int id){
        clients.remove(id);
    }
    public LinkedList<String> cleintsNameList (){
        Collection<Client> cl = clients.values();
        LinkedList<String> names = new LinkedList<>();
        for ( Client i:cl) {
            names.add(i.getClientName());
        }
        return  names;
    }
}
