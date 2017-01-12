package com.mywebapp.apicontroller;

import com.mywebapp.model.ProxyIp;
import com.mywebapp.redis.JedisDao;
import com.mywebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * Created by gaorui on 17/1/11.
 */
@RestController
public class UserController {
    @Autowired
    UserService userService;


    @RequestMapping(value = "/proxyIpApi/{token}", method = RequestMethod.GET)
    public ProxyIp getUser(@PathVariable String token) {

        ProxyIp proxyIp = new ProxyIp();

        proxyIp.setList(JedisDao.getProxyIp());
        return proxyIp;
    }

    @RequestMapping(value = "/login/", method = RequestMethod.POST)
    public Map<String, Object> login(@RequestParam String userName, @RequestParam String userPwd) {

        return null;
    }

    @RequestMapping(value = "/register/", method = RequestMethod.POST)
    public Map<String, Object> register(@RequestParam String userName, @RequestParam String userPwd, @RequestParam String userEmail) {


        return null;
    }

    @RequestMapping(value = "/emaiCheck/", method = RequestMethod.GET)
    public Map<String, Object> emaiCheck(@RequestParam String token) {


        return null;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<Map<String,Object>> users() {


        return userService.user();
    }
}