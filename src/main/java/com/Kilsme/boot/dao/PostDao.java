package com.Kilsme.boot.dao;

import com.Kilsme.boot.model.Post;
import com.Kilsme.boot.model.PostFavorites;
import com.Kilsme.boot.model.PostLikes;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PostDao {
    @Insert("INSERT INTO post (id,user_id,title, content,image_url,created_at,updated_at ) VALUES (default,#{userId},#{title}, #{content}, #{imageUrl},now(),now())")
    int insertPost(Post post);
    @Select("select * from post ORDER BY updated_at DESC")
    List<Post>selectAllPost();
    @Select("select * from post where user_id=#{id}")
    List<Post>selectPostByUserId(int id);
    //进行统计点赞数量
    @Select("select  count(*) from post_likes where post_id=#{postId}")
    int selectLikesByPostId(int postId);
    @Select("select  count(*) from post_favorites where post_id=#{postId}")
    int selectFavoritesByPostId(int postId);
    @Select("select count(*) from post_likes where post_id=#{param1} AND user_id=#{param2}")
    int  selectLikeByUserIdAndPostId( int postId, int userId);
    @Select("select count(*) from post_favorites where post_id=#{param1} AND user_id=#{param2}")
    int  selectFavoritesByUserIdAndPostId( int postId,  int userId);
    @Insert("insert into post_likes (id,post_id,user_id,created_at) values (default,#{PostId},#{UserId},now())")
    int insertPostLike(PostLikes postLikes);
    @Delete("delete from post_likes where post_id=#{param1} AND user_id=#{param2}")
    int deletePostLike(int postId,int userId);
    @Insert("insert into post_favorites (id,post_id,user_id,created_at) values (default,#{PostId},#{UserId},now())")
    int insertFavorite(PostFavorites postFavorites);
    @Delete("delete  from post_favorites where post_id=#{param1} and user_id=#{param2}")
    int deletePostFavorite(int postId,int userId);
    @Select("select * from post_favorites where user_id=#{userId}")
    List<PostFavorites> selectFavoriteByUserId(int userId);//所以对文章进行删除的时候我们进行考虑这个文章是否被别人进行收藏
     @Select("select * from post where id=#{postId}")
    Post selectByPostId(int postId);
}
