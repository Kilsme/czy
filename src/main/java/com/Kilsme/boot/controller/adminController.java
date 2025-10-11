package com.Kilsme.boot.controller;

import com.Kilsme.boot.common.userLevel;
import com.Kilsme.boot.common.blockStatus;
import com.Kilsme.boot.common.registerStatus;
import com.Kilsme.boot.dao.AdminDao;
import com.Kilsme.boot.dao.RegisterDao;
import com.Kilsme.boot.dao.UserDao;
import com.Kilsme.boot.model.Register;
import com.Kilsme.boot.model.User;
import com.Kilsme.boot.model.AdminPost;
import com.Kilsme.boot.service.PostService;
import com.Kilsme.boot.service.UploadService;
import com.Kilsme.boot.service.adminService;

import jakarta.annotation.Resource;           // ✅ 修改：javax → jakarta
import jakarta.servlet.http.HttpSession;     // ✅ 修改：javax → jakarta

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.Kilsme.boot.common.AdminPostContent.LoginPost;

@Controller
public class adminController{
    @Resource
    private adminService adminService;
    @Resource
    private UserDao userDao;
    @Resource
    private PostService postService;
    @Resource
    private RegisterDao registerDao;
    @Autowired
    private UploadService uploadService;
    @Resource
    private AdminDao adminDao;

    @GetMapping("adminView")
    public String adminView(Model model, @RequestParam(defaultValue = "1") int page){//默认数量是1
        //对其进行显示，每个用户的
        //第一要先统计总人数
        //第二进行选择页数进行分页查询
        if(page<1){
            page=1;
        }
        int totalPages=(adminService.GetUserAllNum()+10-1)/10;
        if(page>totalPages){
            page=totalPages;
        }
        int start=(page-1)*10;
        List<User> data=userDao.selectByPage(start);
        model.addAttribute("UserList",data);
        List<Integer>pageList=pages(page,totalPages);
        model.addAttribute("pagesList",pageList);
        model.addAttribute("nowPage",page);//现在的页数
        return "admin";
    }
    @GetMapping("changeBlock")
    public String changeBlock(int id){
        User user = userDao.selectUserById(id);
        if(user.getBlockId()==0){
            userDao.Disable(id);
        }else{
            userDao.Enable(id);
        }
        return "redirect:adminView";
    }

    public static List<Integer> pages(int p,int total) {
        List<Integer> list = new ArrayList<>();
        if(total<5){
            for(int i=1;i<=total;i++){
                list.add(i);
            }
        }
        else{
            if(p<=3){
                for(int i=1;i<total;i++){
                    list.add(i);
                }
            }else if(p>=total-2){
                for(int i=total-4;i<=total;i++){
                    list.add(i);
                }
            }else{
                for(int i=p-2;i<=p+2;i++){
                    list.add(i);
                }
            }
        }
        return list;
    }
    @GetMapping("registerApplicationView")
    public String registerApplicationView(Model model){
      //查询每个进行注册申请的
       List<Register>registers=adminService.getNowNoRegisterApplication();
       model.addAttribute("registers",registers);
        return "Registration Application";
    }
    @GetMapping("registerApplicationOperation")
    public String registerApplicationOperation(@RequestParam String action,int id){
        if(action.equals("reject")){
            //拒绝注册申请
             registerDao.updateStatus(registerStatus.Rejection,id);
             return "Registration Application";
        }
        //同意注册申请
        registerDao.updateStatus(registerStatus.Agreement,id);
        //然后进行添加
        Register register=registerDao.selectById(id);
        User user=new User();
        user.setUserName(register.getUserName());
        user.setName(register.getName());
        user.setEmail(register.getEmail());
        user.setPassword(register.getPassword());
        user.setBlockId(blockStatus.Enabled);
        user.setPersonId(userLevel.Regular);
        userDao.insertUser(user);
        return "Registration Application";
    }
    @PostMapping("/loginUpload")
    public String handleUpload(
            @RequestParam("image") MultipartFile file,
            Model model, HttpSession session) {
        if (file.isEmpty()) {
            model.addAttribute("error", "请选择图片");
            return "uploadPostView";
        }
        User user=(User) session.getAttribute("user");
        try (InputStream in = file.getInputStream()) {
            String loginUrl = uploadService.upload(in, file.getOriginalFilename());
            //这个就是图片地址进行云存储
            if (loginUrl != null) {
                model.addAttribute("success", true);
                model.addAttribute("loginUrl", loginUrl);
                 AdminPost loginPost=new AdminPost();
                 loginPost.setContent(LoginPost);
                 loginPost.setLoginUrl(loginUrl);
                int content=LoginPost;
                adminDao.updateLoginUrlByContent(loginUrl,content);

            } else {
                model.addAttribute("error", "上传失败，请重试");
            }
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "文件读取失败");
        }
        return "LoginPageImageUploadView";
    }
    @GetMapping("loginPostView")
    public String LoginPostView(){
        return "LoginPageImageUploadView";
    }

}
