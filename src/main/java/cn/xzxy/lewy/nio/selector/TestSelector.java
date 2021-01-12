package cn.xzxy.lewy.nio.selector;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class TestSelector {

    @Test
    public void testSelectionKey() throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(9998));
        ssc.configureBlocking(false);

        Selector selector = Selector.open();
        //一个SelectionKey键表示了一个特定的通道对象和一个特定的选择器对象之间的注册关系
        SelectionKey key = ssc.register(selector, SelectionKey.OP_ACCEPT);
        //返回该SelectionKey对应的channel
        //sun.nio.ch.ServerSocketChannelImpl[/0:0:0:0:0:0:0:0:9998]
        System.out.println(key.channel());
        //返回该SelectionKey对应的Selector
        //sun.nio.ch.KQueueSelectorImpl@3581c5f3
        System.out.println(key.selector());
        //返回SelectionKey的attachment，attachment可以在注册channel的时候指定，不指定为null
        //attachment的作用：
        System.out.println(key.attachment());
        //可通过interestOps()获取感兴趣事件中操作，并可以通过以下方法来判断Selector是否对Channel的某种事件感兴趣：
        int interestSet = key.interestOps();
        boolean isInterest = (interestSet & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT;
        boolean isInterest2 = (interestSet & SelectionKey.OP_READ) == SelectionKey.OP_READ;
        System.out.println(isInterest);
        System.out.println(isInterest2);
        //返回一个bit mask，代表在相应channel上可以进行的IO操作
        //System.out.println(key.readyOps());

        System.out.println(key.isReadable());
        System.out.println(key.isWritable());
        System.out.println(key.isConnectable());
        System.out.println(key.isAcceptable());
        System.out.println(key.isValid());
    }

    @Test
    public void testSelect() {

    }

}
