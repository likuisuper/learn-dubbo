package com.cxylk.controller;

import cxylk.dubbo.User;
import cxylk.dubbo.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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
//        long start = System.currentTimeMillis();
//        userService.getUser(id);
        //调用方法后，会将结果填充到future(setFuture)，所以要立即获取。不能再调用一个方法然后拿回执，是拿不到最开始调用方法的回执的
        //是根据requestId来获取defaultFuture的
//        Future<User> future1 = RpcContext.getContext().getFuture();
//        userService.getUser(id);
//        Future<User> future2 = RpcContext.getContext().getFuture();
//        userService.getUser(id);
//        Future<User> future3 = RpcContext.getContext().getFuture();
//        User user=null;
//        try {
//            user=future1.get();
//            System.out.println("future1:"+user);
//            user=future2.get();
//            System.out.println("future2:"+user);
//            user=future3.get();
//            System.out.println("future3:"+user);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        long end = System.currentTimeMillis();
//        System.out.println("耗时："+(end-start));
        return userService.getUser(id);
    }

    @RequestMapping("/find")
    @ResponseBody
    public List<User> find(String label, Integer age ){
        return userService.findUsersByLabel(label,age);
    }
}
