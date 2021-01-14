package cn.xzxy.lewy.disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 消费者
 */
@Slf4j
public class OrderEventConsumer implements EventHandler<OrderEvent>, WorkHandler<OrderEvent> {
    @Override
    public void onEvent(OrderEvent event, long sequence, boolean endOfBatch) {
        log.info("event: {}, sequence: {}, endOfBatch: {}", event, sequence, endOfBatch);
    }

    @Override
    public void onEvent(OrderEvent event) {
        log.info("event: {}", event);
    }
}

