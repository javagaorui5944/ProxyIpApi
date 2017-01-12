package com.mywebapp.service.imp;

import com.mywebapp.mapper.UserMapper;
import com.mywebapp.service.UserService;
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


}
