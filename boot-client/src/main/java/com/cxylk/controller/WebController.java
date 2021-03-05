package com.cxylk.controller;

import cxylk.dubbo.User;
import cxylk.dubbo.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Classname WebController
 * @Description TODO
 * @Author likui
 * @Date 2021/3/4 23:08
 **/
@Controller
public class WebController {
    @Autowired
    private UserService userService;

    @RequestMapping("/getUser")
    @ResponseBody
    public User getUser(Integer id){
        return userService.getUser(id);
    }
}
