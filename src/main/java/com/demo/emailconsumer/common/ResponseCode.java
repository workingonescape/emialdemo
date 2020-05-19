package com.demo.emailconsumer.common;

/**
 * @author Reece Lin
 * @version 1.00
 * @time 2020/1/2 0:06
 */
public enum ResponseCode {

    MAIL_SEND_SUCCESS(0,"发送成功");


    private String msg;
    private Integer code;


    ResponseCode( Integer code,String msg) {
        this.msg = msg;
        this.code = code;
    }


    public String getMsg() {
        return msg;
    }

    public Integer getCode() {
        return code;
    }
}
