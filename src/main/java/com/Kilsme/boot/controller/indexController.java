package com.Kilsme.boot.controller;

import Vo.PostVo;
import com.Kilsme.boot.dao.AdminDao;
import com.Kilsme.boot.dao.UserDao;
import com.Kilsme.boot.model.Post;
import com.Kilsme.boot.model.User;
import com.Kilsme.boot.model.AdminPost;
import com.Kilsme.boot.service.PostService;
import com.Kilsme.boot.service.adminService;
import com.Kilsme.boot.service.loginService;

// ✅ 修改：javax → jakarta
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

import static com.Kilsme.boot.common.AdminPostContent.LoginPost;

@Controller
public class indexController {
    @Resource
    private adminService adminService;
    @Resource
    private loginService loginService;
    @Resource
    private PostService postService;
    @Resource
    private UserDao userDao;
    @Resource
    private AdminDao adminDao;
    @GetMapping("IndexView")
      public String IndexView(Model model){
        int content=LoginPost;
        AdminPost loginPost=adminDao.selectByContent(content);
        model.addAttribute("loginUrl",loginPost.getLoginUrl());
          return "index";
      }
      @PostMapping("PersonChoose")
      public String PersonChoose(String username, String password, Model model, HttpSession session){
            //对其进行查找名字
          User user=loginService.slectUser(username,password);
          if(user==null){
              model.addAttribute("noPerson",1);
              return "index";
              //如果没有找到的话，直接返回到刚开始的页面
          }
          if(user.getPersonId()==3){
              //如果是管理员的话
              session.setAttribute("admin",user);
              return "redirect:adminView";
          }
          //用户的话直接返回用户页面
          session.setAttribute("user",user);
          return "redirect:userView";

      }
      @GetMapping("userView")
      public String userView(Model model,HttpSession session){
        List<PostVo>list=postService.getPostVos(session);
        model.addAttribute("PostVoList",list);
        model.addAttribute("userId",((User)session.getAttribute("user")).getId());
        return "user";
      }
      //负责进行负责进行注册跳转
      @GetMapping("registerView")
    public String registerView(){
        return "register";
    }
}
