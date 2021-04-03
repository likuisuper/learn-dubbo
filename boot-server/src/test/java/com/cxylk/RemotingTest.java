package com.cxylk;


import org.apache.dubbo.common.URL;
import org.apache.dubbo.remoting.Channel;
import org.apache.dubbo.remoting.RemotingException;
import org.apache.dubbo.remoting.transport.ChannelHandlerAdapter;
import org.apache.dubbo.remoting.transport.netty4.NettyClient;
import org.apache.dubbo.remoting.transport.netty4.NettyServer;
import org.junit.Test;

import java.io.IOException;

/**
 * @Classname RemotingTest
 * @Description 使用netty4完成最基本的通信
 * @Author likui
 * @Date 2021/4/3 12:56
 **/
public class RemotingTest {
    String url="dubbo://127.0.0.1:20880/cxylk.dubbo.UserService?timeout=3000";

    @Test
    public void openServer() throws IOException, RemotingException {
        NettyServer server=new NettyServer(URL.valueOf(url),new ChannelHandlerAdapter(){
            @Override
            public void received(Channel channel, Object message) throws RemotingException {
                System.out.println("接收到数据："+message);
                //通过channel发送返回结果
                channel.send("收到,over");
            }
        });
        System.in.read();
    }

    @Test
    public void invoke() throws RemotingException, IOException {
        NettyClient client=new NettyClient(URL.valueOf(url),new ChannelHandlerAdapter(){
            @Override
            public void received(Channel channel, Object message) throws RemotingException {
                System.out.println("返回结果:"+message);
            }
        });
        //异步
        client.send("hello netty4");
        //不让线程结束
        System.in.read();
    }
}
