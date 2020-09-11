package com.rabbitmqdemo.basicExample.utils;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

//黄色队列消息监听
public class ColorConsumer {

    private static final String QUEUE_NAME = "color_queue";

    //入口方法
   public static void main(String[] args) throws Exception{
       Channel channel = RabbitmqUtil.getChannel();

       DefaultConsumer consumer = new DefaultConsumer(channel){

           @Override
           public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
               String message = new String(body , StandardCharsets.UTF_8);
               System.out.println("当前队列:" + QUEUE_NAME + ",接收消息:" + message);
           }
       };
       channel.basicConsume(QUEUE_NAME , true , QUEUE_NAME , consumer);
   }
}
