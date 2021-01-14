package cn.xzxy.lewy.juc.completableFuture;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 测试 TestCompletableFuture API
 * 1. 获取值（get/getNow/join）
 * 2. complete
 * 3. cancel
 * 4. completeExceptionally
 * 5. toCompletableFuture
 * 5. 线程池测试
 */
public class TestCompletableFuture {

    /**
     * 获取值（get / join / getNow）
     */
    @Test
    public void testFetch() {

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Result of the asynchronous computation";
        });

        try {
            // System.out.println(future.get()); // 阻塞，抛出检查性异常
            // System.out.println(future.get(2, TimeUnit.SECONDS)); // 阻塞指定时间，抛出检查性异常
            System.out.println(future.getNow("GetNow")); // 阻塞，若没值返回默认值
            // System.out.println(future.join()); // 阻塞，抛出非检查性异常
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * toCompletableFuture：返回自己
     */
    @Test
    public void testToCompletableFuture() {

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Result of the asynchronous computation";
        });

        try {
            CompletableFuture<String> future2 = future.toCompletableFuture();
            System.out.println(future == future2); // true
            System.out.println(future.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 手动完成
     */
    @Test
    public void testManual_complete() {

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Result of the asynchronous computation";
        });

        // complete(result)告知future立即完成，结果为result，future中未执行的流程将不再执行
        future.complete("ok immediately");
        System.out.println("after complete >> future.isDone:" + future.isDone()); // true
        // complete后的future不能算是异常完成的
        System.out.println("after complete >> future.isCompletedExceptionally:" + future.isCompletedExceptionally()); // false

        try {
            System.out.println(future.get());
            // System.out.println(future.join());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 手动取消
     */
    @Test
    public void testManual_cancel() {

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Result of the asynchronous computation";
        });

        System.out.println("before cancel >> future.isDone:" + future.isDone()); // false
        System.out.println("before cancel >> future.isCompletedExceptionally:" + future.isCompletedExceptionally()); // false
        // 取消异步任务，参数并没有实际意义
        future.cancel(false); // 取消后，当前future算异常完成
        System.out.println("after cancel >> future.isDone:" + future.isDone()); // true
        System.out.println("after cancel >> future.isCompletedExceptionally:" + future.isCompletedExceptionally()); // true

        // try {
        //    // future被取消后无法调用get/join，会抛出 CancellationException 异常
        //    System.out.println(future.get());
        //    // System.out.println(future.join());
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}
    }

    /**
     * 手动完成（异常完成）
     */
    @Test
    public void testManual_exceptional() {

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Result of the asynchronous computation";
        });

        // 给 future 手动抛出一个异常
        CompletableFuture<String> future2 = future.exceptionally(throwable -> "canceled message");
        System.out.println(future.cancel(true)); // true
        System.out.println(future.isCompletedExceptionally()); // true

        try {
            // future异常完成后也无法调用get/join，会抛出 CancellationException 异常
            // System.out.println(future.get());
            System.out.println(future2.get()); // 获取异常信息
            // System.out.println(future2.join());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 线程池测试
     */
    @Test
    public void testThreadPools() {
        // 采用 Executor 的方式维护异步线程
        // 需要手动指定，若不指定默认为 ForkJoinPool
        Executor executor = Executors.newFixedThreadPool(10);
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Result of the asynchronous computation";
        }, executor).handle((s, t) -> {
            System.out.println(s);
            return "ok";
        }).join();
        System.out.println(result);
    }

}
