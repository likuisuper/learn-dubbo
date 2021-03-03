package cxylk.dubbo;

import cxylk.dubbo.bean.User;

/**
 * @Classname UserService
 * @Description TODO
 * @Author likui
 * @Date 2021/3/1 21:18
 **/
public interface UserService {
    User getUser(Integer id);
}
