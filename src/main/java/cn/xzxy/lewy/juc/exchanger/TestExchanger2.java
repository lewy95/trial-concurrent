package cn.xzxy.lewy.juc.exchanger;

import java.util.concurrent.Exchanger;

/**
 * 交换机：用于两个线程间的数据交换
 * 实例：
 * 两个间谍相互交换暗号
 * 线程1:间谍1,暗号:回眸一笑
 * 线程2:间谍2,暗号:寸草不生
 */
public class TestExchanger2 {
    public static void main(String[] args) {
        Exchanger<String> ex = new Exchanger<>();
        new Thread(new Spy1(ex)).start();
        new Thread(new Spy2(ex)).start();
    }
}

class Spy1 implements Runnable {
    private Exchanger<String> ex;

    public Spy1(Exchanger<String> ex) {
        this.ex = ex;
    }

    @Override
    public void run() {
        String info = "回眸一笑";
        try {
            //exchange()用于向对方线程传递数据
            //exchange()的返回值是对方线程传来的数据
            String result = ex.exchange(info);
            System.out.println("1收到2发来的暗号:" + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Spy2 implements Runnable {
    private Exchanger<String> ex;

    public Spy2(Exchanger<String> ex) {
        this.ex = ex;
    }

    @Override
    public void run() {
        String info = "寸草不生";
        try {
            String result = ex.exchange(info);
            System.out.println("2收到1发来的暗号:" + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

