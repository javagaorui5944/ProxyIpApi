package com.mywebapp.apicontroller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mywebapp.model.ProxyIp;
import com.mywebapp.redis.JedisDao;
import com.mywebapp.service.UserService;
import com.mywebapp.util.CommonUtil;
import com.mywebapp.util.ParamUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
     *
     * @return
     * @deprecated 获取爬虫api
     */
    @ApiOperation(value = "获取Ip", notes = "")
    @RequestMapping(value = "/getIp/", method = RequestMethod.GET)
    public Map<String, String> getIp(HttpServletRequest request) {

        String ip = request.getRemoteAddr();
        Map<String, String> map = new HashMap<String, String>();
        map.put("ip", ip);
        return map;
    }

    /**
     * author:javaGr_ais
     *
     * @return
     * @deprecated 获取爬虫api
     */
    @ApiOperation(value = "获取爬虫", notes = "")
    @RequestMapping(value = "/proxyIpApi/{user}", method = RequestMethod.GET)
    public ProxyIp getUser(@PathVariable("user")String user,String key) {

        System.out.println(user+key);

        ProxyIp proxyIp = new ProxyIp();

        proxyIp.setList(JedisDao.getProxyIp());
        return proxyIp;
    }

    /**
     * author:javaGr_ais
     *
     * @return
     * @deprecated 获取爬虫api
     */
    @ApiOperation(value = "获取爬虫", notes = "")
    @RequestMapping(value = "/allProxyIpApi/", method = RequestMethod.GET)
    public ProxyIp getAllUser() {

        ProxyIp proxyIp = new ProxyIp();

        proxyIp.setList(JedisDao.getAllProxyIp());
        return proxyIp;
    }

    /**
     * @param userName
     * @param userPwd
     * @return
     * @author javaGr_ais
     * @deprecated 登陆api
     */
    @ApiOperation(value = "登陆", notes = "")
    @RequestMapping(value = "/login/", method = RequestMethod.POST)
    public Map<String, Object> login(@RequestParam String userName, @RequestParam String userPwd) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(userName, userPwd);
        return map;
    }

    /**
     * author:javaGr_ais
     *
     * @param userEmail
     * @param userPwd
     * @param userName
     * @return
     * @deprecated 获取爬虫api
     */
    @RequestMapping(value = "/register/", method = RequestMethod.POST)
    public Map<String, Object> register(@RequestParam String userName, @RequestParam String userPwd, @RequestParam String userEmail) {


        return null;
    }

    @RequestMapping(value = "/emaiCheck/", method = RequestMethod.GET)
    public Map<String, Object> emaiCheck(@RequestParam String token) {


        return null;
    }

    @ApiOperation(value = "获取users", notes = "")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<Map<String, Object>> users() {


        return userService.user();
    }

    @ApiOperation(value = "获取user", notes = "")
    @RequestMapping(value = "/user_info", method = RequestMethod.GET)
    public JSONObject user_info(HttpSession session) {


        JSONObject user = (JSONObject) session.getAttribute("user");
        if(user != null){

            return CommonUtil.constructResponse(1, "user_info", user);
        }
        return CommonUtil.constructResponse(0, "user详情", null);
    }

    /**
     * 授权github用户登录
     *
     * @return
     */
    @RequestMapping(value = "RegisteredByGithub")
    @ResponseBody
    public JSONObject RegisteredByGithub(@RequestParam(value = "code", required = false) String code, HttpSession session, HttpServletResponse response) {


        try {
            String me = CommonUtil.sendPost
                    ("https://github.com/login/oauth/access_token?client_id=" + ParamUtil.client_id + "&client_secret=" + ParamUtil.client_secret + "&code=" + code + "&redirect_uri=http://127.0.0.1:8080/RegisteredByGithub", null);

            String atoke = me.split("&")[0];

            String res = CommonUtil.sendGet("https://api.github.com/user?" + atoke + "");
            JSONObject user = (JSONObject) JSON.parse(res);

            if (user.getString("login") != null) {

                session.setAttribute("user", user);
                response.sendRedirect("http://127.0.0.1:8080/api.html");
            }

            return null;

        } catch (Exception e) {

            e.printStackTrace();
            return CommonUtil.constructResponse(0, "user详情", null);

        }
    }
}