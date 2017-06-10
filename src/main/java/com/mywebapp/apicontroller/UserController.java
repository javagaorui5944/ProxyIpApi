package com.mywebapp.apicontroller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mywebapp.bean.User;
import com.mywebapp.model.ProxyIp;
import com.mywebapp.redis.JedisDao;
import com.mywebapp.service.UserService;
import com.mywebapp.util.CommonUtil;
import com.mywebapp.util.ParamUtil;
import com.mywebapp.util.SpiderUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;


/**
 * Created by gaorui on 17/1/11.
 */
@RestController
public class UserController {
    @Autowired
    UserService userService;
    public static ProxyIp proxyIp = new ProxyIp();

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
     * @deprecated 首页获取爬虫api
     */
    @ApiOperation(value = "获取Ip", notes = "")
    @RequestMapping(value = "/proxyIpApi/", method = RequestMethod.GET)
    public JSONObject proxyIpApi(HttpServletRequest request) {


        return CommonUtil.constructResponse(1, "获取爬虫api", proxyIp.getList());
    }

    /**
     * author:javaGr_ais
     *
     * @return
     * @deprecated 获取爬虫api
     */
    @ApiOperation(value = "获取爬虫", notes = "")
    @RequestMapping(value = "/proxyIpApi/{user}", method = RequestMethod.GET)
    public JSONObject proxyIpApi(@PathVariable("user") String user, String key) {

        User check = userService.check(user, key);
        if (check != null) {
            int status = userService.getUserStatus(check.getU_name());
            int count = check.getU_count();

            if(status == 0){
                return CommonUtil.constructResponse(-10, "您的帐号被冻结,如何疑问请联系cquptgr@gmail.com", null);
            }
            else if (count <= 0) {
                return CommonUtil.constructResponse(-1, "您的免费Api次数已使用完", null);
            }
            userService.userCount(user);
            return CommonUtil.constructResponse(1, "Api success", JedisDao.getProxyIp(10));
        } else
            return CommonUtil.constructResponse(0, "Permission denied", null);
    }

    /**
     * author:javaGr_ais
     *
     * @return
     * @deprecated 获取爬虫api
     */
    @ApiOperation(value = "获取爬虫", notes = "")
    @RequestMapping(value = "/allProxyIpApi/", method = RequestMethod.GET)
    public JSONArray allProxyIpApi() {

        return JedisDao.getAllProxyIp();
    }

    @ApiOperation(value = "获取爬虫", notes = "")
    @RequestMapping(value = "/manager/", method = RequestMethod.POST)
    public JSONObject manager(String userName, String passWord, HttpSession session) {

        if (userName.equals("admin") && passWord.equals("admin")) {

            session.setAttribute("admin", "admin");
            return CommonUtil.constructResponse(1, "ok", null);
        }


        return CommonUtil.constructResponse(0, "error", null);
    }

    @ApiOperation(value = "获取爬虫", notes = "")
    @RequestMapping(value = "/managerlogin/", method = RequestMethod.POST)
    public JSONObject managerlogin(HttpSession session) {

        if (session.getAttribute("admin") != null)
            return CommonUtil.constructResponse(1, "ok", null);
        else
            return CommonUtil.constructResponse(0, "error", null);


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


    @ApiOperation(value = "获取users", notes = "")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<Map<String, Object>> users() {


        return userService.user();
    }

    @ApiOperation(value = "获取user", notes = "")
    @RequestMapping(value = "/user_info", method = RequestMethod.GET)
    public JSONObject user_info(HttpSession session) {


        JSONObject user = (JSONObject) session.getAttribute("user");
        if (user != null) {
            User user_info = userService.getUserByLogin(user.getString("login"));
            user.put("name", user_info.getU_nickname());
            user.put("email", user_info.getU_email());
            int status = userService.getUserStatus(user.getString("login"));
            int u_status = Integer.parseInt(user.getString("u_status"));
            int count = userService.getUserCount(user.getString("login"));
            if(status != u_status){
                user.put("u_status",status);
                user.put("u_count",count);
                session.setAttribute("user",user);
            }
            user.put("u_count",count);
            session.setAttribute("user",user);
            return CommonUtil.constructResponse(1, "user_info", user);
        }
        return CommonUtil.constructResponse(0, "error", null);
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
                User user_info = userService.getUserByLogin(user.getString("login"));


                if (user_info != null) {
                    int status = user_info.getU_status();
                    int count = userService.getUserCount(user.getString("login"));
                    if(status == 0){
                        user.put("name", user_info.getU_nickname());
                        user.put("email", user_info.getU_email());
                        user.put("u_status", 0);
                        session.setAttribute("user", user);
                        user.put("u_key", user_info.getU_key());
                        user.put("u_count", count);
                        response.sendRedirect("http://127.0.0.1:8080/api.html");
                        return CommonUtil.constructResponse(1, "ok", null);
                    }
                    user.put("name", user_info.getU_nickname());
                    user.put("email", user_info.getU_email());
                    user.put("u_status", 10);
                    user.put("u_key", user_info.getU_key());
                    user.put("u_count", count);

                    session.setAttribute("user", user);
                    response.sendRedirect("http://127.0.0.1:8080/api.html");
                    return CommonUtil.constructResponse(1, "ok", null);
                } else {
                    String u_key = CommonUtil.GUID();
                    int insertRes = userService.insertUser(user.getString("login"), user.getString("email"), u_key, user.getString("name"), user.getString("created_at"), user.getString("avatar_url"));
                    if (insertRes > 0) {
                        user.put("u_status", 10);
                        user.put("u_key", u_key);
                        user.put("u_count", 10);
                        session.setAttribute("user", user);
                        response.sendRedirect("http://127.0.0.1:8080/api.html");
                    } else {

                        return CommonUtil.constructResponse(0, "登陆失败", null);
                    }
                }
            }

            return null;

        } catch (Exception e) {

            e.printStackTrace();
            return CommonUtil.constructResponse(0, "user详情", null);

        }
    }


    @RequestMapping(value = "/getAllUsers/", method = RequestMethod.POST)
    public JSONObject getAllUsers() {


        List<User> user = userService.getAllUsers();
        return CommonUtil.constructResponse(1, "ok", user);
    }

    @RequestMapping(value = "/addUserCount/", method = RequestMethod.POST)
    public JSONObject addUserCount(@RequestParam("user") String user) {


        int code = userService.addUserCount(user);
        return CommonUtil.constructResponse(code, "ok", null);
    }


    @RequestMapping(value = "/bannedUser/", method = RequestMethod.POST)
    public JSONObject bannedUser(@RequestParam("user") String user,HttpSession session) {


        int code = userService.bannedUser(user);
        return CommonUtil.constructResponse(code, "ok", null);
    }

    @RequestMapping(value = "/restoreUser/", method = RequestMethod.POST)
    public JSONObject restoreUser(@RequestParam("user") String user,HttpSession session) {


        int code = userService.restoreUser(user);
        return CommonUtil.constructResponse(code, "ok", null);
    }

    @RequestMapping(value = "/updateInfo", method = RequestMethod.POST)
    public JSONObject updateInfo(@RequestParam("nickname") String nickname,@RequestParam("email") String email,HttpSession session) {

        JSONObject user = (JSONObject) session.getAttribute("user");
        int res = userService.updateInfo(user.getString("login"),nickname,email);
        if(res > 0)
        return CommonUtil.constructResponse(1, "ok", null);
        else
            return CommonUtil.constructResponse(0, "error", null);

    }

    @RequestMapping(value = "/deleteProxyIp", method = RequestMethod.POST)
    public JSONObject deleteProxyIp(@RequestParam("porxy") String porxy) {


        long res = JedisDao.deleteProxyIp(porxy);
        if(res > 0)
            return CommonUtil.constructResponse(1, "ok", null);
        else
            return CommonUtil.constructResponse(0, "error", null);

    }



    @RequestMapping(value = "/crawler", method = RequestMethod.POST)
    public JSONObject crawler(@RequestParam("format") String format,@RequestParam("url") String url){


        List<String> list = SpiderUtil.Spider(url,format);
        if(list.size() > 0)
            return CommonUtil.constructResponse(1, "ok", list);
        else
            return CommonUtil.constructResponse(0, "error", null);

    }

}