package com.demo.emailconsumer.config;


import com.demo.emailconsumer.constant.Constant;
import com.demo.emailconsumer.service.MsgLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Reece Lin
 * @version 1.00
 * @time 2020/1/1 23:05
 */
@Configuration
@Slf4j
public class RabbitConfig {

    @Autowired
    private CachingConnectionFactory factory;

    @Autowired
    private MsgLogService logService;

    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate template=new RabbitTemplate(factory);
        template.setMessageConverter(converter());
        template.setConfirmCallback(((correlationData, ack, cause) -> {
            if (ack){
                log.info("消息发送成功到Exchange");
                String msgId=correlationData.getId();
                logService.updateStatus(msgId, Constant.MsgLogStatus.DELIVER_SUCCESS);
            }else {
                log.error("消息发送到Exchange失败");
            }
        }));
        //触发setReturnCallBack回调必须设置mandatory=true,否则Exchange没有找到Queue就会丢弃消息
        template.setMandatory(true);
        template.setReturnCallback(((message, replyCode,replyText,exchange,routingKey) -> {
            log.error("消息从Exchange路由到Queue失败: exchange: {}, route: {}, replyCode: {}, replyText: {}, message: {}", exchange, routingKey, replyCode, replyText, message);
        }));
        return template;
    }

    @Bean
    public Jackson2JsonMessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    public static final String MAIL_QUEUE_NAME="mail.queue";
    public static final String MAIL_EXCHANGE_NAME="mail.exchange";
    public static final String MAIL_ROUTING_KEY_NAME ="mail.routing.key";

    @Bean
    public Queue mailQueue(){
        return new Queue(MAIL_QUEUE_NAME, true);
    }

    @Bean
    public Exchange mailExchange(){
        return new DirectExchange(MAIL_EXCHANGE_NAME, true, false);
    }

    @Bean
    public BindingBuilder.GenericArgumentsConfigurer mailBinding() {
      return   BindingBuilder.bind(mailQueue()).to(mailExchange()).with(MAIL_ROUTING_KEY_NAME);
    }
}
