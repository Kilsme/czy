package com.Kilsme.boot.model;

import lombok.Data;

@Data
public class PostLikes {
     private int id;
     private int PostId;
     private int UserId;
     private String CreatedAt;
}
