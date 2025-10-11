package com.Kilsme.boot.dao;
import com.Kilsme.boot.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserDao {
    @Select("select  * from user where username=#{param1} AND password=#{param2}")
    User selectUser(String username, String password);
    @Select("select count(*) from user where person_id !=3")
    int  getAllNum();
    @Select("select * from user where person_id !=3 order by id limit 10 offset #{offset}")
    List<User>selectByPage(@Param("offset") int page);
     @Update("update user set  block_id=1  where id=#{id}")
    int Disable(int id);
     @Update("update user set  block_id=0  where id=#{id}")
    int Enable(int id);
    @Select("select  * from user where id=#{id}")
    User selectUserById(int id);
    @Select("select  * from user where username=#{username}")
    User selectUserByUserName(String username);
    @Insert("insert into  user (person_id,username,password,block_id,name,email) values (#{personId},#{UserName},#{password},#{blockId},#{Name},#{email})")
    int insertUser(User user);
}
