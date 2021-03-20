package com.cxylk;

import cxylk.dubbo.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Method;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname DubboConfig
 * @Description dubbo配置类，方便管理
 * @Author likui
 * @Date 2021/3/5 20:45
 **/
@Configuration
public class DubboConfig {

    /**
     * dubboService已经把这个bean注入了，但是这个bean的id和下面的bean的id是一样的，所以不会出现不是单例bean的错误。async设置异步调用
     */
    @DubboReference(group = "${server.member.group}",timeout = 5000,methods = {@Method(name = "getUser",timeout = 5000)})
    private UserService userService;

    //bean的id就是方法名，不能写成其他
    @Bean
    public UserService userService(){
        return userService;
    }
}
