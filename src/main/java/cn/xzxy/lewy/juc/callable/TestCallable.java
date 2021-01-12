package cn.xzxy.lewy.juc.callable;

import java.util.concurrent.*;

public class TestCallable {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        //ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<Student> future = executorService.submit(new CallRunner());
        // future.get是阻塞的，如果没有返回结果，会无限期阻塞下去
        // Student result = future.get();
        // 推荐使用future.get(long timeout, TimeUnit unit)，表示超过时间不会继续等待
        // 但会抛出 TimeoutException 异常
        Student result2 = future.get(3, TimeUnit.SECONDS);
        System.out.println("返回值为" + result2);
        executorService.shutdown();
    }
}

/**
 * 创建线程类，可以继承Thread或者Runnable
 * jdk1.5以后，引入新的线程接口callable
 * callable可以抛异常
 * call方法可以有返回值，返回值的类型就是泛型
 * callable只能由线程池启动，也可以接受其他返回值
 */
class CallRunner implements Callable<Student> {

    @Override
    public Student call() throws Exception {
        TimeUnit.SECONDS.sleep(5); // 超出future等待时间

        Student student = new Student();
        student.setName("lewy");
        student.setAge(20);
        return student;
    }
}
