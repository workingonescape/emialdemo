package com.demo.emailconsumer.service.impl;


import com.demo.emailconsumer.common.ResponseCode;
import com.demo.emailconsumer.common.ServerResponse;
import com.demo.emailconsumer.config.RabbitConfig;
import com.demo.emailconsumer.pojo.Mail;
import com.demo.emailconsumer.pojo.MsgLog;
import com.demo.emailconsumer.service.PublisherService;
import com.demo.emailconsumer.utils.MessageHelper;
import com.demo.emailconsumer.utils.RandomUtil;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Reece Lin
 * @version 1.00
 * @time 2020/1/1 23:32
 */
@Service
public class PublisherServiceImpl implements PublisherService {
    //@AutoWired
    // private MsgLogMapper logMapper;


    @Autowired
    private RabbitTemplate template;


    @Override
    public ServerResponse send(Mail mail) {
        String msgId= RandomUtil.UUID32();
        mail.setMsgId(msgId);
        MsgLog msgLog=new MsgLog();
        CorrelationData data=new CorrelationData(msgId);
        template.convertAndSend(RabbitConfig.MAIL_EXCHANGE_NAME, RabbitConfig.MAIL_ROUTING_KEY_NAME, MessageHelper.objToMsg(mail),data);
        return ServerResponse.success(ResponseCode.MAIL_SEND_SUCCESS.getMsg());
    }
}
