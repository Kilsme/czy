package com.Kilsme.boot.model;

import lombok.Data;

@Data
public class Post {
    private int id;
    private int userId;
    private String title;
    private String content;
    private String imageUrl;
    private String  createdAt;
    private String  updatedAt;
}
