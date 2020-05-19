package com.demo.emailconsumer.dao;


import com.demo.emailconsumer.pojo.MsgLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository("msgLogDao")
public interface MsgLogDao {
    int deleteByPrimaryKey(String msgId);

    int insert(MsgLog record);

    int insertSelective(MsgLog record);

    MsgLog selectByPrimaryKey(@Param("msgId") String msgId);

    int updateByPrimaryKeySelective(MsgLog record);

    int updateByPrimaryKey(MsgLog record);

    int updateStatus(@Param("msgId") String msgId, @Param("status") Integer status);

    List<MsgLog> selectTimeoutMsgs();

    int updateTryCount(@Param("msgId") String msgId, @Param("nextTryTime") Date nextTryTime);
}