package com.Kilsme.boot.controller;

import com.Kilsme.boot.dao.RegisterDao;
import com.Kilsme.boot.dao.UserDao;
import com.Kilsme.boot.model.Post;
import com.Kilsme.boot.model.Register;
import com.Kilsme.boot.model.User;
import com.Kilsme.boot.service.PostService;

import jakarta.annotation.Resource;           // ✅ 修改：javax → jakarta
import jakarta.servlet.http.HttpSession;     // ✅ 修改：javax → jakarta

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

import static com.Kilsme.boot.common.registerStatus.Unresolved;

@Controller
public class userController {
    //TODO
    //AD2o5FD6X-HxK47Gx9Ixm-KnX5wXbV9E7NrFoRaN   SK
    //8MXvyykVC3HjCJSWLSdzrdLCquCnNWXoThppE_zX    AK
    @Resource
    private RegisterDao registerDao;
    @Resource
    private UserDao userDao;
    @Resource
    private PostService postService;

    @PostMapping("registerUser")
    public String registerUser(Register register, Model model) {
        //先进行查重
        User user = userDao.selectUserByUserName(register.getUserName());
        if (user != null) {
            model.addAttribute("Existing", 1);
            return "register";
        }
        register.setStatus(Unresolved);
        registerDao.insertRegister(register);
        return "index";
    }

    @GetMapping("uploadPostView")
    public String uploadPostView() {
        return "uploadPostView";
    }

    @GetMapping("PostListViewByUser")
    public String PostListViewByUser(Model model, HttpSession session) {
        List<Post> list = postService.selectPostByUserId(((User) session.getAttribute("user")).getId());
        model.addAttribute("userPostList", list);
        return "PostListByUser";
    }

    //点赞
    @GetMapping("giveLike")
    public String Like(int postId, int userId) {
         postService.giveLike(postId,userId);
        return "redirect:userView";
    }

    //取消点赞
    @GetMapping("UnLike")
    public String UnLike(int postId, int userId) {
     postService.giveUpLike(postId,userId);
        return "redirect:userView";
    }
    @GetMapping("favorite")
  public String favorite(int postId,int userId){
        postService.giveFavorite(postId,userId);
      return "redirect:userView";
  }
  @GetMapping("RemoveFavorite")
  public String RemoveFavorite(int postId,int userId){
      postService.giveUpFavorite(postId,userId);
      return "redirect:userView";
  }
  @GetMapping("userFavoriteView")
  public String userFavoriteView(int userId,Model model){
      List<Post> postList = postService.selectPostByUserId(userId);
      model.addAttribute("postList",postList);
      return "userFavorite";
  }
}
