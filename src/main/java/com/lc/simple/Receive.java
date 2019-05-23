package com.lc.simple;

import com.lc.util.ConnectionUtil;
import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/*
*
* 消费者  获取消息
*
* */
//@SuppressWarnings("all")
public class Receive {

    //测试普通队列
    private static final String QUEUE_NAME="test_simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        //获取链接
        Connection connection = ConnectionUtil.getConnection();

        //创建频道
        Channel channel = connection.createChannel();

        //定义队列的消费者
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        //监听队列
//        channel.basicConsume(QUEUE_NAME,true,queueingConsumer);
//
//        while(true){
//
//            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
//
//            String msgString =new String(delivery.getBody());
//
//            System.out.println("{recv} msg:"+msgString);
//
//        }
        //队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                //当队列中存在消息时，默认出发这个方法
                String msg = new String(body,"utf-8");

                System.out.println("new api recv:"+msg);

            }
        };
        //监听队列
        channel.basicConsume(QUEUE_NAME,true,queueingConsumer);

    }


    @Test
    public void oldpiu() throws IOException, TimeoutException, InterruptedException {

        //获取链接
        Connection connection = ConnectionUtil.getConnection();

        //创建频道
        Channel channel = connection.createChannel();

        //定义队列的消费者
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        //监听队列
        channel.basicConsume(QUEUE_NAME,true,queueingConsumer);

        while(true){

            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();

            String msgString =new String(delivery.getBody());

            System.out.println("{recv} msg:"+msgString);

        }



    }



}
