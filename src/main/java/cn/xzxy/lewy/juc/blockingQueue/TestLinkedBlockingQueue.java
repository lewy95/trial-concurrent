package cn.xzxy.lewy.juc.blockingQueue;

import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/*
 * LinkedBlockingQueue底层是链表结构，也满足FIFO原则
 * LinkedBlockingQueue是无界的，所以不需要指定队列的容量
 * 由于队列特点就是经常性的增删，所以列表性能更好点
 * LinkedBlockingQueue是经典的队列的实现
 */
public class TestLinkedBlockingQueue {

    /**
     * 创建
     */
    private static LinkedBlockingQueue<Object> createLbq() {
        return new LinkedBlockingQueue<>(10);
    }

    /*
     * remove()当队列为空时，会抛出NoSuchElement异常
     * poll()当队列为空时会抛出null；如果不为空，则按FIFO原则对应的元素
     * poll超时也会阻塞，阻塞放开条件有两个，①队列有数据 ②过了超时时间
     * take()会产生阻塞，阻塞放开条件是队列有数据可消费
     */
    @Test
    public void consumeMethod() throws InterruptedException {
        LinkedBlockingQueue<Object> lbq = createLbq();

        for (int i = 0; i < 10; i++){
            lbq.offer(i);
        }

        //lbq.remove();
        Integer e = (Integer) lbq.poll();
        System.out.println(e);
        //lbq.take();
    }
}
