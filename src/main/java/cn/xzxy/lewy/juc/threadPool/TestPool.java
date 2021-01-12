package cn.xzxy.lewy.juc.threadPool;

import org.junit.Test;

import java.util.concurrent.*;

public class TestPool {

    /**
     * 参数一：corePoolSize:核心线程数
     * 参数二：maximumPoolSize：最大线程数
     * 参数三：keepAliveTime：存活时间
     * 参数四：TimeUnit：时间单位
     * 参数五：workQueue队列
     * 线程池作用：避免线程的重复创建和摧毁，可以节省资源
     * 注意：
     * 1.当线程池小于corePoolSize时，新提交任务将创建一个新线程执行任务，即使此时线程池中存在空闲线程。
     * 2.当线程池达到corePoolSize时，新提交任务将被放入workQueue中，等待线程池中任务调度执行
     * 3.当workQueue已满，且maximumPoolSize>corePoolSize时，新提交任务会创建临时线程执行任务，
     * 所以核心线程数要小于最大线程数
     * 4.当提交任务数超过maximumPoolSize时，新提交任务由RejectedExecutionHandler处理
     * 5.当线程池中超过corePoolSize线程，空闲时间达到keepAliveTime时，关闭空闲线程
     * 6.当设置allowCoreThreadTimeOut(true)时，线程池中corePoolSize线程空闲时间达到keepAliveTime也将关闭
     */
    @Test
    public void testCreate() {
        ExecutorService es = new ThreadPoolExecutor(
                3, 6, 3000, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(5),
                //当提交任务数超过maximumPoolSize+workQueue之和时，任务会交给RejectedExecutionHandler来处理
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        System.out.println("当前队列已满，请等待");
                    }
                });
        for (int i = 0; i < 12; i++) {
            //将线程放入线程池启动
            es.execute(new ExRunner());
        }
        //关闭线程池，调用此方法后，不会接受新的线程
        es.shutdown();
        while (true) ;
    }
}

class ExRunner implements Runnable {

    @Override
    public void run() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
