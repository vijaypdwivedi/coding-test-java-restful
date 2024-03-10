package com.coding.repository;

import com.coding.dto.UserInfo;
import com.coding.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserRepository {
    @Select("SELECT id, username, password, role FROM users WHERE username = #{username}")
    public UserInfo findByUsername(String username);

    @Insert("INSERT INTO users(username,password,role) VALUES(#{username},#{password},#{role})")
    public void saveUser(User user);

}
