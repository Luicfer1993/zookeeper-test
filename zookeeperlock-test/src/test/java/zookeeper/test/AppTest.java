package zookeeper.test;

import com.ZkLock;

/**
 * Unit test for simple App.
 */
public class AppTest implements Runnable{

    ZkLock zkLock = new ZkLock("192.168.153.130:2181","/cifer");
    static int i = 0;

    public static void main(String[]args) throws InterruptedException {
        AppTest at = new AppTest();
        Thread t1 = new Thread(at);
        Thread t2 = new Thread(at);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }

    @Override
    public void run() {
        for(int j=0; j<1000; j++){
            zkLock.lock();
            i++;
            zkLock.unlock();
        }
    }
}
