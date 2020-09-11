package com.rabbitmqdemo.spring_rabbitMQ.Listener;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//继承MessageListener类后，重写onMessage方法，
//当spring容器接收消息后，会自动交由onMessage进行处理。
@Component
public class EmailListener implements MessageListener {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onMessage(Message message) {
        try {
            //获取email相关内容
            byte[] emailBody = message.getBody();
            //使用jackson转换为json对象
            JsonNode jsonNode = objectMapper.readTree(emailBody);
            //获取email和内容
            String email = jsonNode.get("email").asText();
            String msgContent = jsonNode.get("content").asText();
            System.out.println("获取队列中消息，邮箱号：" + email + ",邮件内容：" + msgContent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
