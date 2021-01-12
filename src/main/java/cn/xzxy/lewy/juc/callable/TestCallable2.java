package cn.xzxy.lewy.juc.callable;

import java.util.concurrent.*;

public class TestCallable2 {

    public static void main(String[] args) throws InterruptedException, ExecutionException{

        ExecutorService executorService = Executors.newFixedThreadPool(1);

        /*
        Future future = executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("async task");
            }
        });
        */
        
        Future future = executorService.submit(new Callable<Object>() {
            public Object call() throws Exception {
                System.out.println("Asynchronous Callable");
                return "Callable Result";
            }
        });
        
        //若成功执行返回null
        System.out.println("future: " + future.get());

        executorService.shutdown();
    }
}
