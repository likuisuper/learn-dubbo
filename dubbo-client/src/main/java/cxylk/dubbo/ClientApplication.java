package cxylk.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @Classname ClientApplication
 * @Description spring xml方式实现客服端
 * @Author likui
 * @Date 2021/3/4 19:40
 **/
public class ClientApplication {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("spring-consumer.xml");
        UserService userService = context.getBean(UserService.class);
        while (System.in.read() != 'q') {
            System.out.println(userService.getUser(111));
        }
    }
}
