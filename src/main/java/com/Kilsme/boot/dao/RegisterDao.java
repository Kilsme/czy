package com.Kilsme.boot.dao;

import com.Kilsme.boot.model.Register;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
public interface RegisterDao {
    @Insert("INSERT INTO register (user_name, name, status, password, email, create_time) " +
            "VALUES (#{UserName}, #{Name}, #{status}, #{password}, #{email}, NOW())")
    int insertRegister(Register register);
    @Select("select * from register where status=#{status}")
    List<Register> selectByStatus(int status);//查找没有进行处理的
    @Update("update register set status=#{num} where id=#{id}")
    int  updateStatus(int num,int id);
    @Select("select  * from register where  id=#{id}")
    Register selectById(int id);
}
