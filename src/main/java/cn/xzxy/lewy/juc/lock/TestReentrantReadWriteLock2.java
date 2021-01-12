package cn.xzxy.lewy.juc.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * part2  写写互斥
 */
public class TestReentrantReadWriteLock2 {

    public static void main(String[] args) {
        final MyTask2 myTask = new MyTask2();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                myTask.write();
            }
        });
        t1.setName("t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                myTask.write();
            }
        });
        t2.setName("t2");

        t1.start();
        t2.start();
    }
}

class MyTask2 {

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    void write() {
        try {
            lock.writeLock().lock();
            // 当t1执行了写操作，t2再执行写操作会等待，直到t1的写操作结束
            System.out.println(Thread.currentThread().getName() + " start");
            Thread.sleep(3 * 1000);
            System.out.println(Thread.currentThread().getName() + " end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
    }
}


