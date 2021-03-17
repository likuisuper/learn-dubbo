package com.cxylk;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Classname CompletableFutureTest
 * @Description 测试Future异步计算结果
 * @Author likui
 * @Date 2021/3/14 14:50
 **/
public class CompletableFutureTest {

    @Test
    public void test() throws ExecutionException, InterruptedException, TimeoutException {
        CompletableFuture<String> future=new CompletableFuture<>();
//        try {
//            future.get(500, TimeUnit.MILLISECONDS);//阻塞500ms，
//        } catch (InterruptedException | ExecutionException | TimeoutException e) {
//            e.printStackTrace();
//        }
        new Thread(()->{
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            future.complete("hell0");
        }).start();
//        String result = future.get(1000,TimeUnit.MILLISECONDS);在1000ms期间并没有获取结果，也会报超时异常
        String result = future.get();//不设置超时时间就会一直阻塞，直到填充结果
        System.out.println("获取结果："+result);
    }
}
