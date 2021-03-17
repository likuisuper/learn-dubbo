package com.cxylk;


import cxylk.dubbo.User;
import cxylk.dubbo.UserService;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Method;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname UserServiceImpl
 * @Description 服务端配置了超时时间，当发生超时时，异常却是出现在客户端。如果在@Method注解中配置了timeout，那么最终是以该timeout为准
 * @Author likui
 * @Date 2021/3/1 21:18
 **/
//@DubboService(timeout = 1000,methods = {@Method(name = "getUser",timeout = 1500)})
@DubboService(group = "${server.member.group}",protocol = {"rmi","dubbo","http"},
        methods = {@Method(name = "getUser",timeout = 3000)})
public class UserServiceImpl implements UserService {
//    private Integer port;

    @Value("${server.member.group}")
    private String group;

    @Value("${server.name}")
    private String name;

    @Override
    public User getUser(Integer id) {
        User user = createUser(id);
        user.setDesc("当前服务:" + name);
        if(id==1){
            System.out.println("调用服务...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    @Override
    public List<User> findUsersByLabel(String label, Integer age) {
        List<User> list = new ArrayList<>();
        System.out.println("当前服务"+name);
        list.add(createUser(1));
        list.add(createUser(1));
        list.add(createUser(1));
        return list;
    }

    static User createUser(Integer id) {
        User user = new User();
        user.setId(id);
        user.setName("lk");
        user.setBirthday("1998");
        user.setAge(22);
        return user;
    }
}
