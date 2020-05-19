package com.demo.emailconsumer.common;

import lombok.Data;

/**
 * @author Reece Lin
 * @version 1.00
 * @time 2020/1/1 23:31
 */
@Data
public class ServerResponse <T> {

    private String msg;
    private Integer code;
    private T data;

    public ServerResponse(String msg) {
        this.msg = msg;
    }

    public ServerResponse(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }

    public ServerResponse(String msg, Integer code, T data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }



    public static ServerResponse success(String msg){
        return new ServerResponse(msg);
    }


}
