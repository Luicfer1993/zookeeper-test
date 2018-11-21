package com;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/11/21.
 */
public class ZkLock {
    private ZooKeeper zookeeper;
    private String path="/cifer";
    private CountDownLatch latch=null;

    public ZkLock(String host, String path){
        try {
            this.zookeeper = new ZooKeeper(host, 5000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.path = path;
    }

    //加锁
    public void lock() {

        try {
            //创建临时节点
            zookeeper.create(path, path.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        } catch (Exception e) {
            this.latch = new CountDownLatch(1);
            try {
                //等待，这里应该一直等待其他线程释放锁
                this.latch.await(1000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            this.latch = null;
            lock();
        }
    }

    //释放锁
    public void unlock(){
        try {
            zookeeper.delete(path, -1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

}
