package cn.xzxy.lewy.nio.channel;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class TestServerSocketChannel {

    @Test
    public void testServerSocketChannel() throws IOException {
        //ServerSocketChannel是一个抽象类，不能直接new，
        //需要调用其方法：open(),创建的为实现类ServletSocketChannelImpl
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(9996));
        //ServletSocketChannel默认是阻塞的
        //想要设置为非阻塞,需要设置：configureBlocking(false)，参数为false，表示非阻塞
        ssc.configureBlocking(false);
        ssc.accept();
        System.out.println("NIO服务端收到客户请求");
    }

    /**
     * 对应TestSocketChannel中的testWrite方法
     */
    @Test
    public void testRead() throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(9996));
        //ServletSocketChannel默认是阻塞的
        //想要设置为非阻塞,需要设置：configureBlocking(false)，参数为false，表示非阻塞
        ssc.configureBlocking(false);
        //时刻让sc进行监听，有客户端请求，则直接连接
        SocketChannel sc = null;
        while (sc == null) {
            sc = ssc.accept();
        }
        ByteBuffer buffer = ByteBuffer.allocate(12);
        while (buffer.hasRemaining()) {
            //read方法也是非阻塞的，需要用while(buffer.hasRemaining())来确保数据读取完整
            sc.read(buffer);
        }
        System.out.println("服务端接收到数据：" + new String(buffer.array()));
    }
}
