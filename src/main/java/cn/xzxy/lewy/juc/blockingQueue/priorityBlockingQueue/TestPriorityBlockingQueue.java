package cn.xzxy.lewy.juc.blockingQueue.priorityBlockingQueue;

import org.junit.Test;

import java.util.concurrent.PriorityBlockingQueue;

public class TestPriorityBlockingQueue {

    /**
     * 优先级队列里的元素必须要实现Comparable接口，因为需要对队列中的元素进行排序
     */
    @Test
    public void testPBQ(){
        PriorityBlockingQueue<Student> pbq = new PriorityBlockingQueue<>();
        Student s1 = new Student("lewy",95);
        Student s2 = new Student("anna",98);
        Student s3 = new Student("clala",96);
        pbq.add(s1);
        pbq.add(s2);
        pbq.add(s3);
        for (int i = 0; i < 3; i++) {
            Student s = pbq.poll();
            System.out.println(s);
        }
    }
}
