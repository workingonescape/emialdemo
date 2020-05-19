package com.demo.emailconsumer.utils;


import com.demo.emailconsumer.pojo.Mail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * @author Reece Lin
 * @version 1.00
 * @time 2020/1/1 22:49
 */
@Component
@Slf4j
public class MailUtil {

    @Value("${spring.mail.from}")
    private String from;

    @Autowired
    private JavaMailSender sender;

    public boolean send(Mail mail){
        String to=mail.getTo();
        String title=mail.getTitle();
        String content=mail.getContent();
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(title);
        message.setText(content);

        try {
            sender.send(message);
            log.info("邮件发送成功");
            return true;
        } catch (MailException e) {
            log.error("邮件发送失败");
            return false;
        }
    }
}
