package com.rabbitmqdemo.spring_rabbitMQ;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MessageConsumer {

   public static void main(String[] args){
       //读取配置文件，使得监听生效
       ClassPathXmlApplicationContext content =
               new ClassPathXmlApplicationContext("rabbitmq-consumer.xml");
       try{
           //System.in.read();让程序不停止
           //这样监听器可以一直监听消息队列
           System.in.read();
       }catch (Exception e){
           e.printStackTrace();
       }
   }
}
