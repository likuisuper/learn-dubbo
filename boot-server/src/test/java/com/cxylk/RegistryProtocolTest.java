package com.cxylk;

import cxylk.dubbo.UserService;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.registry.integration.RegistryProtocol;
import org.apache.dubbo.registry.zookeeper.ZookeeperRegistryFactory;
import org.apache.dubbo.remoting.zookeeper.curator.CuratorZookeeperTransporter;
import org.apache.dubbo.rpc.Exporter;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.ProxyFactory;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.apache.dubbo.rpc.model.ServiceDescriptor;
import org.apache.dubbo.rpc.model.ServiceRepository;
import org.apache.dubbo.rpc.protocol.dubbo.DubboProtocol;
import org.apache.dubbo.rpc.proxy.javassist.JavassistProxyFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @Classname RegistryProtocolTest
 * @Description 基于代理协议进行服务暴露与引用
 * @Author likui
 * @Date 2021/3/27 19:33
 **/
public class RegistryProtocolTest {
    final String urlTest="zookeeper://192.168.63.128:2181/org.apache.dubbo.registry.RegistryService?\n" +
            "application=boot-server&dubbo=2.0.2&\n" +
            "export=dubbo%3A%2F%2F127.0.0.1%3A20880%2Fcxylk.dubbo.UserService&timeout=6000";
    final String refUrlText="registry://192.168.63.128:2181/org.apache.dubbo.registry.RegistryService?application=boot-client&dubbo=2.0.2&pid=6420&qos.enable=false&refer=application%3Dboot-client%26check%3Dfalse%26dubbo%3D2.0.2%26init%3Dfalse%26interface%3Dcxylk.dubbo.UserService%26loadbalance%3Droundrobin%26metadata-type%3Dremote%26methods%3DfindUsersByLabel%2CgetUser%26pid%3D6420%26qos.enable%3Dfalse%26register.ip%3D127.0.0.1%26release%3D2.7.8%26side%3Dconsumer%26sticky%3Dfalse%26timestamp%3D1616744859108&registry=zookeeper&release=2.7.8&timestamp=1616744859120";
    @Before
    public void init() {
        ApplicationModel.getConfigManager().setApplication(new ApplicationConfig("test"));
    }

    /**
     * 服务暴露
     * @throws IOException
     */
    @Test
    public void exportTest() throws IOException {
        //环境准备
        UserServiceImpl impl = new UserServiceImpl();
        impl.name = "注册中心测试";
        ServiceRepository repository = ApplicationModel.getServiceRepository();
        ServiceDescriptor serviceRepository = repository.registerService(UserService.class);
        repository.registerProvider(
                "cxylk.dubbo.UserService", impl, serviceRepository,
                new ServiceConfig<>(), null
        );
        //初始化代理协议
        //一个是目标协议，一个是注册工厂
        RegistryProtocol protocol = new RegistryProtocol();
        //代理的目标协议，这里是dubbo协议
        protocol.setProtocol(new DubboProtocol());
        ZookeeperRegistryFactory registryFactory = new ZookeeperRegistryFactory();
        registryFactory.setZookeeperTransporter(new CuratorZookeeperTransporter());
        protocol.setRegistryFactory(registryFactory);

        //构建Invoker 使用ProxyFactory的方式
        ProxyFactory proxyFactory=new JavassistProxyFactory();//动态生成代理类
        Invoker<UserService> invoker=proxyFactory.getInvoker(impl,UserService.class, URL.valueOf(urlTest));

        //dubbo协议 netty-server远程服务
        Exporter<UserService> export = protocol.export(invoker);
        //暴露服务 注册提供者
        System.in.read();
    }

    /**
     * 服务引用
     */
    @Test
    public void referTest(){
        //初始化代理协议
        RegistryProtocol protocol=new RegistryProtocol();
        protocol.setProtocol(new DubboProtocol());
        ZookeeperRegistryFactory registryFactory=new ZookeeperRegistryFactory();
        registryFactory.setZookeeperTransporter(new CuratorZookeeperTransporter());
        protocol.setRegistryFactory(registryFactory);

        //服务引用
        Invoker<UserService> invoker = protocol.refer(UserService.class, URL.valueOf(refUrlText));
        ProxyFactory proxyFactory=new JavassistProxyFactory();
        UserService userService = proxyFactory.getProxy(invoker);
        System.out.println(userService.getUser(111));
    }

    /**
     * javassistProxyFactory测试
     * @throws IOException
     */
    @Test
    public void proxyFactoryTest() throws IOException {
        ProxyFactory proxyFactory=new JavassistProxyFactory();//动态生成代理类
        String urlTest="dubbo://127.0.0.1:20880/cxylk.dubbo.UserService";

        Invoker<UserService> invoker=proxyFactory.getInvoker(new UserServiceImpl(),UserService.class,URL.valueOf(urlTest));
        System.out.println(invoker);
        System.in.read();
    }
}
