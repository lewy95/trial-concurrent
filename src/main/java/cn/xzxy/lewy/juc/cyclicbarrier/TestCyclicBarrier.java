package cn.xzxy.lewy.juc.cyclicbarrier;

import java.util.concurrent.*;

/**
 * 赛马案例：
 * 两匹马（即两个线程）
 * 线程一：马1，肚子不舒服，需要5S调整
 * 线程二：马2
 * 只有两匹马同时准备好了才能跑
 */
public class TestCyclicBarrier {


    public static void main(String[] args) throws InterruptedException,ExecutionException{
        //如果将参数改为3，但是下面只加入了2个选手，这永远等待下去
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(new Thread(new Horse1(cyclicBarrier, "horse1")));
        executor.submit(new Thread(new Horse2(cyclicBarrier, "horse2")));

        //Future f1 = executor.submit(new Thread(new Horse1(cyclicBarrier, "horse1")));
        //System.out.println("f1: " + f1.get());
        //Future f2 = executor.submit(new Thread(new Horse2(cyclicBarrier, "horse2")));
        //System.out.println("f2: " + f2.get());
        executor.shutdown();
    }
}

class Horse1 implements Runnable {
    private CyclicBarrier barrier;

    private String name;

    Horse1(CyclicBarrier barrier, String name) {
        super();
        this.barrier = barrier;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            System.out.println(name + " 拉肚子了");
            Thread.sleep(5000);
            System.out.println(name + " 准备好了...");
            //await方法，在所有参与者都已经在此 barrier 上调用 await 方法之前，将一直等待
            //会产生阻塞，阻塞放开的条件是初始数量减为0，await()每调用一次，初始数量就会递减1
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println(name + " 起跑！");
    }
}

class Horse2 implements Runnable {
    private CyclicBarrier barrier;

    private String name;

    Horse2(CyclicBarrier barrier, String name) {
        super();
        this.barrier = barrier;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            System.out.println(name + " 准备好了...");
            //barrier的await方法，在所有参与者都已经在此 barrier 上调用 await 方法之前，将一直等待。
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println(name + " 起跑！");
    }
}