package com.cxylk;


import cxylk.dubbo.User;
import cxylk.dubbo.UserService;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Method;

/**
 * @Classname UserServiceImpl
 * @Description 服务端配置了超时时间，当发生超时时，异常却是出现在客户端。如果在@Method注解中配置了timeout，那么最终是以该timeout为准
 * @Author likui
 * @Date 2021/3/1 21:18
 **/
@DubboService(timeout = 1000,methods = {@Method(name = "getUser",timeout = 1500)})
public class UserServiceImpl implements UserService {
    private Integer port;


    public User getUser(Integer id) {
        User user = createUser(id);
        user.setDesc("服务端口:" + port);
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return user;
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
