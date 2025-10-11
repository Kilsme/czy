package com.Kilsme.boot.dao;

import com.Kilsme.boot.model.AdminPost;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface AdminDao {
    //对于首先没有数据地址的进行插入操作
//    @Insert("insert into login_post (id,updated_time,create_time,content,login_url ) VALUES (default,now(),now(), #{content},#{loginUrl})")
//      int insertPost(loginPost loginPost);
    @Select("select * from admin_post where content=#{content}")
    AdminPost selectByContent(int content);
    //对于有数据地址的进行更新操作
    @Update("update admin_post set login_url=#{loginUrl} where content=#{content}")
    int updateLoginUrlByContent(String loginUrl,int content);
}
