package com.rabbitmqdemo.basicExample.direct;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmqdemo.basicExample.utils.RabbitmqUtil;
import org.junit.Test;

/**
 * 直连型交换机（direct exchange）是根据消息携带的路由键（routing key）将消息投递给对应队列的。设置
 * Exchange 和 Queue 的 Binding 时需指定 RoutingKey（一般为 Queue Name），发消息时也指定一样的
 * RoutingKey，消息就会被路由到对应的Queue。直连交换机用来处理消息的单播路由（unicast routing，尽管它也
 * 可以处理多播路由） （一对一模式）
 * 当我们需要将一个消息只能发送给一个队列的时候，需要使用这种模式
 */

public class Producer {

    @Test
    public void sendDirect() throws Exception{
        Channel channel = RabbitmqUtil.getChannel();
        //声明一个 直接模式的交换机
        channel.exchangeDeclare("direct_exchange", BuiltinExchangeType.DIRECT);

        //通过路由key  绑定队列和交换器
        //队列，交换机，路由key
        channel.queueBind("red_queue","direct_exchange","red");
        channel.queueBind("green_queue","direct_exchange","green");
        channel.queueBind("yellow_queue","direct_exchange","yellow");

        String message = "这是直连(direct)交换机模式";
        //交换机，路由key，消息其它属性，消息
        //消息经过交换机后，会根据绑定队列列表 匹配路由key 将消息发送到满足的队列中
        channel.basicPublish("direct_exchange","yellow",null,message.getBytes());

        //测试结果 -- 消息经过交换机后，根据路由key 将消息发送到对应的队列中
    }
}
