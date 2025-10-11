package com.Kilsme.boot.model;

import lombok.Data;

@Data
public class PostFavorites {
    private int  id;
    private int PostId;
    private int UserId;
    private String  CreatedAt;
}
