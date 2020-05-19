package com.demo.emailconsumer.mq;


import com.demo.emailconsumer.config.RabbitConfig;
import com.demo.emailconsumer.constant.Constant;
import com.demo.emailconsumer.pojo.Mail;
import com.demo.emailconsumer.pojo.MsgLog;
import com.demo.emailconsumer.service.MsgLogService;
import com.demo.emailconsumer.utils.MailUtil;
import com.demo.emailconsumer.utils.MessageHelper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * @author Reece Lin
 * @version 1.00
 * @time 2020/1/2 0:17
 */
@Component
@Slf4j
public class MailConsumer implements BaseConsumer{

    @Autowired
    private MsgLogService logService;


    @Autowired
    private MailUtil mailUtil;

    @Override
    @RabbitListener(queues = RabbitConfig.MAIL_QUEUE_NAME)
    public void consumer(Message message, Channel channel) throws IOException {
        Mail mail= MessageHelper.msgToObj(message, Mail.class);
        log.info("收到消息：{}",mail.toString());
        String msgId=mail.getMsgId();
        MsgLog msgLog=logService.selectByMsgId(msgId);
        if (null==msgLog || Constant.MsgLogStatus.CONSUMED_SUCCESS.equals(msgLog.getStatus())){//幂等性校验
            log.error("消息重复消费，msgId:{}",msgId);
            return;
        }

        MessageProperties properties=message.getMessageProperties();
        long tag=properties.getDeliveryTag();
        boolean success=mailUtil.send(mail);
        if (success){
            logService.updateStatus(msgId, Constant.MsgLogStatus.CONSUMED_SUCCESS);
            channel.basicAck(tag, false);//消费确认
        }else {
            channel.basicNack(tag, false, true);
        }
    }
}
