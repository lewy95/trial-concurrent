package cn.xzxy.lewy.juc.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 原子性：
 * 案例：使用两个线程对num进行++操作，分别累加10000次，最后打印结果
 * 分析：对于全局变量的数值类型操作 num++，若没有加synchronized关键字则是线程不安全的
 *      num++解析为num=num+1，明显，这个操作不具备原子性，多线程时必然会出现问题
 * 原来的解决方法：
 * 1. volatile修饰的变量能够在线程间保持可见性，能被多个线程同时读又能保证只被单线程写，
 *    且不会读取到过期值(由happen-before原则决定的)
 * 2. volatile修饰的写入操作总是优先于读操作，
 *    即使多个线程同时修改volatile变量字段，总能保证获取到最新的值。
 * 3. 但volatile仅仅保证变量在线程间保持可见性，却依然不能保证非原子性的操作(多试几次依然得不到20000)
 * 现在的解决方法：
 * 使用原子类型，atomic包下提供了AtomicBoolean/AtomicLong/AtomicInteger三个原子更新基本类型。
 */
public class TestAtomic {
    //定义原子性整形
    public static AtomicInteger num = new AtomicInteger(0);  //结果为20000

    //定义普通整形
    //public static Integer num = 0;  //结果远小于20000

    public static void main(String[] args) throws InterruptedException {
        //闭锁
        CountDownLatch countDownLatch = new CountDownLatch(2);

        new Thread(new T3(countDownLatch)).start();
        new Thread(new T4(countDownLatch)).start();

        countDownLatch.await();
        System.out.println("num = " + num);
    }
}

class T3 implements Runnable {

    private CountDownLatch countDownLatch;

    T3(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {

        for (int i = 0; i < 10000; i++) {
            //给AtomicInteger原子的加上指定的delta值并返回加后的值
            TestAtomic.num.addAndGet(1);
            //TestAtomic.num++;
        }
        countDownLatch.countDown();
    }
}

class T4 implements Runnable {

    private CountDownLatch countDownLatch;

    T4(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            TestAtomic.num.addAndGet(1);
            //TestAtomic.num++;
        }
        countDownLatch.countDown();
    }
}
