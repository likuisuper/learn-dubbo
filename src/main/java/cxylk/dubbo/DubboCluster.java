package cxylk.dubbo;

import org.apache.dubbo.config.*;
import org.junit.Test;

import java.io.IOException;

/**
 * @Classname DubboCluster
 * @Description TODO
 * @Author likui
 * @Date 2021/3/2 21:04
 **/
public class DubboCluster {
    //开启三个服务(三个进程，不要写在一个方法里)
    @Test
    public void openService1() throws IOException {
        openService(12345);
    }

    @Test
    public void openService2() throws IOException {
        openService(12346);
    }

    @Test
    public void openService3() throws IOException {
        openService(12347);
    }

    public void openService(int port) throws IOException {
        ServiceConfig serviceConfig = new ServiceConfig();

        //1.指定应用
        ApplicationConfig app = new ApplicationConfig("lk-server");
        serviceConfig.setApplication(app);

        //2.指定协议
        ProtocolConfig protocolConfig = new ProtocolConfig("dubbo");
        protocolConfig.setPort(port);
        serviceConfig.setProtocol(protocolConfig);

        //注册中心
        //指定为multicast(组网广播协议，不能写成localhost，使用这个就不用搭建zookeeper了),后面地址要在224.0.0.0 - 239.255.255.255之间
//        RegistryConfig registryConfig=new RegistryConfig("multicast://224.1.1.1:2223");
        RegistryConfig registryConfig = new RegistryConfig("zookeeper://192.168.63.128:2181");
//        registryConfig.setTimeout(10000);如果出现zookeeper无法连接，调大超时时间即可
        serviceConfig.setRegistry(registryConfig);

        //3.指定接口
        serviceConfig.setInterface(UserService.class);

        //4.指定实现
        serviceConfig.setRef(new UserServiceImpl(port));

        //开启服务
        serviceConfig.export();
        System.out.println("服务已开启:" + port);

        //不让主线程结束
        System.in.read();
    }

    //客服端调用
    public static void main(String[] args) throws IOException {
        UserService userService = getClient();
        while (System.in.read() != 'q') {
            //不停发起调用
            System.out.println(userService.getUser(111));
        }
    }

    private static UserService getClient() {
        ReferenceConfig<UserService> referenceConfig = new ReferenceConfig();

        //应用
        ApplicationConfig app = new ApplicationConfig("lk-client");
        referenceConfig.setApplication(app);

        //地址
        referenceConfig.setRegistry(new RegistryConfig("zookeeper://192.168.63.128:2181"));

        //接口
        referenceConfig.setInterface(UserService.class);

        //获取接口服务
        return referenceConfig.get();
    }
}
