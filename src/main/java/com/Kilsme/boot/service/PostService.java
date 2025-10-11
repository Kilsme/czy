package com.Kilsme.boot.service;

import Vo.PostVo;
import com.Kilsme.boot.dao.PostDao;
import com.Kilsme.boot.model.PostFavorites;
import com.Kilsme.boot.model.PostLikes;
import com.Kilsme.boot.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import com.Kilsme.boot.model.Post;
import jakarta.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    //增加一个帖子，并且将其增加到数据库表格中
    @Resource
    private PostDao postDao;

    public void insertPost(Post post) {
        postDao.insertPost(post);
    }

    public List<Post> selectAllPost() {
        List<Post> list = postDao.selectAllPost();
        return list;
    }

    public List<Post> selectPostByUserId(int id) {
        return postDao.selectPostByUserId(id);
    }

    public List<PostVo> getPostVos(HttpSession session) {
        List<Post> list = selectAllPost();
        User user = (User) session.getAttribute("user");
        List<PostVo> postVos = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Post post = list.get(i);
            PostVo postVo = new PostVo();
            postVo.setUserId(post.getUserId());
            postVo.setId(post.getId());
            postVo.setContent(post.getContent());
            postVo.setTitle(post.getTitle());
            postVo.setCreatedAt(post.getCreatedAt());
            postVo.setImageUrl(post.getImageUrl());
            if (postDao.selectLikeByUserIdAndPostId(post.getId(), user.getId()) != 0) {
                postVo.setLikes(1);
            }
            if (postDao.selectFavoritesByUserIdAndPostId(post.getId(), user.getId()) != 0) {
                postVo.setFavorites(1);
            }
            postVo.setLikes(postDao.selectLikesByPostId(post.getId()));
            postVo.setFavorites(postDao.selectFavoritesByPostId(post.getId()));
            postVos.add(postVo);

        }
        return postVos;
    }
    public  void giveLike(int postId,int userId){
        PostLikes postLikes=new PostLikes();
        postLikes.setPostId(postId);
        postLikes.setUserId(userId);
        postDao.insertPostLike(postLikes);
    }
    public void giveUpLike(int postId,int userId){
         postDao.deletePostLike(postId,userId);
    }
    public void giveFavorite(int postId,int userId){
        PostFavorites postFavorites=new PostFavorites();
        postFavorites.setPostId(postId);
        postFavorites.setUserId(userId);
        postDao.insertFavorite(postFavorites);
    }
    public void giveUpFavorite(int postId,int userId){
        postDao.deletePostFavorite(postId,userId);
    }
    public List<Post> getFavoritePostByUserId(int userId){
          List<PostFavorites>postFavoritesList=postDao.selectFavoriteByUserId(userId);
          List<Post> postList=new ArrayList<>();
          for(int i=0;i<postFavoritesList.size();i++){
              postList.add(postDao.selectByPostId(postFavoritesList.get(i).getPostId()));
          }
          return postList;
    }
}