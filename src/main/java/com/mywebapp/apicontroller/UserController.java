package com.mywebapp.apicontroller;

import com.mywebapp.model.ProxyIp;
import com.mywebapp.redis.JedisDao;
import com.mywebapp.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by gaorui on 17/1/11.
 */
@RestController
public class UserController {
    @Autowired
    UserService userService;


    /**
     * author:javaGr_ais
     * @deprecated 获取爬虫api
     * @return
     */
    @ApiOperation(value="获取Ip", notes="")
    @RequestMapping(value = "/getIp/", method = RequestMethod.GET)
    public Map<String,String> getIp(HttpServletRequest request) {

        String ip = request.getRemoteAddr();
        Map<String,String> map = new HashMap<String,String>();
        map.put("ip",ip);
        return map;
    }

    /**
     * author:javaGr_ais
     * @deprecated 获取爬虫api
     * @return
     */
    @ApiOperation(value="获取爬虫", notes="")
    @RequestMapping(value = "/proxyIpApi/", method = RequestMethod.GET)
    public ProxyIp getUser() {

        ProxyIp proxyIp = new ProxyIp();

        proxyIp.setList(JedisDao.getProxyIp());
        return proxyIp;
    }

    /**
     * author:javaGr_ais
     * @deprecated 获取爬虫api
     * @return
     */
    @ApiOperation(value="获取爬虫", notes="")
    @RequestMapping(value = "/allProxyIpApi/", method = RequestMethod.GET)
    public ProxyIp getAllUser() {

        ProxyIp proxyIp = new ProxyIp();

        proxyIp.setList(JedisDao.getAllProxyIp());
        return proxyIp;
    }
    /**
     * @author javaGr_ais
     * @param userName
     * @param userPwd
     * @deprecated 登陆api
     * @return
     */
    @ApiOperation(value="登陆", notes="")
    @RequestMapping(value = "/login/", method = RequestMethod.POST)
    public Map<String, Object> login(@RequestParam String userName, @RequestParam String userPwd) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(userName,userPwd);
        return map;
    }

    /**
     * author:javaGr_ais
     * @param userEmail
     * @param userPwd
     * @param userName
     * @deprecated 获取爬虫api
     * @return
     */
    @RequestMapping(value = "/register/", method = RequestMethod.POST)
    public Map<String, Object> register(@RequestParam String userName, @RequestParam String userPwd, @RequestParam String userEmail) {


        return null;
    }

    @RequestMapping(value = "/emaiCheck/", method = RequestMethod.GET)
    public Map<String, Object> emaiCheck(@RequestParam String token) {


        return null;
    }

    @ApiOperation(value="获取users", notes="")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<Map<String,Object>> users() {


        return userService.user();
    }
}