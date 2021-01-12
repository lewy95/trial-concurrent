package cn.xzxy.lewy.juc.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * part3  读写 互斥
 */
public class TestReentrantReadWriteLock3 {

    public static void main(String[] args) {
        final MyTask3 myTask = new MyTask3();

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
                myTask.write();
            }
        });
        t2.setName("t2");

        t1.start();

        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t2.start();
    }
}

class MyTask3 {

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock lock2 = new ReentrantReadWriteLock(true);


    void read() {
        try {
            lock.readLock().lock();
            System.out.println(Thread.currentThread().getName() + " start");
            Thread.sleep(5 * 1000);
            System.out.println(Thread.currentThread().getName() + " end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();
        }
    }

    public void write() {
        try {
            lock.writeLock().lock();
            System.out.println(Thread.currentThread().getName() + " start");
            Thread.sleep(4 * 1000);
            System.out.println(Thread.currentThread().getName() + " end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
    }
}
