package com.Kilsme.boot.model;

import lombok.Data;

@Data
public class User {
    private int personId;//1代表普通用户  2代表超级用户 3代表管理员身份
    private String UserName;
    private String password;
    private int id;//主键id
    private int blockId;   //禁用id账号，0代表没有禁用  1代表禁用
    private String Name;
    private String email;
}
