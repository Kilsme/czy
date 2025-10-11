package com.Kilsme.boot.model;

import lombok.Data;

@Data
public class PostComments {
    private int id;
    private int PostId;
    private int UserId;
    private int ParentId;
    private String content;
    private int  status;
    private String createdAt;
    private String updatedAt;
}
