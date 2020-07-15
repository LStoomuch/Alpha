package threads.myLock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Cache {
    static Map<String, Object> map = new HashMap<String, Object>();
    static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    static Lock r = rwl.readLock();
    static Lock w = rwl.writeLock();
    // 获取一个key对应的value
    public static final Object get(String key) {
        r.lock();
        try {
            Thread.sleep(10000);
            return map.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            r.unlock();
        }
    }
    // 设置key对应的value，并返回旧的value
    public static final Object put(String key, Object value) {
        w.lock();
        try {
            System.out.println("get write lock");
            Thread.sleep(1000);
            System.out.println("sleep 10s");
            return map.put(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            w.unlock();
        }
    }
    // 清空所有的内容
    public static final void clear() {
        w.lock();
        try {
            map.clear();
        } finally {
            w.unlock();
        }
    }
}
