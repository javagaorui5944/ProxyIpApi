package com.mywebapp.apicontroller;

import com.mywebapp.model.ProxyIp;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

/**
 * Created by gaorui on 17/1/11.
 */
@RestController
@EnableAutoConfiguration
public class UserController {

    @RequestMapping(value = "/users/{username}", method = RequestMethod.GET)
    public ProxyIp getUser(@PathVariable String username) {
        ProxyIp proxyIp = new ProxyIp();
        proxyIp.setIp(username);
        proxyIp.setPort(8080);

        return proxyIp;
    }


}