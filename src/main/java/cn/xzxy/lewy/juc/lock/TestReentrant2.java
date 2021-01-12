package cn.xzxy.lewy.juc.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试 ReentrantLock 的可重入性
 *
 * 可重入性：同一个线程在外层获得锁之后，在进入内层自动获得锁
 *
 * 结果：
 * t1	 invoked get
 * t1	 ######invoked set
 * t2	 invoked get
 * t2	 ######invoked set
 */
public class TestReentrant2 {

    public static void main(String[] args) {

        Model model = new Model();

        Thread thread1 = new Thread(model);
        thread1.setName("t1");
        Thread thread2 = new Thread(model);
        thread2.setName("t2");
        thread1.start();
        thread2.start();
    }
}

class Model implements Runnable{

    Lock lock = new ReentrantLock();

    public void get() {
        lock.lock();
        // lock.lock(); // 这里写多个lock，即多加锁几次 但不论加几个lock，必须要释放掉几个unlock
        try {
            System.out.println(Thread.currentThread().getName() + "\t invoked get");
            set();
        } finally {
            lock.unlock();
            // lock.unlock();
        }
    }

    public void set() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t ######invoked set");
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {
        get();
    }
}


