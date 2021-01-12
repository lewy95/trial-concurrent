package cn.xzxy.lewy.juc.lock;

import java.util.concurrent.TimeUnit;

/**
 * 测试 synchronized 的可重入性
 *
 * 可重入性：同一个线程在外层获得锁之后，在进入内层自动获得锁
 *
 * 结果：
 * t1	 invoked sendSMS          在外层获取锁
 * t1	 ######invoked sendMail   进入内层自动获得锁
 * t2	 invoked sendSMS
 * t2	 ######invoked sendMail
 */
public class TestReentrant1 {
    static Object objLockA = new Object();
    static Object objLockB = new Object();

    public static void main(String[] args) {
        Phone phone = new Phone();

        System.out.println("========== 同步函数 =========");

        new Thread(() -> {
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"t1").start();

        new Thread(() -> {
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"t2").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("========== 同步代码块 =========");

        new Thread(() -> {
            synchronized (objLockA) {
                System.out.println(Thread.currentThread().getName() + "\t outside lock get");
                synchronized (objLockB) {
                    System.out.println(Thread.currentThread().getName() + "\t inside lock get");
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        },"t3").start();

        new Thread(() -> {
            synchronized (objLockB) {
                System.out.println(Thread.currentThread().getName() + "\t outside lock get");
                synchronized (objLockA) {
                    System.out.println(Thread.currentThread().getName() + "\t inside lock get");
                }
            }
        },"t4").start();
    }
}

class Phone {
    public synchronized void sendSMS() throws Exception {
        System.out.println(Thread.currentThread().getName() + "\t invoked sendSMS");
        sendMail();
    }

    public synchronized void sendMail() throws Exception {
        System.out.println(Thread.currentThread().getName() + "\t ######invoked sendMail");
    }
}
