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

/**
 * 服务端
 */
public class TestSelectorServer {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.socket().bind(new InetSocketAddress(9999));
        //创建一个Selector
        Selector selector = Selector.open();
        //要想用selector提供监听，要先注册，首先为服务器通道设置accept监听
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            int readyNum = selector.select();
            if (readyNum == 0) {
                continue;
            }
            //select()方法会被阻塞，直到有监听的事件被触发
            //拿到所有就绪事件
            Set<SelectionKey> set = selector.selectedKeys();
            //获取事件集合的迭代器
            Iterator<SelectionKey> iter = set.iterator();
            while (iter.hasNext()) {
                SelectionKey sk = iter.next();
                //证明有客户端接入
                if (sk.isAcceptable()) {
                    //注意这个服务通道必须有sk获得
                    ServerSocketChannel ss = (ServerSocketChannel) sk.channel();
                    ss.configureBlocking(false);
                    SocketChannel sc = ss.accept();
                    sc.configureBlocking(false);
                    System.out.println("有客户端连入，负责处理该请求的线程id:" + Thread.currentThread().getId());
                    //OP_READ 0000 0001
                    //OP_WRITE0000 0100
                    //OP_READ|OP_WRITE 0000 0101 相对注册了读"或"写的事件监听
                    sc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                }
                if (sk.isReadable()) {
                    SocketChannel sc = (SocketChannel) sk.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(10);
                    sc.read(buffer);
                    System.out.println("服务器端读到的数据：" + new String(buffer.array()));
                    System.out.println("负责处理读请求的线程id：" + Thread.currentThread().getId());
                    //去掉读监听
                    sc.register(selector, SelectionKey.OP_WRITE);

                }
                if(sk.isWritable()){
                    SocketChannel sc = (SocketChannel)sk.channel();
                    ByteBuffer buffer = ByteBuffer.wrap("收到".getBytes("UTF-8"));
                    sc.write(buffer);
                    System.out.println("服务器端成功写出数据，负责处理写请求的线程id："+Thread.currentThread().getId());
                    //去掉写监听
                    sc.register(selector, SelectionKey.OP_READ);
                }

                //删除该事件，防止一个事件被重复处理
                iter.remove();
            }
        }
    }

}
