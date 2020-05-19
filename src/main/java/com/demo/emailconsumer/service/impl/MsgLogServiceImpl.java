package com.demo.emailconsumer.service.impl;

import com.demo.emailconsumer.dao.MsgLogDao;
import com.demo.emailconsumer.pojo.MsgLog;
import com.demo.emailconsumer.service.MsgLogService;
import com.demo.emailconsumer.utils.JodaTimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Reece Lin
 * @version 1.00
 * @time 2020/1/1 23:06
 */
@Service
public class MsgLogServiceImpl implements MsgLogService {

    @Resource
    private MsgLogDao msgLogDao;

    @Override
    public int updateStatus(String msgId, Integer status) {
        return msgLogDao.updateStatus(msgId, status);
    }


    @Override
    public MsgLog selectByMsgId(String msgId) {
        return msgLogDao.selectByPrimaryKey(msgId);
    }


    @Override
    public List<MsgLog> selectTimeoutMsgs() {
        return msgLogDao.selectTimeoutMsgs();
    }

    @Override
    public int updateRetryNum(String msgId, Date tryTime) {
        Date nextTryTime= JodaTimeUtil.plusMinutes(tryTime,1);

        return msgLogDao.updateTryCount(msgId, tryTime);
    }
}
