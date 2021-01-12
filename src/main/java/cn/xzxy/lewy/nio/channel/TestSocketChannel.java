package cn.xzxy.lewy.nio.channel;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class TestSocketChannel {

    @Test
    public void testSocketChannel() throws IOException {
        SocketChannel sc = SocketChannel.open();
        //SocketChannel默认也是阻塞的
        //要设置非阻塞，设置configureBlocking(false)
        sc.configureBlocking(false);
        sc.connect(new InetSocketAddress("127.0.0.1", 9996));
        System.out.println("NIO客户端连接");
    }

    /**
     * 对应TestServerSocketChannel中的testRead方法
     */
    @Test
    public void testWrite() throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.configureBlocking(false);//设置为非阻塞
        sc.connect(new InetSocketAddress("127.0.0.1", 9996));
        //判断是否连接
        while (!sc.isConnected()) {
            //判断正在进行套接字连接的sc是否已经完成连接
            boolean flag = sc.finishConnect();
            System.out.println(flag);
        }
        ByteBuffer buffer =
                ByteBuffer.wrap("hellonanjing".getBytes());
        while (buffer.hasRemaining()) {
            sc.write(buffer);
        }
        sc.close();
    }
}
