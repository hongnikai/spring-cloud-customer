package com.lc.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/*
* 获取  MQ连接
*
* @Author lc
*
*
* */
public class ConnectionUtil {

    public static Connection getConnection() throws IOException, TimeoutException {

        //定义一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();

        //设置服务地址
        factory.setHost("localhost");

        //设置端口号
        factory.setPort(5672);

        //vhost  相当于设置  添加的数据库

        factory.setVirtualHost("/vhost_root");

        //用户名：
        factory.setUsername("root");

        //密码
        factory.setPassword("123456");

        factory.newConnection();

        return  factory.newConnection();

    }


}
