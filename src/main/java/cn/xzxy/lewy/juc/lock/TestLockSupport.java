package cn.xzxy.lewy.juc.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class TestLockSupport {

    //static ReentrantLock lock = new ReentrantLock();
    //static Condition condition = lock.newCondition();

    public static void main(String[] args) {

        Thread threadA = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\t come in");
            // LockSupport.park(); // 只要B通知了A，这一行有没有都一样
            System.out.println(Thread.currentThread().getName() + "\t running");
        },"a");
        threadA.start();


        Thread threadB = new Thread(() -> {
            LockSupport.unpark(threadA);
            System.out.println(Thread.currentThread().getName() + "\t signal " + threadA.getName());
        },"b");
        threadB.start();
    }
}
