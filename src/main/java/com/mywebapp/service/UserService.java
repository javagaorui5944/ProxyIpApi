package com.mywebapp.service;


import com.mywebapp.bean.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by gaorui on 17/1/11.
 */
public interface UserService {
    public List<Map<String,Object>> user();
    User getUserByLogin(String login);
    int insertUser(String u_name,String u_email, String u_key,String u_nickname,String u_created_at, String u_avatar_url);
    User check(String login,String u_key);
}
