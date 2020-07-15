package zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

public class Master implements Watcher {
    ZooKeeper zk;
    String hostPort;

    Master(String hostPort) {
        this.hostPort = hostPort;
    }

    void startZK() {
        try {
            zk = new ZooKeeper(hostPort, 15000, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void stopZK() throws Exception {
        zk.close();
    }

    public void process(WatchedEvent watchedEvent) {
        System.out.println(watchedEvent);
    }

    public static void main(String[] args) throws Exception {
        Master master = new Master("127.0.0.1:2181");
        master.startZK();
        Thread.sleep(60000);
        master.stopZK();
    }
}
