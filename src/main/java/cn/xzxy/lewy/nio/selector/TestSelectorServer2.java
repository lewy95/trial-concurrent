package cn.xzxy.lewy.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class TestSelectorServer2 {
    private static final int PORT = 9999;
    private static ByteBuffer buffer = ByteBuffer.allocate(1024);

    public static void main(String[] args) {
        try {
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.bind(new InetSocketAddress(PORT));
            ssc.configureBlocking(false);
            //1.register()
            Selector selector = Selector.open();
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("REGISTER CHANNEL , CHANNEL NUMBER IS:" + selector.keys().size());

            while (true) {
                //2.select() 拿到所有就绪事件
                //select()方法会被阻塞，直到有监听的事件被触发
                int n = selector.select();
                if (n == 0) {
                    continue;
                }
                //3.轮询SelectionKey
                Set<SelectionKey> set = selector.selectedKeys();
                //获取事件集合的迭代器
                Iterator<SelectionKey> iterator = set.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    //如果满足Acceptable条件，则必定是一个ServerSocketChannel
                    if (key.isAcceptable()) {
                        ServerSocketChannel sscTemp = (ServerSocketChannel) key.channel();
                        //得到一个连接好的SocketChannel，并把它注册到Selector上，兴趣操作为READ
                        SocketChannel socketChannel = sscTemp.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                        System.out.println("REGISTER CHANNEL , CHANNEL NUMBER IS:" + selector.keys().size());
                    }
                    //如果满足Readable条件，则必定是一个SocketChannel
                    if (key.isReadable()) {
                        //读取通道中的数据
                        SocketChannel channel = (SocketChannel) key.channel();
                        readFromChannel(channel);
                    }
                    //4.remove SelectionKey
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readFromChannel(SocketChannel channel) {
        buffer.clear();
        try {
            while (channel.read(buffer) > 0) {
                buffer.flip();
                byte[] bytes = new byte[buffer.remaining()];
                buffer.get(bytes);
                System.out.println("READ FROM CLIENT:" + new String(bytes));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}