package com.lc.routing;

import com.lc.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class send {

    private static final String EXCHANGE_NAME="test_exchange_direct";


    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection =  ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        //声明交换机 exchange
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");

        String msg="hello direct!";

        String routingKey="error";
        channel.basicPublish(EXCHANGE_NAME,routingKey,null,msg.getBytes());

        System.out.println("Send "+msg);

        channel.close();
        connection.close();

    }

}
