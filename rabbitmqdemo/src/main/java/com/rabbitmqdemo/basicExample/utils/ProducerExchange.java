package com.rabbitmqdemo.basicExample.utils;

import com.rabbitmq.client.Channel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ProducerExchange {
    Channel channel = null;

    //junit中的@Before方法
    //test前执行
    @Before
    public void init(){
        channel = RabbitmqUtil.getChannel();
    }

    //test后执行，释放连接
    @After
    public void close(){
        RabbitmqUtil.destory();
    }

    @Test
    public void createQueue() throws Exception{
        channel.queueDeclare("red_queue",true,false,false,null);
        channel.queueDeclare("green_queue",true,false,false,null);
        channel.queueDeclare("yellow_queue",true,false,false,null);
        channel.queueDeclare("color_queue",true,false,false,null);
    }
}

