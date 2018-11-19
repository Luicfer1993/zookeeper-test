package com.znode;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * Created by Administrator on 2018/11/19.
 */
public class ZookeeperCrud {
    private String connectString="192.168.153.130:2181";

    private ZooKeeper zookeeper;

    //构造方法
    public ZookeeperCrud() {
        try {
            zookeeper=new ZooKeeper(connectString,5000,null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //创建持久节点
    public String createPersistent(String path,String data){
        try {
            return  zookeeper.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  null;
    }

    //创建临时节点
    public String createEphemeral(String path,String data){
        try {
            return zookeeper.create(path,data.getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 获取信息
     * @param path
     * @return
     */
    public String getData(String path){
        byte data[] = new byte[0];
        try {
            data = zookeeper.getData(path,false,null);
            data = (data == null)? "null".getBytes() : data;
            return new String(data);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    /***
     * 更新信息
     * @param path
     * @param data
     * @return
     */
    public Stat setData(String path, String data){
        try {
            return zookeeper.setData(path, data.getBytes(), -1);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 是否存在
     * @param path
     * @return
     */
    public Stat exists(String path) {
        try {
            return zookeeper.exists(path,false);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 删除
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
}
