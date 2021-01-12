package cn.xzxy.lewy.juc.semaphore;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 案例1 ：当作锁使用
 */
public class TestSemaphore {

    // 定义了信号量为3，即只有3个许可证
    private static final Semaphore semaphore = new Semaphore(3);
    // 定义一个线程池
    private static final ThreadPoolExecutor threadPool =
            new ThreadPoolExecutor(5, 10, 60,
                    TimeUnit.SECONDS, new LinkedBlockingQueue<>());

    private static class InformationThread implements Runnable {
        private final String name;
        private final int age;

        InformationThread(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public void run() {
            try {
                // 申请信号量
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName() + ":hello，i am " + name + ", i am " + age + ", now is " + System.currentTimeMillis());
                Thread.sleep(3 * 1000);
                System.out.println(name + " prepare for leave，now is " + System.currentTimeMillis());
                // 释放信号量
                semaphore.release();
                //System.out.println(name + " is revert seat， remaining seats:" + semaphore.availablePermits());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        String[] name = {"lewy", "ivan", "muller", "thiago", "comman", "serigo", "leno"};
        int[] age = {26, 27, 33, 45, 19, 23, 41};
        for (int i = 0; i < 7; i++) {
            threadPool.execute(new Thread(new InformationThread(name[i], age[i])));
        }
        threadPool.shutdown();
    }

}