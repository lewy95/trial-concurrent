package cn.xzxy.lewy.nio.reactor.single;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class Handler implements Runnable {

    private final SocketChannel channel;
    private final SelectionKey sk;

    private final int INPUT_SIZE = 1024;
    private final int SEND_SIZE = 1024;
    private ByteBuffer input = ByteBuffer.allocate(INPUT_SIZE);
    private ByteBuffer output = ByteBuffer.allocate(SEND_SIZE);

    private static final int READING = 0, SENDING = 1;
    private int state = READING;

    Handler(Selector selector, SocketChannel c) throws IOException {
        channel = c;
        c.configureBlocking(false);
        // Optionally try first read now
        sk = channel.register(selector, 0);
        //将Handler作为callback对象
        sk.attach(this);
        //第二步,注册Read就绪事件
        sk.interestOps(SelectionKey.OP_READ);
        selector.wakeup();
    }

    private void read() throws IOException {
        channel.read(input);
        if (inputIsComplete()) {
            process();
            state = SENDING;
            // Normally also do first write now
            //第三步,接收write就绪事件
            sk.interestOps(SelectionKey.OP_WRITE);
        }
    }

    private void send() throws IOException {
        channel.write(output);
        //write完就结束了, 关闭select key
        if (outputIsComplete()) {
            sk.cancel();
        }
    }

    private boolean inputIsComplete() {
        /* ... */
        return false;
    }

    private boolean outputIsComplete() {
        /* ... */
        return false;
    }

    private void process() {
        /* ... */
    }

    public void run() {
        try {
            if (state == READING) {
                read();
            } else if (state == SENDING) {
                send();
            }
        } catch (IOException ex) { /* ... */ }
    }


}
