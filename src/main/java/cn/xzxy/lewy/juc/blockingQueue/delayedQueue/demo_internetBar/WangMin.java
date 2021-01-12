package cn.xzxy.lewy.juc.blockingQueue.delayedQueue.demo_internetBar;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 案例：网吧上网
 * 模拟在网咖上网时会用到一个网吧综合系统，其中有一个主要功能就是给每一位网民计时，
 * 用户充值一定金额会有相应的上网时常，这里我们用DelayQueue模拟实现一下：
 * 1. 用DelayQueue存储网民（WangMin类，实现Delayed接口），每一个人都有姓名和身份证；
 * 2. WangBa线程对DelayQueue进行监控，从队列中取出到时间的网民执行下机操作。
 */
public class WangMin implements Delayed {

    private String name;
    private String id; // idCard
    private long endTime; //截止时间
    //定义时间工具类
    private TimeUnit timeUnit = TimeUnit.SECONDS;

    public WangMin(String name, String id, long endTime){
        this.name=name;
        this.id=id;
        this.endTime = endTime;
    }

    public String getName(){
        return this.name;
    }

    public String getId(){
        return this.id;
    }

    /**
     * 用来判断是否到了截止时间
     */
    @Override
    public long getDelay(TimeUnit unit) {
        //return unit.convert(endTime, TimeUnit.MILLISECONDS) - unit.convert(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        return endTime - System.currentTimeMillis();
    }

    /**
     * 相互批较排序用
     */
    @Override
    public int compareTo(Delayed delayed) {
        WangMin w = (WangMin)delayed;
        return this.getDelay(this.timeUnit) - w.getDelay(this.timeUnit) > 0 ? 1:0;
    }
}
