package com.example.springbootdemo.mapper;

import com.example.springbootdemo.model.Authority;
import com.example.springbootdemo.model.User;
import org.apache.ibatis.annotations.*;


import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM t_user WHERE username=#{username}")
    User loadUserByUsername(String username);

    @Select("SELECT * FROM t_authority a,t_user_authority u WHERE a.id=u.authority_id and u.user_id=#{id}")
    List<Authority> getUserRolesByUid (Integer id) ;


}
