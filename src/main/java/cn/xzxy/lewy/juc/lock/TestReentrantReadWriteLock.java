package cn.xzxy.lewy.juc.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁：part1 读读 共享
 *
 * 读写锁：更细粒度的锁，重入锁和同步代码块一旦锁住，同一时刻，只能有一个线程进行访问
 * 读写锁不同，读写锁本质上有两把锁，分别是读锁和写锁，
 * 在读锁下，多个线程可以并发访问资源
 * 在写锁下，才需要按顺序执行
 * 适用场景：读多，写少的场景
 *
 */
public class TestReentrantReadWriteLock {

    public static void main(String[] args) {
        final MyTask myTask = new MyTask();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                myTask.read();
            }
        });
        t1.setName("t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                myTask.read();
            }
        });
        t2.setName("t2");

        t1.start();
        t2.start();
    }
}

class MyTask {

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    void read() {
        try {
            lock.readLock().lock();
            // 两个读操作，可以一起执行
            System.out.println(Thread.currentThread().getName() + " start");
            Thread.sleep(3 * 1000);
            System.out.println(Thread.currentThread().getName() + " end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();
        }
    }
}


