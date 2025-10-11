package com.Kilsme.boot.service;

import com.Kilsme.boot.dao.UserDao;
import com.Kilsme.boot.model.User;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;     // ✅ 修改：javax → jakarta
@Service
public class loginService {
    @Resource
      private UserDao userDao;
    //查找用户进行返回 如果有的话返回对象，没有的话进行返回null
    public User  slectUser(String username,String password){
           User user=userDao.selectUser(username,password);
           if(user!=null){
               return user;
           }
        return null;
    }

}
