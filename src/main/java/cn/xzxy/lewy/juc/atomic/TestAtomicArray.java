package cn.xzxy.lewy.juc.atomic;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class TestAtomicArray {

    private static final ConcurrentHashMap<String, AtomicIntegerArray> statisticalMap =
            new ConcurrentHashMap<>();

    // index 不能大于 length
    public static int getAtomicInteger(String key, int index) {
        if (statisticalMap.get(key) == null) {
            statisticalMap.putIfAbsent(key, new AtomicIntegerArray(2));
        }
        int count = statisticalMap.get(key).incrementAndGet(index); // 根据数组索引位置进行自增
        return statisticalMap.get(key).get(index);
    }

    public static void main(String[] args) {
//        int number = TestAtomicArray.getAtomicInteger("yy", 0);
//        int number2 = TestAtomicArray.getAtomicInteger("yy",0);
//        int number3 = TestAtomicArray.getAtomicInteger("yy",1);
//        int number4 = TestAtomicArray.getAtomicInteger("oo",0);
//        int number5 = TestAtomicArray.getAtomicInteger("oo",1);
//
//        System.out.println(number + " " + number2 + " " + number3 + " " + number4 + " " + number5);
//        System.out.println(statisticalMap.get("yy"));
//        System.out.println(statisticalMap.get("oo"));

        long l1 = 1;
        long l2 = 2;
        Map<String, Object> m = new HashMap<String, Object>();
        Map m2 = new HashMap<String, Object>();
        m.put("yy", "ok");
        Object[] objects = {l1, l2, m};
        System.out.println(objects[0]);
        m2 = (Map) objects[2];
        System.out.println(m2.get("yy"));
    }
}
