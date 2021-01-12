package cn.xzxy.lewy.juc.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 测试 AtomicStampReference 解决aba问题
 */
public class TestAtomicReference {

    static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
    // atomicStampedReference(初始值为1，版本号为1)
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100, 1);

    public static void main(String[] args) {
        System.out.println("============= 测试aba问题的产生 ============= ");
        new Thread(() -> {
            atomicReference.compareAndSet(100, 101); // A->B
            atomicReference.compareAndSet(101, 100); // B->A
        },"t1").start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                atomicReference.compareAndSet(100, 102);
                System.out.println(atomicReference.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t2").start();

        // 暂停一下线程，便于观察结果
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("============= 测试aba问题的解决 ============= ");
        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "第一次版本号：" + stamp);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 模拟一个ABA操作，看看能否成功
            atomicStampedReference.compareAndSet(100, 103, stamp, stamp +1);
            System.out.println(Thread.currentThread().getName() + "第二次版本号：" + stamp);
            atomicStampedReference.compareAndSet(103, 100, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() +1);
            System.out.println(Thread.currentThread().getName() + "第三次版本号：" + stamp);
        },"t3").start();

        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "第一次版本号：" + stamp);
            try {
                // 等待3s，保证t3能够执行一次模拟的ABA操作
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean result = atomicStampedReference.compareAndSet(100, 104, stamp, stamp + 1);
            System.out.println(Thread.currentThread().getName() + "修改结果：" + result
                    + "，当前最新版本号：" + atomicStampedReference.getStamp()
                    + "，当前最新结果：" + atomicStampedReference.getReference());
        },"t4").start();
    }
}
