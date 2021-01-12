package cn.xzxy.lewy.juc.blockingQueue.priorityBlockingQueue;

/**
 * 学生类，实现了Comparable接口
 */
public class Student implements Comparable<Student> {
    private String name;
    private int score;

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Student [name=" + name + ", score=" + score + "]";
    }

    /**
     * 按照学生的得分排序
     */
    @Override
    public int compareTo(Student o) {
        return o.score - this.score;
    }
}
