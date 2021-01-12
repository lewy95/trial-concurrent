package cn.xzxy.lewy.juc.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 打麻将案例：
 * 四个人（即四个线程）
 * 线程一：人1，先吃饭5s
 * 线程二：人2，迟到了10s
 * 线程三：人3
 * 线程四：人4
 * 只有四个人同时准备好了才能跑
 */
public class TestCyclicBarrier2 {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(4);
        ExecutorService executor = Executors.newFixedThreadPool(4);

        executor.submit(new Thread(new Person1(cyclicBarrier, "person1")));
        executor.submit(new Thread(new Person2(cyclicBarrier, "person2")));
        executor.submit(new Thread(new Person3(cyclicBarrier, "person3")));
        executor.submit(new Thread(new Person4(cyclicBarrier, "person4")));
        executor.shutdown();
    }
}

class Person1 implements Runnable {
    private CyclicBarrier barrier;

    private String name;

    public Person1(CyclicBarrier barrier, String name) {
        super();
        this.barrier = barrier;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            System.out.println(name + " 迟到了");
            Thread.sleep(10000);
            System.out.println(name + " 准备好了...");
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println(name + " 开牌！");
    }
}

class Person2 implements Runnable {
    private CyclicBarrier barrier;

    private String name;

    public Person2(CyclicBarrier barrier, String name) {
        super();
        this.barrier = barrier;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            System.out.println(name + " 吃饭去了");
            Thread.sleep(5000);
            System.out.println(name + " 准备好了...");
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println(name + " 开牌！");
    }
}

class Person3 implements Runnable {
    private CyclicBarrier barrier;

    private String name;

    public Person3(CyclicBarrier barrier, String name) {
        super();
        this.barrier = barrier;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            System.out.println(name + " 准备好了...");
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println(name + " 开牌！");
    }
}


class Person4 implements Runnable {
    private CyclicBarrier barrier;

    private String name;

    public Person4(CyclicBarrier barrier, String name) {
        super();
        this.barrier = barrier;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            System.out.println(name + " 准备好了...");
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println(name + " 开牌！");
    }
}