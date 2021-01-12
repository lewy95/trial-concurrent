package cn.xzxy.lewy.juc.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁简单实现
 *
 * 自旋的本质：循环去获取锁
 */
public class TestSpinLock {

    // 原子引用线程
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    // 加锁
    public void doLock() {
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + "\t come in");

        while (!atomicReference.compareAndSet(null, thread)) {

        }
    }

    // 解锁
    public void doUnlock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(Thread.currentThread().getName() + "\t invoked doUnlock");
    }

    public static void main(String[] args) {
        TestSpinLock spinLock = new TestSpinLock();

        // A 线程
        new Thread(() -> {
            spinLock.doLock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLock.doUnlock();
        },"AA").start();

        // 暂停两秒，方便看效果
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // B 线程
        new Thread(() -> {
            spinLock.doLock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLock.doUnlock();
        },"BB").start();
    }
}
