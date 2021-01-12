package cn.xzxy.lewy.juc.cow;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/*
 * 测试 CopyOnWriteArrayList
 *
 * 由于ArrayList是非线程安全的，程序可能会产生ConcurrentModificationException
 * 推荐使用 CopyOnWriteArrayList
 *
 */
public class TestCopyOnWriteArrayList {

    // list是ArrayList对象时，产生ConcurrentModificationException异常
    //private static List<String> list = new ArrayList<String>();
    private static List<String> list = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {

        // 同时启动两个线程对list进行操作
        new MyThread("ta").start();
        new MyThread("tb").start();
    }

    private static void printAll() {
        String value = null;
        Iterator iter = list.iterator();
        while (iter.hasNext()) {
            value = (String) iter.next();
            System.out.print(value + ", ");
        }
        System.out.println();
    }

    private static class MyThread extends Thread {
        MyThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            int i = 0;
            while (i++ < 4) {
                String val = Thread.currentThread().getName() + "-" + i;
                list.add(val);
                // 遍历List
                printAll();
            }
        }
    }
}