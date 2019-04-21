package com.lc.work;

import com.lc.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    private static final String QUEUE_NAME="test_work_queue";



    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //获取链接
        Connection connection = ConnectionUtil.getConnection();

        //获取channel
        Channel channel =connection.createChannel();

        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        for (int i=0;i<50;i++){

        String msg="hello "+i;
        System.out.println("[WQ send:]"+msg);
        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());

        Thread.sleep(i*20);
        }
        channel.close();
        connection.close();
    }

}
