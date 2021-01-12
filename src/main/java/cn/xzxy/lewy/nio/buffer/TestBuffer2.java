package cn.xzxy.lewy.nio.buffer;

import java.nio.CharBuffer;

public class TestBuffer2 {

    public static void main(String[] args) {

        CharBuffer charBuffer = CharBuffer.allocate(8);
        System.out.println(charBuffer.capacity());  //8
        System.out.println(charBuffer.limit());     //8
        System.out.println(charBuffer.position());  //0

        charBuffer.put('a');
        charBuffer.put('b');
        charBuffer.put('c');
        System.out.println(charBuffer.position());  //3

        charBuffer.flip();//写模式转到读模式
        System.out.println(charBuffer.limit());     //3
        System.out.println(charBuffer.position());  //0

        System.out.println("取出第一个元素是："+charBuffer.get());  //a
        System.out.println("取完第一个元素之后，position的变化："+charBuffer.position());  //1

        charBuffer.clear();//取完第一个元素之后，执行clear方法，重新为写操作做准备
        System.out.println(charBuffer.position());  //0
        System.out.println(charBuffer.get());    //a 事实证明clear之后，之前的元素还在,并未被清空。当有新的元素进来时才会将其覆盖。
        System.out.println(charBuffer.get(1));   //b
        System.out.println(charBuffer.get(2));   //c
        System.out.println(charBuffer.limit());     //8

        System.out.println("---------------------");
        charBuffer.clear();
        charBuffer.put('d');
        charBuffer.put('e');
        charBuffer.put('f');
        charBuffer.flip();
        System.out.println(charBuffer.position());  //0
        System.out.println(charBuffer.limit());     //3
        System.out.println(charBuffer.get());       //d
        System.out.println(charBuffer.get(1));      //e
        System.out.println(charBuffer.get(2));      //f

    }
}
