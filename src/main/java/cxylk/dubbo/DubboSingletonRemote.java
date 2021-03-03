package cxylk.dubbo;

import org.apache.dubbo.config.*;
import org.junit.Test;

import java.io.IOException;

/**
 * @Classname DubboSingletonRemote
 * @Description TODO
 * @Author likui
 * @Date 2021/3/1 21:23
 **/
public class DubboSingletonRemote {
    //客服端
    @Test
    public void invokeRemote() {
        //引用服务
        ReferenceConfig<UserService> referenceConfig = new ReferenceConfig();

        //1.应用
        ApplicationConfig app = new ApplicationConfig("lk-client");
        referenceConfig.setApplication(app);

        //2.URL地址
        referenceConfig.setUrl("dubbo://127.0.0.1:8080/cxylk.dubbo.UserService");

        //3.指定接口
        referenceConfig.setInterface(UserService.class);

        //4.获取接口服务(动态代理)
        UserService userService = referenceConfig.get();

        //先启动服务端，再启动客服端
        System.out.println(userService.getUser(111));
    }

    //服务端
    @Test
    public void openService() throws IOException {
        //服务设置
        ServiceConfig serviceConfig = new ServiceConfig();

        //1.应用
        //指定服务名称，否则会报错
        ApplicationConfig app = new ApplicationConfig("lk-server");
        serviceConfig.setApplication(app);

        //2.协议
        ProtocolConfig protocolConfig = new ProtocolConfig("dubbo");//dubbo是二进制、全双工的应用协议
        //指定端口
        protocolConfig.setPort(8080);
        serviceConfig.setProtocol(protocolConfig);
        //注册中心(集群时才会用到)这里不是集群，所以设置为空，不然会报错
        RegistryConfig registryConfig = new RegistryConfig(RegistryConfig.NO_AVAILABLE);
        serviceConfig.setRegistry(registryConfig);

        //3.接口
        serviceConfig.setInterface(UserService.class);

        //4.实现
        serviceConfig.setRef(new UserServiceImpl(8080));

        //开启服务
        serviceConfig.export();

        //不让主线程关闭，否则客服端获取不到
        System.in.read();
    }
}
