package com.mywebapp.apicontroller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by gaorui on 17/1/11.
 */
@RestController
@EnableAutoConfiguration
public class MyController {

    @RequestMapping(value = "/my/{username}", method = RequestMethod.GET)
    public String getUser(@PathVariable String username) {


        return "my," + username;
    }


}