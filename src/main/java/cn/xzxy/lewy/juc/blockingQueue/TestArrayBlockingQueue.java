package cn.xzxy.lewy.juc.blockingQueue;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * BlockingQueue是一个接口，主要掌握两个实现类ArrayBlockingQueue和LinkedBlockingQueue
 *
 * ArrayBlockingQueue：底层的数据结构是数组，同时具备了队列FIFO的特点
 * 在创建ArrayBlockingQueue时，可以传入一个boolean值，来设置公平策略
 * 如果是true，即让线程排队处理，可以避免某一个线程处于饥饿状态
 */
public class TestArrayBlockingQueue {

    /**
     * 创建
     */
    private static ArrayBlockingQueue<Object> createAbq() {
        return new ArrayBlockingQueue<>(10,true);
    }

//    BlockingQueue

    /**
     * 插入
     * add()在队列满了之后再插入数据，会抛出queue full的异常
     * offer()在队列满了之后，会抛出false，如果未满，则为true
     * offer()也可以设置超时时间，在超时时间之内，会产生阻塞，
     * 阻塞放开条件：一、队列数据被消费  二、过了超时时间，会返回false
     * put()在队列满了之后，会产生阻塞，直到队列未满
     *
     * ArrayBlockingQueue是有界的，如果想实现无界的效果，可以设置容量为Integer.MAX_VALUE
     */
    @Test
    public void testAdd() throws InterruptedException {
        ArrayBlockingQueue<Object> abq = createAbq();
        //boolean add = abq.add(1);
        //System.out.println(add);

        //先队列插满数据
        for (int i = 0; i < 10; i++){
            abq.add(i);
        }
        //add()方法在队列满了以后，再往里填数据会抛异常
        abq.add(11);

        System.out.println("执行完成");
    }

    @Test
    public void testPut() throws InterruptedException {
        ArrayBlockingQueue<Object> abq = createAbq();
        //先队列插满数据
        for (int i = 0; i < 10; i++){
            abq.add(i);
        }
        //put()方法在队列满了之后，会产生阻塞，直到队列未满
        abq.put(11);
        System.out.println("ok");
    }

    @Test
    public void testOffer() throws InterruptedException {
        ArrayBlockingQueue<Object> abq = createAbq();
        for (int i = 0; i < 10; i++){
            abq.offer(i);
        }
        //offer()方法在队列满了以后返回false，未满返回true
        boolean b = abq.offer(11);
        System.out.println(b);// false

        //offer()方法也可以指定超时时间，在超时时间内会产生阻塞
        boolean b2 = abq.offer(11, 10000, TimeUnit.MILLISECONDS);
        System.out.println(b2);// false
    }

    @Test
    public void testPoll() throws InterruptedException {
        ArrayBlockingQueue<Object> abq = createAbq();
        //poll()方法如果队列中没有数据可取，返回null
        System.out.println(abq.poll()); //null
        //poll()也可以指定超时时间，超时时间内阻塞，超时后返回null
        System.out.println(abq.poll(5000,TimeUnit.MILLISECONDS));
        abq.add("1");
        String poll = (String) abq.poll();
        System.out.println(poll); //1
    }

    @Test
    public void testTake() throws InterruptedException {
        ArrayBlockingQueue<Object> abq = createAbq();
        //take()方法如果队列中没有数据可取，会发生阻塞，直到有数据进去为止
        abq.take();
        System.out.println("执行完成");
    }

    @Test
    public void remove() throws InterruptedException {
        ArrayBlockingQueue<Object> abq = createAbq();
        //remove()方法如果队列没有数据可取，会抛出NoSuchElement异常
        abq.remove();
    }

    @Test
    public void testDrainTo() {
        ArrayBlockingQueue<Object> abq = createAbq();
        //drainTo 移除此队列中所有可用的元素，并将它们添加到给定集合中
        for (int i = 0; i < 4; i++){
            abq.offer(i);
        }
        Collection<Object> cs = new ArrayList<>();
        int i = abq.drainTo(cs);
        System.out.println("number: " + i); //4
        //新集合中的
        for (Object li: cs) {
            System.out.println(li); // 0 1 2 3
        }
        //abq中剩余的个数
        System.out.println(abq.remainingCapacity()); // 10，说明已清空

        //也可以指定移除给定数量的可用元素，并将这些元素添加到给定
        for (int j = 10; j < 20; j++){
            abq.offer(j);
        }
        int i2 = abq.drainTo(cs, 6);
        //新集合中的
        for (Object li: cs) {
            System.out.println(li); // 0 1 2 3 10 11 12 13 14 15
        }
        System.out.println(abq.remainingCapacity()); // 6
    }

    @Test
    public void testOthers() {
        ArrayBlockingQueue<Object> abq = createAbq();
        for (int i = 0; i < 5; i++){
            abq.offer(i);
        }
        //查看所有元素
        Iterator<Object> iterator = abq.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next()); // 0 1 2 3 4
        }
        //查看还有多少空间
        System.out.println(abq.remainingCapacity()); //5
        //查看是否存在某个对象
        System.out.println(abq.contains(2)); //true
        System.out.println(abq.contains(6)); //false
    }
}
