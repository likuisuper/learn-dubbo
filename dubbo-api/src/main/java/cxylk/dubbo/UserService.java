package cxylk.dubbo;

import java.util.List;

/**
 * @Classname UserService
 * @Description TODO
 * @Author likui
 * @Date 2021/3/1 21:18
 **/
public interface UserService {
    User getUser(Integer id);

    List<User> findUsersByLabel(String label, Integer age);
}
