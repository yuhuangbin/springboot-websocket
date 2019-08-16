package com.yuhb.websocket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Description:
 * author: yu.hb
 * Date: 2019-08-16
 */
@Controller
public class ViewController {

    @RequestMapping("/login")
    public ModelAndView login(String username, String password){

        return new ModelAndView("chat.html").addObject("userName",username);
    }

}
