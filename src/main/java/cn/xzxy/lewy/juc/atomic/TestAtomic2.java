package cn.xzxy.lewy.juc.atomic;

import org.junit.Test;

import java.util.concurrent.atomic.*;

public class TestAtomic2 {

    //定义原子性整型
    public static AtomicInteger num = new AtomicInteger(0);
    //定义一个原子数组
    public static int[] array = {1, 2, 3, 4, 5};

    @Test
    public void testAtomicInteger() {
        System.out.println(num.get());
        System.out.println(num.addAndGet(2)); //2
        //如果当前num等于2，则返回true并将num置为1
        //如果当前num不等于2，则返回false，不会重置num
        boolean b = num.compareAndSet(2, 5);
        System.out.println(b);
        System.out.println(num); //这里已经将num置为了5
        System.out.println(num.getAndIncrement()); //5
        System.out.println(num.get()); //6
        System.out.println(num.incrementAndGet());//7
        num.lazySet(8);
        System.out.println(num.get());//8
        int gas = num.getAndSet(9);
        System.out.println(gas);//8
        System.out.println(num.get());//9
        num.weakCompareAndSet(9, 10);
        System.out.println(num);
    }

    @Test
    public void testAtomicIntegerArray() {
        AtomicIntegerArray aia = new AtomicIntegerArray(array);
        //AtomicIntegerArray aia2 = new AtomicIntegerArray(5);

        // {1, 2, 3, 4, 5}  索引为1的值 2 加上 8 = 10
        aia.addAndGet(1, 8);
        System.out.println(aia.get(1)); //10

        //[1, 10, 3, 4, 5]
        int gai = aia.getAndIncrement(1);
        System.out.println(gai); // 10
        //[1, 11, 3, 4, 5] 索引为1的值自增了
        System.out.println(aia.toString());
        int iag = aia.incrementAndGet(1);
        System.out.println(iag); //12

        //[1,12,3,4,5]
        boolean b1 = aia.compareAndSet(0, 1, 9);
        System.out.println(b1);//true
        System.out.println(aia.toString()); // [9,12,3,4,5]
        boolean b2 = aia.weakCompareAndSet(2, 5, 0);
        System.out.println(b2);//false
        System.out.println(aia.toString()); // [9,12,3,4,5]

        int dag = aia.decrementAndGet(1);
        System.out.println(dag);
        System.out.println(aia.toString());
    }

    @Test
    public void testAtomicReference() {
        AtomicReference<Player> ar = new AtomicReference<>(new Player("tony",44));
        System.out.println(ar.get().toString());
        ar.set(new Player("lewy", 30));
        System.out.println(ar.get().toString());
        Player p = ar.getAndSet(new Player("muller", 29));
        System.out.println(p.toString());//lewy
        System.out.println(ar.get().toString());//muller
    }

    @Test
    public void testAtomicStampReference() {
        Player p = new Player("muller", 21);
        AtomicStampedReference<Player> player = new AtomicStampedReference<>(p,0);

        System.out.println(player.getStamp());//0
        System.out.println(player.getReference().toString());//muller
        player.set(new Player("lewy",30), 3);
        System.out.println(player.getStamp());//3
        System.out.println(player.getReference().toString());//lewy
    }

    @Test
    public void testAtomicMarkableReference() {
        Player p = new Player("muller", 21);
        AtomicMarkableReference<Player> amr = new AtomicMarkableReference<>(p, false);
    }
}

