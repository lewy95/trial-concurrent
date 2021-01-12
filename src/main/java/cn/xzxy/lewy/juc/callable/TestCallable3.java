package cn.xzxy.lewy.juc.callable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class TestCallable3 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        Set<Callable<String>> callableSet = new HashSet<>();

        callableSet.add(new Callable<String>() {
            public String call() throws Exception {
                return "Task 1";
            }
        });
        callableSet.add(new Callable<String>() {
            public String call() throws Exception {
                return "Task 2";
            }
        });
        callableSet.add(new Callable<String>() {
            public String call() throws Exception {
                return "Task 3";
            }
        });

        List<Future<String>> futures = executorService.invokeAll(callableSet);

        for (Future<String> f : futures) {
            System.out.println("fs: " + f.get());
        }

        executorService.shutdown();
    }
}
