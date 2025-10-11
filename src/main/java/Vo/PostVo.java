package Vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class PostVo {
    private int id;
    private String title;
    private String content;
    private int userId;
    private String createdAt;
    private int Likes;
    private int comments;
    private int favorites;
    private String imageUrl;
    private  int isLike;
    private int isFavorites;
}
