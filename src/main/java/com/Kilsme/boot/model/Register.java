package com.Kilsme.boot.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Register {
    private int id;
    private String UserName;
    private String password;
    private String Name;//用户的真实姓名   只能进行注册普通用户
    private String email;
//    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private String createTime;
    private int status;//0代表没有进行处理  1代表同意 2代表拒绝
}
