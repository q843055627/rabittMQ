package com.rabbitmqdemo.spring_rabbitMQ;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)      //@RunWith(SpringJUnit4ClassRunner.class),让测试运行于Spring测试环境
@ContextConfiguration("classpath:rabbitmq.xml")          //通常与@RunWith联合使用用来测试
public class MessageProducer {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void sendMsg() throws Exception{
        Map<String , String> map = new HashMap<>();
        map.put("phone","18895374352");
        map.put("email","843055627@qq.com");
        map.put("content","恭喜，账号注册成功");

        //map即为消息
        rabbitTemplate.convertAndSend("sms.user.msg",map);
        rabbitTemplate.convertAndSend("email.user.msg",map);
    }
}
