package cxylk.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @Classname ServerApplication
 * @Description spring xml方式实现服务端
 * @Author likui
 * @Date 2021/3/4 19:34
 **/
public class ServerApplication {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("spring-provider.xml");
        context.start();
        System.in.read();
    }

}
