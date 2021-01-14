package cn.xzxy.lewy.disruptor;

import com.lmax.disruptor.RingBuffer;

/**
 * 生产者
 */
public class OrderEventProducer {
    private final RingBuffer<OrderEvent> ringBuffer;

    public OrderEventProducer(RingBuffer<OrderEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(String orderId) {
        long sequence = ringBuffer.next();
        try {
            OrderEvent orderEvent = ringBuffer.get(sequence);
            orderEvent.setId(orderId);
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}

