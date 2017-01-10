package com.mywebapp.main;

import com.mywebapp.apicontroller.MyController;
import com.mywebapp.apicontroller.UserController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by gaorui on 17/1/11.
 */


@SpringBootApplication
public class MappRunApplication {

    public static void main(String[] args) throws Exception {

        SpringApplication.run(new Object[]{UserController.class, MyController.class}, args);
    }
}
