package cxylk.dubbo;


import java.util.List;

/**
 * @Classname UserServiceImpl
 * @Description TODO
 * @Author likui
 * @Date 2021/3/1 21:18
 **/
public class UserServiceImpl implements UserService {
    private Integer port;


    public User getUser(Integer id) {
        User user = createUser(id);
        user.setDesc("服务端口:" + port);
        return user;
    }

    public List<User> findUsersByLabel(String label, Integer age) {
        return null;
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
