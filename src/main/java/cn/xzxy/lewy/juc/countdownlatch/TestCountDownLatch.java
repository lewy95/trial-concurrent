package cn.xzxy.lewy.juc.countdownlatch;

import java.util.concurrent.*;

/**
 * 盖房子案例
 * 三个人
 * 一个人买钢筋，一个人能买水泥，一个人买沙子
 * 都买好了开始盖房子
 */
public class TestCountDownLatch {
    public static void main(String[] args) throws InterruptedException {
        //创建闭锁时，要指定一个初始的数量，这个要根据实际情况来定
        CountDownLatch cdl = new CountDownLatch(3);
        new Thread(new G1(cdl)).start();
        new Thread(new G2(cdl)).start();
        new Thread(new G3(cdl)).start();

        //await()方法会产生阻塞，等待初始的数量到达0，即线程都完成
        cdl.await();
        System.out.println("我要盖房子了");

    }
}

class G1 implements Runnable {
    public CountDownLatch cdl;

    public G1(CountDownLatch cdl) {
        this.cdl = cdl;
    }

    @Override
    public void run() {
        System.out.println("买钢筋去了");
        //闭锁的countdown()方法，每调用一次，初始数量减一，当数量变为0时，阻塞放开
        cdl.countDown();
    }
}

class G2 implements Runnable {
    public CountDownLatch cdl;

    public G2(CountDownLatch cdl) {
        this.cdl = cdl;
    }

    @Override
    public void run() {
        System.out.println("买水泥去了");
        cdl.countDown();
    }
}

class G3 implements Runnable {
    public CountDownLatch cdl;

    public G3(CountDownLatch cdl) {
        this.cdl = cdl;
    }

    @Override
    public void run() {
        System.out.println("买沙子去了");
        cdl.countDown();
    }
}