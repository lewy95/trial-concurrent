package cn.xzxy.lewy.juc.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * 赛跑案例：
 * begin：定义一条起跑线，所有选手在枪声未响之前都无法起跑
 * end：定义一条终点线，所有选手到达后，都会countdown一次，直到n=0时，表明比赛结束
 *      end的await用于判断选手是否达到终点线
 */
public class TestCountDownLatch5 {

    public static void main(String[] args) {

        CountDownLatch begin = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(2);

        for(int i=0; i<2; i++){
            Thread thread = new Thread(new Player(begin,end));
            thread.start();
        }

        try{
            System.out.println("the race begin");
            // countdown后，n为0，所有选手的await都会被放开，即起跑
            begin.countDown();
            // 判断是否所有选手都已到达终点
            end.await();
            System.out.println("the race end");
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}


/**
 * 选手
 */
class Player implements Runnable{

    private CountDownLatch begin;

    private CountDownLatch end;

    Player(CountDownLatch begin,CountDownLatch end){
        this.begin = begin;
        this.end = end;
    }

    public void run() {

        try {
            begin.await();
            System.out.println(Thread.currentThread().getName() + " arrived !");;
            // 每名到达后，countdown一次
            end.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
