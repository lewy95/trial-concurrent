package cn.xzxy.lewy.juc.blockingQueue;

import java.util.concurrent.*;

public class TestBqBasic {

    public static void main(String[] args) throws Exception {
        // 允许保存5个数据队列
        BlockingQueue<String> queue = new ArrayBlockingQueue<String>(5);
        for (int x = 0; x < 3; x++) {
            new Thread(() -> {
                for (int y = 0; y < 5; y++) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        String str = "【生产数据｛"
                                + Thread.currentThread().getName()
                                + "｝】y = " + y ;
                        queue.put(str);    // 会进入到生产的阻塞状态
                        System.out.println(str);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "生产者-" + x).start();
        }
        for (int x = 0; x < 5; x++) {
            new Thread(() -> {
                while (true) {
                    try {
                        TimeUnit.SECONDS.sleep(2);
                        // 队列内容为空了
                        if (queue.isEmpty()) {
                            break; // 结束循环
                        }
                        System.out.println("【消费数据｛"
                                + Thread.currentThread().getName()
                                + "｝】" + queue.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "消费者-" + x).start();
        }
    }
}
