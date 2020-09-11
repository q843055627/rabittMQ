package com.rabbitmqdemo.basicExample.simple;

import com.rabbitmq.client.*;
import com.rabbitmqdemo.basicExample.Constant;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/*
 *   消费者
 */
public class Consumer {

    public static void main(String[] args) throws Exception {

        String QUEUE_NAME = Constant.QUEUE_NAME;

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(Constant.HOST);
        connectionFactory.setPort(Constant.PORT);
        //消费者和接收者应该对应同一个virtualHost(类似于同一个数据库)
        connectionFactory.setVirtualHost("/yhhost");
        connectionFactory.setUsername("consumer1");
        connectionFactory.setPassword("consumer");

        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,true,false,false,null);

        //创建消费者对象
//
        DefaultConsumer consumer = new DefaultConsumer(channel){
            /**consumerTag 消费者标签，在channel.basicConsume时候可以指定
             *envelope 消息包的内容，可从中获取消息id， *
             *消息routingkey，交换机，消息和重传标志(收到消息失败后是否需 要重新发送)
             *properties 属性信息
             *body 消息
            */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //路由
                System.out.println("路由key为：" +envelope.getRoutingKey());
                //交换机
                System.out.println("交换机为：" + envelope.getExchange());
                //消息id
                System.out.println("消息id为：" +envelope.getDeliveryTag());
                //收到的消息
                String message = new String(body,StandardCharsets.UTF_8);
                System.out.println("接收到的消息：" +message);
            }
        };
        //消费队列中的消息，会自动开启监听。
        // 参数1：队列名称
        // 参数2：是否自动确认，设置为true表示消息接收到自动向mq回复接收到了，mq接收到回复会删除消息，设置为false则需要手动确认 *
        // 参数3：消息接收到后回调 */
        channel.basicConsume(QUEUE_NAME,true,consumer);
        //不关闭资源，应该一直监听消息
    }
}
