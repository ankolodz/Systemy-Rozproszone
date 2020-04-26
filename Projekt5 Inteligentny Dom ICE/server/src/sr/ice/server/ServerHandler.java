package sr.ice.server;

import Demo.Server;
import com.zeroc.Ice.Current;

public class ServerHandler implements Server {
    String items;
    ServerHandler (String items){
        this.items = items;
    }
    @Override
    public String AllID(Current current) {
        return items;
    }
}
