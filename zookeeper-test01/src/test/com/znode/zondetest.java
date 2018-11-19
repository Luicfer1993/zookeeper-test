package znode;

import com.znode.ZookeeperCrud;

/**
 * Created by Administrator on 2018/11/19.
 */
public class zondetest {
    public static void main(String[] args) {
        ZookeeperCrud zookeeperCrud=new ZookeeperCrud();
        zookeeperCrud.createPersistent("/lucifer","2018");
        zookeeperCrud.createPersistent("/lucifer/cifer","20182018");
//        zookeeperCrud.createEphemeral("/lucifer1","2018");
        zookeeperCrud.setData("/lucifer","2018-11-19");
        System.out.println(zookeeperCrud.getData("/lucifer"));
//        zookeeperCrud.delete("/lucifer");
        System.out.println(zookeeperCrud.exists("/lucifer"));
    }
}
