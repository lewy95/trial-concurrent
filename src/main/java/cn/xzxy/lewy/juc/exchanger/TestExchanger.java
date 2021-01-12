package cn.xzxy.lewy.juc.exchanger;

import java.util.concurrent.Exchanger;

/**
 * 绑架案例：
 * 两拨绑匪交换人质对象
 * 每个绑匪是一个线程，此线程中有自己的人质对象
 */
public class TestExchanger {

    public static void main(String[] args) {

        Exchanger<Person> exchanger = new Exchanger<>();
        new Thread(new Kidnapper1(exchanger)).start();
        new Thread(new Kidnapper2(exchanger)).start();
    }
}

class Kidnapper1 implements Runnable {

    private Exchanger exchanger;

    Kidnapper1(Exchanger<Person> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        Person person = new Person();
        person.setName("oo");
        person.setAge(21);

        try {
            Person p = (Person) exchanger.exchange(person);
            System.out.println("绑匪一拿到了对方的人质：" + p);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


class Kidnapper2 implements Runnable {

    private Exchanger exchanger;

    Kidnapper2(Exchanger<Person> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        Person person = new Person();
        person.setName("pp");
        person.setAge(26);

        try {
            Person p = (Person) exchanger.exchange(person);
            System.out.println("绑匪二拿到了对方的人质：" + p);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


