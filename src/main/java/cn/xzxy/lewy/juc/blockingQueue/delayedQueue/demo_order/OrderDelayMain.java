package cn.xzxy.lewy.juc.blockingQueue.delayedQueue.demo_order;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

/**
 * 案例：订单超时取消
 * 1. 用DelayQueue存储订单信息（OrderDelay类，实现Delayed接口），每个订单有一个订单ID和超时时间；
 * 2. OrderDelayMain线程对DelayQueue进行监控，从队列中取出超时的订单。
 *
 * 总结：DelayQueue只是java原生的集合，基于JVM，内存有限，不足以支持大型的项目，一旦服务器宕机，消息都会丢失
 *      所以，一般类似订单超时取消这类需要延时队列处理的任务可以借助redis实现
 */
public class OrderDelayMain {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("00000001");
        list.add("00000002");
        list.add("00000003");
        list.add("00000004");
        list.add("00000005");
        DelayQueue<OrderDelay> queue = new DelayQueue<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 5; i++) {
            //设置进延迟队列，设置延时三秒（即三秒后从队列中取出）
            queue.put(new OrderDelay(list.get(i),
                    TimeUnit.NANOSECONDS.convert(3, TimeUnit.SECONDS)));
            try {
                queue.take().print();
                System.out.println("After " + (System.currentTimeMillis() - start) + " MilliSeconds");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}