package com.lc.routing;

import com.lc.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class receive2 {

    private static final String EXCHANGE_NAME = "test_exchange_direct";
    private static final String queue_NAME = "test_queue_direct_2";

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtil.getConnection();

        final Channel channel = connection.createChannel();

        // 创建匿名Queue
        //String queueName = channel.queueDeclare().getQueue();

        channel.queueDeclare(queue_NAME, false, false, false, null);


        channel.queueBind(queue_NAME, EXCHANGE_NAME, "er" +
                "ror");
        channel.queueBind(queue_NAME, EXCHANGE_NAME, "info");
        channel.queueBind(queue_NAME, EXCHANGE_NAME, "warning");
        channel.queueBind(queue_NAME, EXCHANGE_NAME, "xxx");

        channel.basicQos(1);

        //定义一个消费者
        Consumer consumer=new DefaultConsumer(channel){

            //消息出发方法
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg=new String(body,"utf-8");
                System.out.println("[2] Recv msg:"+msg);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("[2] done");
                    //手动回执一个消息
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };

        boolean autoAck=false;//自动应答 false
        channel.basicConsume(queue_NAME,autoAck , consumer);
    }
}




