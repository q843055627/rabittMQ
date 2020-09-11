package com.rabbitmqdemo.basicExample.fanout;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmqdemo.basicExample.utils.RabbitmqUtil;
import org.junit.Test;


/**
 扇出交换机（funout exchange）将消息路由给绑定到它身上的所有队列，而不理会绑定的路由键。如果N个队列绑
 定到某个扇型交换机上，当有消息发送给此扇出交换机时，交换机会将消息的拷贝分别发送给这所有的N个队列。扇
 出用来交换机处理消息的广播路由（broadcast routing）。 简单的说，Fanout Exchange 会忽略RoutingKey 的设
 置，直接将 Message 广播到所有绑定的 Queue 中。因此，你也可以将该交换机称之为广播交换机。
 当我们需要将消息一次发给多个队列时，需要使用这种模式。
 工作机制：
 1. n个队列绑定到一个扇出交换机上。
 2. 当消息发送到这个交换机上，这个消息会被分发给与之绑定的所有队列里。
 */
public class Producer {

    @Test
    public void sendFanout() throws Exception{
        Channel channel = RabbitmqUtil.getChannel();

        //创建扇出交换机
        channel.exchangeDeclare("fanout_exchange", BuiltinExchangeType.FANOUT);

        //交换机与队列绑定，无需绑定路由，该模式下，路由会被忽略
        channel.queueBind("red_queue","fanout_exchange","");
        channel.queueBind("green_queue","fanout_exchange","");
        channel.queueBind("yellow_queue","fanout_exchange","");

        String message = "这是扇出(fanout)交换机模式";

        //路由key不能为null
        channel.basicPublish("fanout_exchange","",null,message.getBytes());

        //测试结果 -- 消息经过交换机后，所有的绑定队列都收到了消息
    }
}
