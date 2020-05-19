package com.demo.emailconsumer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.demo.emailconsumer.dao")
public class EmailconsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmailconsumerApplication.class, args);
    }

}
