package com.wechat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
public class IndexController {
    @RequestMapping(value = "/")
    public void toLogin(HttpServletRequest request, HttpServletResponse response)throws Exception{

        request.getRequestDispatcher("/login.jsp").forward(request,response);
        return;

    }
}
