package com.mywebapp.apicontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by gaorui on 17/1/11.
 */
@Controller
public class MyController {

    @RequestMapping(value = "/welcome")
    public void welcome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("welcome");
        request.getRequestDispatcher("WEB-INF/page/index.html").forward(request,response);
        //return  null;
    }


}