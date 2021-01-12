package cn.xzxy.lewy.juc.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestReentrantLock2 {

    private Lock lock = new ReentrantLock();
    //与wait和notify方法相同
    private Condition condition = lock.newCondition();
    private void method1(){
        try {
            lock.lock();
            System.out.println("当前线程：" + Thread.currentThread().getName() + "进入等待状态..");
            Thread.sleep(3000);
            System.out.println("当前线程：" + Thread.currentThread().getName() + "释放锁..");
            condition.await();	// Object wait
            //由于是重入锁，尽管t1被等待了，但是不妨碍t2拿到锁
            System.out.println("当前线程：" + Thread.currentThread().getName() +"继续执行...");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("释放锁method1");
            lock.unlock();
        }
    }

    private void method2(){
        try {
            lock.lock();
            System.out.println("当前线程：" + Thread.currentThread().getName() + "进入..");
            Thread.sleep(3000);
            System.out.println("当前线程：" + Thread.currentThread().getName() + "发出唤醒..");
            condition.signal(); //Object notify ，唤醒了前面被condition await掉的t1
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("释放锁method2");
            lock.unlock();
        }
    }

    public static void main(String[] args) {

        final TestReentrantLock2 uc = new TestReentrantLock2();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                uc.method1();
            }
        }, "t1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                uc.method2();
            }
        }, "t2");

        t1.start();
        t2.start();
    }

}
