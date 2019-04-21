package com.lc.simple;

import com.lc.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.connection.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    //测试普通队列
    private static final String QUEUE_NAME="test_simple_queue";


    public static void main(String[] args) throws IOException,TimeoutException {

        //获取一个链接
        com.rabbitmq.client.Connection connection = ConnectionUtil.getConnection();

        //从链接中获取一个通道
        Channel channel = connection.createChannel();
        //创建队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        String msg="hello simple !";

        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());

        System.out.println("--send msg:"+msg);
        channel.close();
        connection.close();


    }
}
