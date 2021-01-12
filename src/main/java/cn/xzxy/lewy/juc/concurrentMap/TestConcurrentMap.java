package cn.xzxy.lewy.juc.concurrentMap;

import org.junit.Test;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class TestConcurrentMap {

    /**
     * HashMap是非线程安全的
     * Hashtable是线程安全的，当一个线程操作Hashtable时，会锁住整表，并且查询时也锁整表。
     * 引入思想：分段锁（分段桶）的概念
     * ConcurrentMap是利用这个思想来做的，分了16个桶（segment）
     * 性能是Hashtable的16倍。
     */

    @Test
    public void testCreate() {
        ConcurrentMap<String, String> map =
                new ConcurrentHashMap<>();
        map.put("one", "lewy");
        map.put("two", "anna");
        map.put("three", "clala");
        System.out.println(map.get("one"));
    }

    /**
     * 根据指定的key来获取符合条件的子map
     * 方法：
     * headMap(key)：获取的范围  key)
     * subMap(fromKey,toKey)获取的范围[fromKey,toKey)
     * tailMap(key)：获取的范围  [key
     */
    @Test
    public void testNavigable(){
        ConcurrentNavigableMap<Integer,String> map = new ConcurrentSkipListMap<>();
        map.put(1, "一");
        map.put(2, "二");
        map.put(3, "三");
        map.put(4, "四");
        map.put(5, "五");
        map.put(6, "六");
        map.put(7, "七");
        map.put(8, "八");
        ConcurrentNavigableMap<Integer,String> headMap = map.headMap(2);
        System.out.println(headMap);//得1
        ConcurrentNavigableMap<Integer,String> subMap= map.subMap(3, 6);
        System.out.println(subMap);//得 3 4 5
        ConcurrentNavigableMap<Integer,String> tailMap = map.tailMap(7);
        System.out.println(tailMap);//得 7 8
    }
}

