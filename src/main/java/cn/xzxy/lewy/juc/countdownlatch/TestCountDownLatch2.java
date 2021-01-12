package cn.xzxy.lewy.juc.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * 做饭的实例
 * 三个线程：
 * 线程1：买菜
 * 线程2：买锅
 * 线程3：炒菜
 * 只有当都买来了时才能开始炒菜
 */
public class TestCountDownLatch2 {
    public static void main(String[] args) throws Exception {
        //创建闭锁时，要指定一个初始的数量，这个要根据实际情况来定
        CountDownLatch cdl = new CountDownLatch(2);
        new Thread(new BuyFood(cdl)).start();
        new Thread(new BuyPot(cdl)).start();

        //await()方法会产生阻塞，等待初始的数量到达0，即线程都完成
        cdl.await();
        System.out.println("开始炒菜");
    }
}

class BuyFood implements Runnable {
    public CountDownLatch cdl;

    public BuyFood(CountDownLatch cdl) {
        this.cdl = cdl;
    }

    @Override
    public void run() {
        System.out.println("菜买回来了");
        //闭锁的countdown()方法，每调用一次，初始数量减一，当数量变为0时，阻塞放开
        cdl.countDown();
    }
}

class BuyPot implements Runnable {
    public CountDownLatch cdl;

    public BuyPot(CountDownLatch cdl) {
        this.cdl = cdl;
    }

    @Override
    public void run() {
        System.out.println("锅买回来了");
        //闭锁的countdown()方法，每调用一次，初始数量减一，当数量变为0时，阻塞放开
        cdl.countDown();
    }

}