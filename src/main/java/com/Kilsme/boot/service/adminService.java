package com.Kilsme.boot.service;

import com.Kilsme.boot.dao.AdminDao;
import com.Kilsme.boot.dao.RegisterDao;
import com.Kilsme.boot.dao.UserDao;
import com.Kilsme.boot.model.Register;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;     // ✅ 修改：javax → jakarta

import java.util.List;

import static com.Kilsme.boot.common.registerStatus.Unresolved;

@Service
public class adminService {
    @Resource
    private UserDao userDao;
    @Resource
    private RegisterDao registerDao;
    @Resource
    private AdminDao adminDao;
    public int GetUserAllNum(){
        return userDao.getAllNum();
    }
    public List<Register> getNowNoRegisterApplication(){
        return registerDao.selectByStatus(Unresolved);
    }
    public boolean selectLoginPostByContent(int content){
        if(adminDao.selectByContent(content)==null)return false;
        return true;
    }


}
