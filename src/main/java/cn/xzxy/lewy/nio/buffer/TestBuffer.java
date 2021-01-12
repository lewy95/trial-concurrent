package cn.xzxy.lewy.nio.buffer;

import org.junit.Test;

import java.nio.ByteBuffer;

public class TestBuffer {

    //Buffer的分配
    private ByteBuffer dispenseBuffer() {
        //ByteBuffer是个抽象类，我们得到的是他的实现子类对象，HeapByteBuffer
        return ByteBuffer.allocate(512);
    }

    /**
     * 创建缓存区
     */
    @Test
    public void createBuffer() {
        // 法一: 直接创建
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        //CharBuffer charBuffer = CharBuffer.allocate(1024);
        // 法二: 从现有数组中创建
        byte[] bytes = new byte[1024];
        ByteBuffer byteBuffer2 = ByteBuffer.wrap(bytes);
    }

    /**
     * 向Buffer中写数据
     */
    @Test
    public void testPut() {
        ByteBuffer buffer = dispenseBuffer();
        // put()方法 用于写入数据
        buffer.put((byte) 1); //存入的是字节数据，占一个字节
        System.out.println(buffer.position()); // 1，表示buffer的从position从0开始，put一个则加一
        buffer.put("123".getBytes());  //也可以传入字节数组
        System.out.println(buffer.position()); // 4
        buffer.putInt(1);   //putInf，存入的是整数数据，占四个字节
        System.out.println(buffer.position()); // 8
        buffer.putDouble(1.2); //putDouble，存入的是Double型数据，占八个字节
        System.out.println(buffer.position());  // 16
        // and so on，但在开发里，我们操作都是字节数据，所以掌握put方法即可
    }

    @Test
    public void testGet() {
        ByteBuffer buffer = dispenseBuffer();
        //注意：当向缓冲区里写入字节时，比如1，当调用get()方法时，得到的却是0，这是什么原因呢？
        buffer.put((byte) 1);
        buffer.put((byte) 2);
        buffer.put((byte) 3);
        System.out.println(buffer.get()); // 0
        //原因是在buffer缓冲区里，position指针每次put后，position位置才会加1
        //所以当put完之后，直接调用get()方法，get()是根据最新指针位置来取值的，最新位置肯定是没有数据的，所以是0
        //解决方法：可以通过get(position)来读取指定位置的数据
        System.out.println(buffer.get(2));  // 3
    }

    @Test
    public void testPositionAndLimit() {
        ByteBuffer buffer = dispenseBuffer();
        buffer.put((byte) 1);
        buffer.put((byte) 2);
        buffer.put((byte) 3);
        System.out.println(buffer.get(2)); //不会 +1
        System.out.println(buffer.get()); //会 +1
        //position的初始位置是0，每put()/get()一次，都会加一，
        System.out.println("当前的position位置：" + buffer.position());
        //在写完数据后，将limit设置为当前position位置，然后将position位置重置为0
        //这样做的目的是为了从头开始读数据，并且，用limit限制了读取下标，不会造成读出空数据的情况
        buffer.limit(buffer.position());
        //position位置重置为0
        buffer.position(0);
        //当前的limit值
        System.out.println(buffer.limit());
        //当前的position值
        System.out.println(buffer.position());
    }

    @Test
    public void testFlip() {
        ByteBuffer buffer = dispenseBuffer();
        buffer.put((byte) 1);
        buffer.put((byte) 2);
        buffer.put((byte) 3);
        System.out.println("当前的position位置：" + buffer.position()); // 3
        buffer.flip();
        //当前的limit值
        System.out.println(buffer.limit()); // 3
        //当前的position值
        System.out.println(buffer.position()); // 0
    }

    @Test
    public void testHasRemaining() {
        ByteBuffer buffer = dispenseBuffer();
        buffer.put((byte) 1);
        buffer.put((byte) 2);
        buffer.put((byte) 3);
        buffer.flip();
        //遍历position位和limit位之间的元素
        while (buffer.hasRemaining()) {
            System.out.println(buffer.get()); // 1 2 3
        }
    }

    @Test
    public void testRewind() {
        ByteBuffer buffer = dispenseBuffer();
        buffer.put((byte) 1);
        buffer.put((byte) 2);
        buffer.put((byte) 3);
        buffer.rewind(); // 将position重置为0
        System.out.println(buffer.position()); // 0
    }

    @Test
    public void testMarkAndReset() {
        ByteBuffer buffer = dispenseBuffer();
        buffer.put((byte) 1);
        buffer.put((byte) 2);
        buffer.mark(); //position = 2
        buffer.put((byte) 3);
        buffer.mark(); //position = 3 覆盖掉了前面一个
        buffer.reset();
        System.out.println(buffer.reset()); //position = 3
    }

    @Test
    public void testClear() {
        ByteBuffer buffer = dispenseBuffer();
        buffer.put((byte) 1);
        buffer.put((byte) 2);
        buffer.put((byte) 3);
        //clear的作用是清空缓冲区，但并不真正的清除数据，而是把position置为0，limit置为容量上限
        //换句话说，Buffer 被清空了，Buffer中的数据并未清除，所以，当get(0)时，能得到原缓冲区数据的，
        // 直到有新的数据进来后才会被覆盖
        //只是这些标记告诉我们可以从哪里开始往Buffer里写数据。
        buffer.clear();
        System.out.println(buffer.position()); // 0
        System.out.println(buffer.get(0)); // 1
        buffer.put((byte) 6);
        System.out.println(buffer.get(0)); // 6
        System.out.println(buffer.position()); // 1
    }


    /**
     * 如果Buffer中有一些未读的数据，调用clear()方法，数据将“被遗忘”，意味着不再有任何标记会告诉你哪些数据被读过，哪些还没有。
     * 如果Buffer中仍有未读的数据，且后续还需要这些数据，但是此时想要先先写些数据，那么使用compact()方法。
     * compact()方法将所有未读的数据拷贝到Buffer起始处。
     * 然后将position设到最后一个未读元素正后面，limit属性依然像clear()方法一样，设置成capacity。
     * 现在Buffer准备好写数据了，但是不会覆盖未读的数据。
     */
    @Test
    public void testCompact() {
        ByteBuffer buffer = dispenseBuffer();
        buffer.put((byte) 1);
        buffer.put((byte) 2);
        buffer.put((byte) 3);
        buffer.flip();
        System.out.println(buffer.get()); // 1
        buffer.compact();
        System.out.println(buffer.position()); // 2
        buffer.put((byte) 8);
        System.out.println(buffer.position()); // 3
    }

    @Test
    public void testWrap() {
        //wrap方法也可以创建一个Buffer，接收的是一个字节数组
        //并且利用wrap方法创建完buffer后，buffer里就有了字节数组里的数据
        byte[] b = {1, 2, 3, 4};
        ByteBuffer buffer = ByteBuffer.wrap(b);
        System.out.println(buffer.capacity()); //4
        System.out.println(buffer.limit());  //4
        ByteBuffer buffer2 = ByteBuffer.wrap("helloworld".getBytes());
        System.out.println(buffer2.capacity()); //10
        System.out.println(buffer2.limit()); //10
        //也可以通过指定数组下标的界限来创建Buffer并填充数据，注意：
        //利用此方法创建buffer，buffer的容量和数组的容量一致，buffer里也包含了数组的全部数据，不同的是limit的位置变化了
        ByteBuffer buffer3 = ByteBuffer.wrap(b,2,2);
        System.out.println(buffer3.capacity()); //4
        System.out.println(buffer3.limit());  //2
        System.out.println(buffer3.get(1));
    }
}
