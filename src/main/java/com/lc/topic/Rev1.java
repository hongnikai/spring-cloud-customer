package com.lc.topic;

import com.lc.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@SuppressWarnings("all")
public class Rev1 {

    private static final String EXCHANGE_NAME="test_exchange_topic";
    private static final String queue_NAME="test_queue_topic_1";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection =  ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(queue_NAME,false,false,false,null);

        channel.queueBind(queue_NAME,EXCHANGE_NAME,"goods.add");

        //同一时刻服务器只会发一条消息给消费者(能者多劳模式)，空闲多的消费者,消费更多的消息
        channel.basicQos(1);
        //定义一个消费者
        Consumer consumer = new DefaultConsumer(channel){

            //消息出发方法
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg=new String(body,"utf-8");
                System.out.println("[1] Recv msg:"+msg);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("[1] done");
                    //手动回执一个消息
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };

        boolean autoAck=false;//自动应答  改成false
        channel.basicConsume(queue_NAME,autoAck,consumer);

    }







}
