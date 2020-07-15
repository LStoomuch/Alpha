package zookeeper;

import java.io.IOException;
import java.util.*;
import org.apache.zookeeper.AsyncCallback.DataCallback;
import org.apache.zookeeper.AsyncCallback.StringCallback;
import org.apache.zookeeper.AsyncCallback.VoidCallback;
import org.apache.zookeeper.*;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.AsyncCallback.ChildrenCallback;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.data.Stat;
import org.slf4j.*;

public class Worker implements Watcher {
    private static final Logger LOG = LoggerFactory.getLogger(Worker.class);
    ZooKeeper zk;
    String hostPort;
    String serverId = Integer.toHexString(7);

    Worker(String hostPort) {
        this.hostPort = hostPort;
    }

    void start() throws IOException {
        zk = new ZooKeeper(hostPort, 15000, this);
    }

    public void process(WatchedEvent event) {
        LOG.info(event.toString() + ", " + hostPort);
        System.out.println(event.toString() + ", " + hostPort);
    }

    void register() {
        zk.create("/workers/worker-"+serverId,
                "Idle".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL,
                createWorkerCallback,
                null
        );
    }

    StringCallback createWorkerCallback = new StringCallback() {
        public void processResult(int rc, String path, Object ctx,
                                  String name) {
            switch (Code.get(rc)) {
                case CONNECTIONLOSS:
                    System.out.println("已连接");
                    register();
                    break;
                case OK:
                    System.out.println("创建成功");
                    break;
                case NODEEXISTS:
                    System.out.println("已注册");
                    break;
                default:
                    System.out.println("wrong");
            }
        } };

    public static void main(String args[]) throws Exception {
        Worker w = new Worker("127.0.0.1:2181");
        w.start();
        w.register();
        Thread.sleep(30000);
    }
}
