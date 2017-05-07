package com.mywebapp.service.imp;

import com.mywebapp.bean.User;
import com.mywebapp.mapper.UserMapper;
import com.mywebapp.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by gaorui on 17/1/11.
 */
@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public List<Map<String,Object>> user() {

        return  userMapper.getUsers();
    }

    @Override
    public User getUserByLogin(String login) {

        return userMapper.getUserByLogin(login);
    }

    @Override
    public int insertUser(String u_name, String u_email, String u_key, String u_nickname,String u_created_at,String u_avatar_url) {

        return userMapper.insertUser(u_name,u_email,u_key,u_nickname,u_created_at,u_avatar_url);
    }

    @Override
    public User check(String login, String u_key) {

        return userMapper.check(login,u_key);
    }


}
