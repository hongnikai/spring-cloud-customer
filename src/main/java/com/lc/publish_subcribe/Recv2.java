package com.lc.publish_subcribe;

import com.lc.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv2 {

    private static final String QUEUE_NAME="test_queue_fanout_sms";
    private static final String EXCHANGE_NAME="test_change_fanout";
    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //绑定队列道交换机转发器
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"");

        //一次只分发一个
        channel.basicQos(1);

        //定义一个消费者
        Consumer consumer = new DefaultConsumer(channel){

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

        boolean autoAck=false;//自动应答  改成false
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);



    }





}
