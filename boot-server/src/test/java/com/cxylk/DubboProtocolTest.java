package com.cxylk;

import cxylk.dubbo.UserService;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.rpc.Exporter;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.ProxyFactory;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.apache.dubbo.rpc.protocol.dubbo.DubboProtocol;
import org.apache.dubbo.rpc.proxy.jdk.JdkProxyFactory;
import org.junit.Test;

import java.io.IOException;

/**
 * @Classname DubboProtocolTest
 * @Description 基于dubbo协议来暴露服务
 * @Author likui
 * @Date 2021/3/26 21:06
 **/
public class DubboProtocolTest {
    String urlText="dubbo://127.0.0.1:20880/cxylk.dubbo.UserService";
    // 暴露服务测试
    @Test
    public void exportTest() throws IOException, IOException {
        DubboProtocol protocol=new DubboProtocol();
        ProxyFactory proxyFactory=new JdkProxyFactory();// 反射调用
        UserServiceImpl refImpl = new UserServiceImpl();// 实现类
        Invoker<UserService> invoker =
                proxyFactory.getInvoker(refImpl, UserService.class, URL.valueOf(urlText));
        Exporter<UserService> export = protocol.export(invoker);//
        ApplicationModel.getServiceRepository().registerService(UserService.class);

        System.out.println("服务暴露成功");
        System.in.read();
    }

    /**
     * 服务引用
     */
    @Test
    public void referTest(){
        DubboProtocol protocol=new DubboProtocol();
        ProxyFactory proxyFactory=new JdkProxyFactory();// 反射调用
        Invoker<UserService> invoker = protocol.refer(UserService.class, URL.valueOf(urlText));
        UserService proxy = proxyFactory.getProxy(invoker);
        System.out.println(proxy.getUser(111));
    }


    /**
     * 服务调用
     */
    @Test
    public void invokeTest() {
        ReferenceConfig<UserService> referenceConfig=new ReferenceConfig();
        //  应用名称
        ApplicationConfig app=new ApplicationConfig("lk-client");
        referenceConfig.setApplication(app);
        // URL地址
        referenceConfig.setUrl(urlText);
        // 指定接口
        referenceConfig.setInterface(UserService.class);
        // 获取接口服务 （动态代理）
        UserService userService = referenceConfig.get();
        System.out.println(userService.getUser(1111));
    }
}
