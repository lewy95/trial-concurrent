package cn.xzxy.lewy.nio.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * 服务端：接收客户端发送消息，收取到消息后，给发送方一个回应
 */
public class TestDatagramChannelServer {

    public static void main(String[] args) throws IOException {

        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.bind(new InetSocketAddress(9995));

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        byte b[];
        while(true) {
            buffer.clear();
            SocketAddress socketAddress = datagramChannel.receive(buffer);
            if (socketAddress != null) {
                int position = buffer.position();
                b = new byte[position];
                buffer.flip();
                for(int i=0; i<position; ++i) {
                    b[i] = buffer.get();
                }
                System.out.println("receive remote " +  socketAddress.toString() + ":"  + new String(b, "UTF-8"));
                //接收到消息后给发送方回应
                sendReback(socketAddress,datagramChannel);
            }
        }
    }

    private static void sendReback(SocketAddress socketAddress,DatagramChannel datagramChannel) throws IOException {
        String message = "I has receive your message";
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(message.getBytes("UTF-8"));
        buffer.flip();
        datagramChannel.send(buffer, socketAddress);
    }
}
