package cn.xzxy.lewy.juc.completableFuture;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 测试 TestCompletableFuture API
 */
public class TestCompletableFuture {

    @Test
    public void testExecutors() {

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
