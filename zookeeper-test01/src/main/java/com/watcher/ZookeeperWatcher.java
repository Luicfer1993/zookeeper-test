package com.watcher;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * Created by Administrator on 2018/11/19.
 */
public class ZookeeperWatcher implements Watcher {

    private String connectString="192.168.153.130:2181";

    private ZooKeeper zookeeper;

    //构造方法
    public ZookeeperWatcher() {
        try {
            zookeeper=new ZooKeeper(connectString,5000,this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***
     * 创建持久节点
     * @param path
     * @param data
     * @return
     */
    public String createPersistent(String path,String data){
        try {
            return  zookeeper.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  null;
    }

    /***
     * 创建临时节点
     * @param path
     * @param data
     * @return
     */
    public String createEphemeral(String path,String data){
        try {
            return  zookeeper.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  null;
    }

    /**
     * 更新信息
     * @param path
     * @param watcher
     * @return
     */
    public String getData(String path,boolean watcher){
        byte data[] = new byte[0];
        try {
            data = zookeeper.getData(path,watcher,null);
            data = (data == null)? "null".getBytes() : data;
            return new String(data);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更新信息
     * @param path
     * @param data
     * @return
     */
    public Stat setData(String path, String data){
        try {
            return zookeeper.setData(path,data.getBytes(), -1);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查看是否存在
     * @param path
     * @param watcher
     * @return
     */
    public Stat exists(String path, boolean watcher){
        try {
            return zookeeper.exists(path,watcher);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除节点
     * @param path
     */
    public void delete(String path){
        try {
            zookeeper.delete(path,-1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent event) {
        // 连接状态
        Event.KeeperState keeperState = event.getState();
        // 事件类型
        Event.EventType eventType = event.getType();
        // 受影响的path
        String path = event.getPath();
        //step 1:
        //  System.out.println("连接状态:"+keeperState+",事件类型："+eventType+",受影响的path:"+path);

        //step:2
        try {
            if(null!=this.exists("/cifer",true)) {
                System.out.println("内容:"+ this.getData("/cifer", true));
            }
            System.out.println("连接状态:"+keeperState+",事件类型："+eventType+",受影响的path:"+path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--------------------");
    }
}
