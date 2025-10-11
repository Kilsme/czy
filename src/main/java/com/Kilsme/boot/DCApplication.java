package com.Kilsme.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.Kilsme.boot.dao")
@SpringBootApplication
public class DCApplication {
    public static void main(String[] args) {
        SpringApplication.run(DCApplication.class,args);
        //设置启动类
    }
}
