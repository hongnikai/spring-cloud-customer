package com.lc.publish_subcribe;

import com.lc.util.ConnectionUtil;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.sql.Connection;
import java.util.concurrent.TimeoutException;
//ribbitMQ  订阅模式分发
public class Send {

    //定义一个交换机名字
    private static final String EXCHANGE_NAME="test_change_fanout";

    public static void main(String[] args) throws IOException, TimeoutException {

        com.rabbitmq.client.Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");

        //发送消息 给交换机
        String msg="hello ps";

        channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes());

        System.out.println("Send :"+msg);

        channel.close();
        connection.close();




    }


}
