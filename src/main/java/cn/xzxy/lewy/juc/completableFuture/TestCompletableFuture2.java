package cn.xzxy.lewy.juc.completableFuture;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 测试 TestCompletableFuture API
 * 1. allOf
 * 2. anyOf
 */
public class TestCompletableFuture2 {

    @Test
    public void testAllOf() {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "yy";
        });
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "ym";
        });
        // 在所有 CompletableFuture 完成后结束，并返回一个 CompletableFuture
        // CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(future1, future2);
        CompletableFuture.allOf(future1, future2).thenApply(v ->
                Stream.of(future1, future2)
                        .map(CompletableFuture::join)
                        .collect(Collectors.joining(" &&& ")))
                .thenAccept(System.out::print).join();
    }

    @Test
    public void testAnyOf() {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "sy";
        });
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "ly";
        });
        // 在任一 CompletableFuture 完成后结束，并返回一个 CompletableFuture
        CompletableFuture<Object> combinedFuture = CompletableFuture.anyOf(future1, future2);
        try {
            System.out.println(combinedFuture.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // getNow
    // toCompletableFuture

}
