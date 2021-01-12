package cn.xzxy.lewy.juc.blockingQueue;

import java.util.Random;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * plot:
 * 生产者1秒生产一个数据
 * 消费者3秒消费一个数据
 * 现在有1个生产者2个消费者
 *
 * 按照常规思想来说生产的数据会多出来。
 * 然后使用SynchronousQueue会生产一个消费一个
 */
public class TestSynchronousQueue {

    public static void main(String[] args) {
        SynchronousQueue<Integer> queue = new SynchronousQueue<Integer>();

        new Thread(new Customer1(queue)).start();
        new Thread(new Customer(queue)).start();
        new Thread(new Product(queue)).start();
    }

    static class Product implements Runnable {
        SynchronousQueue<Integer> queue;

        public Product(SynchronousQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                int rand = new Random().nextInt(1000);
                System.out.println("生产了一个产品：" + rand);
                System.out.println("等待三秒后运送出去...");
                try {
                    queue.put(rand);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Customer implements Runnable {
        SynchronousQueue<Integer> queue;

        public Customer(SynchronousQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("0消费了一个产品:" + queue.take());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("------------------------------------------");
            }
        }
    }

    static class Customer1 implements Runnable {
        SynchronousQueue<Integer> queue;

        public Customer1(SynchronousQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("1消费了一个产品:" + queue.take());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("------------------------------------------");
            }
        }
    }

}
