package com.mywebapp.service;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by gaorui on 17/1/11.
 */
@Service
public interface UserService {
    public List<Map<String,Object>> user();
}
