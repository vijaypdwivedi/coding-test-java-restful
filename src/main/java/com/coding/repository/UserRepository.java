package com.coding.repository;

import com.coding.dto.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserRepository {
    @Select("SELECT username, password, role FROM users WHERE username = #{username}")
    public UserInfo findByUsername(String username);



}
