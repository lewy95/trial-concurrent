package cn.xzxy.lewy.juc.completableFuture;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * 测试 CompletableStage 中定义的各种类型接口
 */
public class TestCompletableStage {

    /**
     * Apply 事件：对于上一阶段结果进行转换后返回
     */
    @Test
    public void thenApply() {
        String result = CompletableFuture.supplyAsync(() -> "hello").thenApply(s -> s + " world").join();
        System.out.println(result);
    }

    /**
     * Accept 事件：对于上一阶段结果进行消耗
     */
    @Test
    public void thenAccept() {
        CompletableFuture.supplyAsync(() -> "hello").thenAccept(s -> System.out.println(s + " world"));
    }

    /**
     * Run 事件：对上一阶段的结果不关心，执行下一个操作
     * 注意：入参是一个 runnable 实例
     */
    @Test
    public void thenRun() throws InterruptedException {
        CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenRun(() -> System.out.println("hello world"));

        // 用于测试，查看返回结果
        TimeUnit.SECONDS.sleep(5);
    }

    /**
     * Combine 事件：结合两个 CompletionStage 的结果，进行转化后返回
     * 需要上一阶段处理的返回值，并且还需要other代表的CompletionStage的返回值，利用这两个返回值，进行转换后返回指定类型的值
     */
    @Test
    public void thenCombine() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "world";
        }), (s1, s2) -> s1 + " " + s2).join();
        System.out.println(result);
    }

    /**
     * AcceptBoth 事件：结合两个 CompletionStage 的结果，进行消耗
     * 需要上一阶段处理的返回值，还需要other代表的CompletionStage的返回值，利用这两个返回值，进行消耗
     */
    @Test
    public void thenAcceptBoth() throws InterruptedException {
        CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenAcceptBoth(CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "world";
        }), (s1, s2) -> System.out.println(s1 + " " + s2));   // 结果为 hello world
        // 用于测试，查看返回结果
        TimeUnit.SECONDS.sleep(10);
    }

    /**
     * RunAfterBoth 事件：在两个 CompletionStage 都运行完执行，不关心之前阶段的结果
     */
    @Test
    public void runAfterBoth() throws InterruptedException {
        CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s1";
        }).runAfterBothAsync(CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s2";
        }), () -> System.out.println("hello world")); // 结果为 hello world
        // 用于测试，查看返回结果
        TimeUnit.SECONDS.sleep(10);
    }

    /**
     * ApplyToEither 事件：对于两个CompletionStage，哪个计算的快就用它结果进行下一步的转化操作
     */
    @Test
    public void applyToEither() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s1";
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello world";
        }), s -> s).join();
        System.out.println(result);   // 结果为 hello world
    }

    /**
     * AcceptEither 事件：对于两个CompletionStage，哪个计算的快就用它结果进行下一步的消耗操作
     */
    @Test
    public void acceptEither() throws InterruptedException {
        CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s1";
        }).acceptEither(CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello world";
        }), System.out::println); // 结果为 hello world
        // 用于测试，查看返回结果
        TimeUnit.SECONDS.sleep(10);
    }

    /**
     * RunAfterEither 事件：对于两个CompletionStage，任何一个完成都会进行下一步操作，不关心下一阶段的结果
     */
    @Test
    public void runAfterEither() throws InterruptedException {
        CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s1";
        }).runAfterEither(CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s2";
        }), () -> System.out.println("hello world"));
        // 用于测试，查看返回结果
        TimeUnit.SECONDS.sleep(10);
    }

    /**
     * exceptionally事件：当运行时出现了异常，可以通过exceptionally进行补偿
     */
    @Test
    public void exceptionally() {

        String result = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (1 == 1) {
                throw new RuntimeException("测试一下异常情况");
            }
            return "s1";
        }).exceptionally(e -> {
            System.out.println(e.getMessage()); // java.lang.RuntimeException: 测试一下异常情况
            return "hello world";
        }).join();
        System.out.println(result); // 结果为 hello world
    }

    /**
     * thenCompose 事件: 基于上阶段的执行完状态，执行下一阶段
     */
    @Test
    public void thenCompose() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenCompose(s -> CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return s + " world";
        })).join();
        System.out.println(result);
    }

    /**
     * whenComplete事件：当运行完成，完成对结果的记录
     */
    @Test
    public void whenComplete() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (1 == 1) {
                throw new RuntimeException("测试一下异常情况");
            }
            return "s1";
        }).whenComplete((s, t) -> {
            System.out.println(s);
            System.out.println(t.getMessage());
        }).exceptionally(e -> {
            System.out.println(e.getMessage());
            return "hello world";
        }).join();
        System.out.println(result);
    }

    /**
     * handle 事件：当运行完成，对结果进行处理
     * 这里的完成分为两种情况：
     * 1. 正常运行，未出现异常；
     * 2. 出现异常；
     */
    @Test
    public void handle() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 假设出现异常，结果为 hello world，反之结果为 s1
            //if (1 == 1) {
            //    throw new RuntimeException("测试一下异常情况");
            //}
            return "s1";
        }).handle((s, t) -> {
            if (t != null) {
                return "hello world";
            }
            return s;
        }).join();
        System.out.println(result);
    }

}
