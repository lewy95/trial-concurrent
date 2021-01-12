package cn.xzxy.lewy.nio.selector;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 客户端
 *
 * 结果：
 * 服务器端启动一次，客户端启动三次，服务器端的控制台输出：
 * 服务器端启动
 * 有客户端连入，负责处理该请求的线程id:1
 * 有客户端连入，负责处理该请求的线程id:1
 * 有客户端连入，负责处理该请求的线程id:1
 *
 * 即处理多个请求使用同一个线程。
 */
public class TestSelectorClient {

    @Test
    public void testConnect() throws Exception {
        SocketChannel sc = SocketChannel.open();
        sc.configureBlocking(false);
        sc.connect(new InetSocketAddress("127.0.0.1", 9999));
        System.out.println("客户端连入");
        //while (true) ;
    }

    @Test
    public void testRead() throws Exception{
        SocketChannel sc = SocketChannel.open();
        sc.configureBlocking(false);
        sc.connect(new InetSocketAddress("127.0.0.1", 9999));
        ByteBuffer buffer = ByteBuffer.wrap(
                "helloworld".getBytes());
        sc.write(buffer);
        while(true);
    }

    @Test
    public void testWrite() throws Exception{
        SocketChannel sc = SocketChannel.open();
        sc.configureBlocking(false);
        sc.connect(new InetSocketAddress("127.0.0.1", 9999));
        ByteBuffer reBuf = ByteBuffer.allocate(6);
        sc.read(reBuf);
        System.out.println("客户端成功读到数据:"+new String(reBuf.array(),"UTF-8"));
        while(true);
    }

}
