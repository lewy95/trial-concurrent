package cn.xzxy.lewy.nio.channel;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class TestFileChannel {

    /**
     * 将缓冲区中的数据写入到通道中
     * 注意：会将原来的数据全都覆盖掉
     */
    @Test
    public void testWrite() throws IOException {
        FileOutputStream outputStream = new FileOutputStream(new File("data/testWrite.txt"));
        //FileChannel需要基于某一个流来创建
        //基于输入流创建的只能输入，基于输出流创建的只能输出
        FileChannel fc = outputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.wrap("fyy".getBytes());
        //fc.write(buffer); //从channel起始位置写 结果是 fyy
        fc.write(buffer,3);// 从channel中position为3的地方开始 结果是    fyy
        //输出流不能进行读操作，否则报错: java.nio.channels.NonReadableChannelException
        //fc.read(buffer);
        outputStream.close();
    }

    /**
     * 读取通道中的数据到缓冲区中
     */
    @Test
    public void testRead() throws IOException {
        FileInputStream inputStream = new FileInputStream(new File("data/testWrite.txt"));
        FileChannel fc = inputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(20);
        System.out.println(new String(buffer.array()));
        //定义起始位置，从3开始读8个
        fc.position(3);
        fc.read(buffer);
        System.out.println(new String(buffer.array()));
        //buffer.put((byte) 8);
        //输入流不能进行写操作，否则报错: java.nio.channels.NonWritableChannelException
        //fc.write(buffer);
        inputStream.close();
    }

    @Test
    public void TestRandom() throws IOException {
        /*
         * 通过RandomAccessFile获取的通道，既可以读，也可以写
         * 如果想读和写，在创建此对象时，需要设置第二个参数为“rw”
         */
        RandomAccessFile raf = new RandomAccessFile("data/testWrite.txt", "rw");
        FileChannel fc = raf.getChannel();

        ByteBuffer buffer = ByteBuffer.wrap("shanghai".getBytes());
        //从索引为4的地方开始覆盖，notrailforme => notrshanghai
        //原来是notrailforme有12个，而rw模式既能读又能写shanghai只覆盖了8个字符
        fc.position(4);
        fc.write(buffer);

        fc.read(buffer);
        System.out.println(fc.size());
        //进入读模式
        buffer.flip();
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        while(buffer.hasRemaining()) {
            System.out.print((char)buffer.get()); //shanghai 只有8个字符是因为limit=capacity=8
        }
        buffer.clear();
    }
}
