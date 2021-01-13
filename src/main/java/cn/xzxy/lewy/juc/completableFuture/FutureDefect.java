package cn.xzxy.lewy.juc.completableFuture;

import java.util.concurrent.*;

/**
 * Future 局限性：
 * get() 方法会产生阻塞，需要当异步任务执行完才调用
 * 解决方法：
 * 1. 轮询判断是否完成，完成后再get；
 * 2. 给get方法指定等待时间，超过会抛出 TimeoutException 异常；
 * 但很明显，这两种方式都不是理想的解决方案，为此，JDK1.8中提供了 CompletableFuture接口来解决这个问题
 */
public class FutureDefect {

    public static void main(String[] args) {
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<Integer> f = es.submit(() -> {
            // 假设进行长时间(5s)的异步计算
            TimeUnit.SECONDS.sleep(5);
            return 200;
        });

        // 法一：轮询判断是否完成，完成后再get
//        while (true) {
//            if (f.isDone()) {
//                try {
//                    // get 会产生阻塞，直到异步计算完成
//                    System.out.println(f.get());
//                    es.shutdown();
//                    break;
//                } catch (InterruptedException | ExecutionException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

        // 法二：指定等待时间，超过会抛出 TimeoutException 异常
        try {
            System.out.println(f.get(3, TimeUnit.SECONDS));
        } catch (Exception e) {
            e.printStackTrace();
            // java.util.concurrent.TimeoutException
        }
    }

}
