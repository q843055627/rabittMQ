package com.rabbitmqdemo.basicExample.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmqdemo.basicExample.Constant;


public class RabbitmqUtil {

    private static Connection connection = null;

    private static Channel channel = null;

    static{
        try{
            //初始化连接
            init();

            // 虚拟机退出时关闭
//            Runtime.getRuntime().addShutdownHook(new Thread(){
//                @Override
//                public void run() {
//                    System.out.println("--------释放关闭资源中....");
//                    //关闭释放资源 destory();
//                    System.out.println("--------释放关闭资源成功....");
//                }
//            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void init() throws Exception{
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();

        connectionFactory.setHost(Constant.HOST);
        connectionFactory.setPort(Constant.PORT);
        connectionFactory.setVirtualHost("/yhhost");
        connectionFactory.setUsername("yh");
        connectionFactory.setPassword("yh");

        //连接
        connection = connectionFactory.newConnection();
        //创建频道
        channel = connection.createChannel();
    }

    //关闭资源
    public static void destory(){
        //关闭频道
        if(null != channel && channel.isOpen()){
            try{
                channel.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        //关闭连接
        if(null != connection && connection.isOpen()){
            try{
                connection.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    //获取连接
    public static Connection getConnection(){
        return connection;
    }

    //获取频道
    public static Channel getChannel(){
        return channel;
    }
}
