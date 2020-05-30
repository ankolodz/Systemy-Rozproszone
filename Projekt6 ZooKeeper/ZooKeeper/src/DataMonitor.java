import org.apache.zookeeper.*;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.data.Stat;
import java.util.List;

public class DataMonitor implements Watcher,  AsyncCallback.StatCallback, AsyncCallback.Children2Callback {
    private final ZooKeeper zooKeeper;
    private final String znode;
    boolean dead;
    private final Ilistener listener;
    private int childrenCount = 0;

    public DataMonitor(ZooKeeper zooKeeper, String znode, Ilistener listener) {
        this.zooKeeper = zooKeeper;
        this.znode = znode;
        this.listener = listener;
        zooKeeper.exists(znode,true,this,null);
        zooKeeper.getChildren(znode, true, this, null);
    }
    private int countChildren(String path){
        int childrenCount = 1;
        try {
            for(String child: zooKeeper.getChildren(path, false)){
                childrenCount += countChildren(path + "/" + child);
            }
        } catch (KeeperException.NoNodeException e){//node deleted
        } catch (Exception e) {
            e.printStackTrace();
        }
        return childrenCount;
    }
    public boolean dead(){
        return dead;
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        String path = watchedEvent.getPath();
        if (watchedEvent.getType() == Event.EventType.None) {

            switch (watchedEvent.getState()) {
                case SyncConnected:
                    break;
                case Expired:
                    dead = true;
                    listener.closing(Code.SessionExpired);
                    break;
            }
        }else if(watchedEvent.getType() == Event.EventType.NodeChildrenChanged){
                zooKeeper.getChildren(path, true, this, null);
        }else if (path != null && path.equals(znode)){
                zooKeeper.exists(znode, true, this, null);
                zooKeeper.getChildren(znode, true, this, null);
            }

    }

    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {
        boolean exists;
        switch (rc) {
            case Code.Ok:
                exists = true;
                break;
            case Code.NoNode:
                exists = false;
                break;
            case Code.SessionExpired:
            case Code.NoAuth:
                dead = true;
                listener.closing(rc);
                return;
            default:
                zooKeeper.exists(znode, true, this, null);
                return;
        }
        listener.handleProgram(exists);
    }

    @Override
    public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
            for(String child: children){
                zooKeeper.getChildren(path + "/" + child, true, this, null);
            }
            int childrenCount = countChildren(znode) - 1;
            if(childrenCount > this.childrenCount)
                System.out.println("Added child. Children count = " + childrenCount);
            this.childrenCount = childrenCount;
        }


}
