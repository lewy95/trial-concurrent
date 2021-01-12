package cn.xzxy.lewy.juc.countdownlatch;

import java.util.concurrent.CountDownLatch;

public class TestCountDownLatch3 {

    public static void main(String[] args) throws InterruptedException {

        //ExecutorService es = Executors.newFixedThreadPool(3);

        CountDownLatch cdl = new CountDownLatch(3);
        new Thread(new Part1(cdl)).start();
        new Thread(new Part2(cdl)).start();
        new Thread(new Part3(cdl)).start();

        cdl.await();
        System.out.println("task all down");

    }
}

class Part1 implements Runnable {

    private CountDownLatch cdl;

    public Part1(CountDownLatch cdl) {
        this.cdl = cdl;
    }

    @Override
    public void run() {
        System.out.println("accomplish task 1");
        cdl.countDown();
    }
}

class Part2 implements Runnable {

    private CountDownLatch cdl;

    public Part2(CountDownLatch cdl) {
        this.cdl = cdl;
    }

    @Override
    public void run() {
        System.out.println("accomplish task 2");
        cdl.countDown();
    }
}

class Part3 implements Runnable {

    private CountDownLatch cdl;

    public Part3(CountDownLatch cdl) {
        this.cdl = cdl;
    }

    @Override
    public void run() {
        System.out.println("accomplish task 3");
        cdl.countDown();
    }
}
