package com.Kilsme.boot.model;

import lombok.Data;

@Data
public class AdminPost {
    private int id;
    private int content;
    private String createTime;
    private String updateTime;
    private String loginUrl;
}
