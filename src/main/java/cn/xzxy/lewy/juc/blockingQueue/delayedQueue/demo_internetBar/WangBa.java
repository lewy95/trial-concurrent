package cn.xzxy.lewy.juc.blockingQueue.delayedQueue.demo_internetBar;

import java.util.concurrent.DelayQueue;

public class WangBa implements Runnable {

    private DelayQueue<WangMin> queue = new DelayQueue<WangMin>();

    public boolean yingye = true; //是否营业

    /**
     * 上机
     */
    public void shangji(String name, String id, int money) {
        WangMin man = new WangMin(name, id, 1000 * money + System.currentTimeMillis());
        System.out.println("网名" + man.getName()
                + " 身份证" + man.getId() + "交钱" + money + "块,开始上机...");
        this.queue.add(man);
    }

    /**
     * 下机
     */
    public void xiaji(WangMin man) {
        System.out.println("网名" + man.getName()
                + " 身份证" + man.getId() + "时间到下机...");
    }

    @Override
    public void run() {
        while (yingye) {
            try {
                WangMin man = queue.take();
                xiaji(man);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println("网吧开始营业");
            WangBa siyu = new WangBa();
            Thread shangwang = new Thread(siyu);
            shangwang.start();

            siyu.shangji("路人甲", "123", 1);
            siyu.shangji("路人乙", "234", 10);
            siyu.shangji("路人丙", "345", 5);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
