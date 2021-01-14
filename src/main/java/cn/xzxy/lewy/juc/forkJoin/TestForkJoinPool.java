package cn.xzxy.lewy.juc.forkJoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Fork/Join 案例：计算数值
 */
public class TestForkJoinPool {
    public static void main(String[] args) {
        // 创建ForkJoinPool，指定并发线程数
        ForkJoinPool forkJoinPool = new ForkJoinPool(2);
        // 创建目标任务
        CalcAddTask myAddTask = new CalcAddTask(1, 10000, 1000);
        // 将目标任务提交到ForkJoinPool执行
        ForkJoinTask<Integer> forkJoinTask = forkJoinPool.submit(myAddTask);
        // 获取任务执行的结果
        Integer result = null;
        try {
            result = forkJoinTask.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("计算结果：" + result);
    }

}

class CalcAddTask extends RecursiveTask<Integer> {

    // 阈值
    private int threshold;
    private int start;
    private int end;

    public CalcAddTask(int start, int end, int threshold) {
        this.start = start;
        this.end = end;
        this.threshold = threshold;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        // 如果粒度足够小，则直接计算
        if (end - start <= threshold) {
            for (int i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            // 否则继续分割任务
            int middle = (start + end) / 2;
            CalcAddTask left = new CalcAddTask(start, middle, threshold);
            CalcAddTask right = new CalcAddTask(middle + 1, end, threshold);
            // 分割子任务
            ForkJoinTask<Integer> leftTask = left.fork();
            ForkJoinTask<Integer> rightTask = right.fork();
            // 执行子任务计算结果
            Integer leftResult = leftTask.join();
            Integer rightResult = rightTask.join();
            sum = leftResult + rightResult;
            System.out.println("子任务合并结果：sum=" + sum + " start=" + start + " end=" + end);
        }
        return sum;
    }
}
