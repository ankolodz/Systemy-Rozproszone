import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Executor implements  Watcher, Runnable, Ilistener{
        private final String znode = "/z";
        private final DataMonitor dataMonitor;
        private final ZooKeeper zooKeeper;
        private Process child;
        private final String[] task;

    public Executor(String port, String[] task) throws IOException {
        this.task = task;
        this.zooKeeper = new ZooKeeper(port,3000,this);
        this.dataMonitor = new DataMonitor(zooKeeper,znode,this);
    }

    public void comandPrompt() {
        System.out.println("Enter show to see structures");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while(true){
            try {
                if (in.readLine().equals("show")) {
                    System.out.println("Print tree");
                    printTree(this.znode);
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }

        }
    }

    private void printTree(String znode) {
        try {
            for(String child : zooKeeper.getChildren(znode,false))
                printTree((znode+"/"+child));
            System.out.println(znode);
        } catch (KeeperException.NoNodeException e){
            System.out.println("Node does not exist");
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        dataMonitor.process(watchedEvent);
    }

    @Override
    public void run() {
        try {
            synchronized (this) {
                while (!dataMonitor.dead()) {
                    wait();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleProgram(boolean exists) {
        try {
            if (!exists) {
                if (child != null) {
                    System.out.println("Killing process");
                    child.destroy();
                    child.waitFor();
                }
                child = null;
            } else {
                System.out.println("Starting child");
                child = Runtime.getRuntime().exec(task);
            }
        } catch (IOException | InterruptedException e) {
        e.printStackTrace();
    }
    }

    @Override
    public void closing(int rc) {
        synchronized (this) {
            notifyAll();
        }
    }
}
