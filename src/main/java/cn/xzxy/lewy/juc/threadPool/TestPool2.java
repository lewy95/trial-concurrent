package cn.xzxy.lewy.juc.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestPool2 {
    public static void main(String[] args) {
		/*
		 * new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
                                      new SynchronousQueue<Runnable>());
         * 特点：
         * 1、没有核心线程
         * 2、都是临时线程，且无限大
         * 3、大池子，小队列
         * 4、适用场景：高并发，短请求
         * 5、缺点：如果是高并发，长请求，由于线程数量一直增加，增加了服务器压力，有可能造成内存溢出
         *    线程的创建和销毁也会浪费一定的资源
		 */
        ExecutorService pool1 = Executors.newCachedThreadPool();

        /*
		 * new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS,
                                      new LinkedBlockingQueue<Runnable>());
         *  特点：
         *  1、全部是核心线程数
         *  2、没有存活时间，因为没有临时线程
         *  3、大队列，小池子
         *  4、适用场景：可以用于缓解高峰的压力，利用队列进行缓解
         *  5、由于核心线程无需销毁，所以性能很好
         *  6、缺点：有的用户不能得到及时处理
		 */
        ExecutorService pool2 = Executors.newFixedThreadPool(10);
    }

    /**
     * 注意：最好不要创建小池子，小队列的线程池。
     */
}
