package com.demo.emailconsumer.constant;

/**
 * @author Reece Lin
 * @version 1.00
 * @time 2020/1/1 23:15
 */
public class Constant {


    public interface MsgLogStatus{
            Integer DELIVERING = 0;// 消息投递中
            Integer DELIVER_SUCCESS = 1;// 投递成功
            Integer DELIVER_FAIL = 2;// 投递失败
            Integer CONSUMED_SUCCESS = 3;// 已消费
    }
}
