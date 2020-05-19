package com.demo.emailconsumer.mq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;

import java.io.IOException;

/**
 * @author Reece Lin
 * @version 1.00
 * @time 2020/1/2 0:29
 */
public interface BaseConsumer {
    void consumer(Message message, Channel channel) throws IOException;
}
