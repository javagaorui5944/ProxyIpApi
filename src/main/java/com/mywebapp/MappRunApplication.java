package com.mywebapp;


import com.mywebapp.timer.MyTimer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by gaorui on 17/1/11.
 */

@SpringBootApplication
public class MappRunApplication{



    public static void main(String[] args) throws Exception {

        SpringApplication.run(MappRunApplication.class, args);
        new MyTimer().Start();
    }
}
