package com.cxylk.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

/**
 * @Classname HttpUtils
 * @Description 通用Http发送方法
 * @Author likui
 * @Date 2021/3/14 12:51
 **/
public class HttpUtils {
    private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * 向指定URL发送GET方法的请求
     * @param url 需要发送请求的url
     * @param param 请求参数，应该是 name1=value1&name2=value2 的形式
     * @return 所代表远程资源的响应结果
     */
    public static String sendGet(String url,String param){
        return sendGet(url,param,"UTF-8");
    }

    /**
     * 向指定URL发送GET方法的请求
     * @param url 需要发送请求的url
     * @param param 请求参数，应该是 name1=value1&name2=value2 的形式
     * @param contentType 编码类型
     * @return 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param, String contentType) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            logger.info("senderGet-{}", urlNameString);
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), contentType));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            logger.info("recv - {}", result);
        } catch (ConnectException e) {
            logger.error("调用HttpUtils.sendGet ConnectException, url=" + url + ",param=" + param, e);
        } catch (SocketTimeoutException e) {
            logger.error("调用HttpUtils.sendGet SocketTimeoutException, url=" + url + ",param=" + param, e);
        } catch (IOException e) {
            logger.error("调用HttpUtils.sendGet IOException, url=" + url + ",param=" + param, e);
        } catch (Exception e) {
            logger.error("调用HttpsUtil.sendGet Exception, url=" + url + ",param=" + param, e);
        }finally {
            try {
                if(in!=null){
                    in.close();
                }
            }catch (Exception ex){
                logger.error("调用in.close Exception, url=" + url + ",param=" + param, ex);
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 31; i++) {
            System.out.println(sendGet("http://127.0.0.1:8080/getUser", "id=2"));
        }
    }
}
