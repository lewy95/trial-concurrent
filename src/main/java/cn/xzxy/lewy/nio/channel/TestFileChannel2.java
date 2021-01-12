package cn.xzxy.lewy.nio.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class TestFileChannel2 {

    /**
     * 以下是一个NIO的例子，这里比作是一个送货的比喻，一下文字简介：
     * 1.送什么货物：FileInputStream fis = new FileInputStream("C:\\reset.css");
     * 2.货物送到哪里：FileOutputStream fos = new FileOutputStream("D:\\reset.css");
     * 3.需要接收货物的快递员：FileChannel fisChannel = fis.getChannel();
     * 4.到达目的城市需要快递员送到手里：FileChannel fosChannel = fos.getChannel();
     * 5.需要车辆来承载这些货物，定义这个车一下能装多少货物：ByteBuffer buffer = ByteBuffer.allocate(1024);
     * 6.执行需要的元素，每次送完一车那么把这车标为空车：buffer.flip();
     * 7.当装不满一车代表不用再接收和送了：if(num == -1)
     * 8.通知快递员下班或者干其他的：fisChannel.close();fosChannel.close();
     */
    public static void main(String[] args) throws IOException {
        //创建输入，这是要传递问文件
        FileInputStream fis = new FileInputStream("source.txt");
        //创建输出， 这是要到谁的手裡
        FileOutputStream fos = new FileOutputStream("destination.txt");
        //创建通道 也就是选择什麽人，先送到邮局，一个接受邮件的到邮局的快递员
        FileChannel fisChannel = fis.getChannel();
        //创建通道 也就是选择什麽人 ，邮局送到个人的手裡，一个送货的快递员
        FileChannel fosChannel = fos.getChannel();
        //东西很多，要打包一车车送，这裡有1024容量的车厢
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //判断是不是装够了，要没装满的就是返回的是-1
        int num = 0;
        //开始送了，快递员接收了，快递员立马送
        while (true) {
            buffer.clear();
            num = fisChannel.read(buffer);//读数据（将通道中数据写入到缓冲区）
            //进入缓冲区的读模式，避免出错
            //将缓存的position归零，就好比车子把东西送到了中心，贴上个标签说这个车是空的了，三个属性limit capacity position
            buffer.flip();
            fosChannel.write(buffer);//这个快递员就把东西送到目的地，写数据（将缓冲区中数据写入到通道中）
            if (num == -1) {//装不满一车就代表送完了
                break;
            }
        }
        System.out.println("end");
        fisChannel.close();//告诉快递员下班了或者做其他的不要再送这批货了，这批货物送完了
        fosChannel.close();
    }

}