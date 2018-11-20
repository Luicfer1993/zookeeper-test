package watcher;

import com.watcher.ZookeeperWatcher;

/**
 * Created by Administrator on 2018/11/20.
 */
public class test {
    public static void main(String[] args) {
        ZookeeperWatcher zookeeperWatcher = new ZookeeperWatcher();
        zookeeperWatcher.createEphemeral("/cifer","123");
        System.out.println(zookeeperWatcher.getData("/cifer",true));
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
