package cn.xzxy.lewy.juc.semaphore;

import java.util.concurrent.Semaphore;

/**
 * 案例2 ：线程间通信
 */
public class TestSemaphore2 {
    public static void main(String[] args) {
        // 线程间进行通信
        Semaphore semaphore = new Semaphore(1);
        Thread t1 = new Thread(new SendingThread(semaphore,"SendingThread"));
        Thread t2 = new Thread(new ReceivingThread(semaphore,"ReceivingThread"));
        t1.start();
        t2.start();
    }
}

class SendingThread implements Runnable {
    private Semaphore semaphore;
    private String name;

    SendingThread(Semaphore semaphore,String name) {
        this.semaphore = semaphore;
        this.name = name;
    }

    public void run() {
        try {
            semaphore.acquire();
            System.out.println("i am " + name);
            Thread.sleep(1000);

        } catch (Exception e) {
            e.printStackTrace();
        }
        semaphore.release();
    }
}

class ReceivingThread extends Thread {
    private Semaphore semaphore;
    private String name;

    ReceivingThread(Semaphore semaphore,String name) {
        this.semaphore = semaphore;
        this.name = name;
    }

    public void run() {
        try {
            semaphore.acquire();
            System.out.println("i am " + name);
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        semaphore.release();
    }
}
