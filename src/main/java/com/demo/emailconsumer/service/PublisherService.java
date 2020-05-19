package com.demo.emailconsumer.service;


import com.demo.emailconsumer.common.ServerResponse;
import com.demo.emailconsumer.pojo.Mail;

/**
 * @author Reece Lin
 * @version 1.00
 * @time 2020/1/1 23:31
 */
public interface PublisherService {

    ServerResponse send(Mail mail);
}
