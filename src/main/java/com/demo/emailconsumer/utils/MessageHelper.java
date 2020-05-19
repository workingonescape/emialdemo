package com.demo.emailconsumer.utils;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;


/**
 * @author Reece Lin
 * @version 1.00
 * @time 2020/1/2 0:08
 */
public class MessageHelper {

    public static Message objToMsg(Object obj) {
        if (obj == null) {
            return null;
        }
        Message message= MessageBuilder.withBody(JsonUtils.objToStr(obj).getBytes()).build();
        message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);//持久化
        message.getMessageProperties().setContentType(MessageProperties.CONTENT_TYPE_JSON);//json格式
        return message;
    }

    public static <T> T msgToObj(Message message, Class<T> clazz) {
        if (null == message || null == clazz) {
            return null;
        }

        String str = new String(message.getBody());
        T obj = JsonUtils.strToObj(str, clazz);

        return obj;
    }
}
