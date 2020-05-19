package com.demo.emailconsumer.service;



import com.demo.emailconsumer.pojo.MsgLog;

import java.util.Date;
import java.util.List;

/**
 * @author Reece Lin
 * @version 1.00
 * @time 2020/1/1 23:06
 */
public interface MsgLogService {

    int updateStatus(String msgId, Integer status);

    MsgLog selectByMsgId(String msgId);

    List<MsgLog> selectTimeoutMsgs();

    int updateRetryNum(String msgId, Date nextTryTime);
}
