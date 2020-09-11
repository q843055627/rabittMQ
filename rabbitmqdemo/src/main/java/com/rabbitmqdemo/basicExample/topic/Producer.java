package com.rabbitmqdemo.basicExample.topic;


import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmqdemo.basicExample.utils.RabbitmqUtil;
import org.junit.Test;


/**
 主题交换机（topic exchanges）通过对消息的路由键和队列到交换机的绑定模式之间的匹配，将消息路由给一个或
 多个队列。主题交换机经常用来实现各种分发/订阅模式及其变种。主题交换机通常用来实现消息的多播路由
 （multicast routing）。 Topic Exchange 配置上和 Direct Exchange 类似，也需要通过 RoutingKey 来路由消息，
 区别在于Direct Exchange 对 RoutingKey 是精确匹配，而 Topic Exchange 支持模糊匹配。 关于通配符，分别支持
 * 和 # 通配符， * 表示匹配一个单词， # 则表示匹配没有或者多个单词。 因此first.# 能够匹配到 first.green或
 first.green.fast ，但是 first.* 只会匹配到 first.green，不能匹配first.green.fast 。
 说明：
 1.这种模式较为复杂，简单来说，就是每个队列都有其关心的主题，所有的消息都带有一个“标题”(RouteKey)，
 Exchange会将消息转发到所有关注主题能与RouteKey模糊匹配的队列。
 2.这种模式需要RouteKey，也许要提前绑定Exchange与Queue。
 3.在进行绑定时，要提供一个该队列关心的主题，如“#.log.#”表示该队列关心所有涉及log的消息(一个RouteKey 为”MQ.log.error”的消息会被转发到该队列)。
 */
public class Producer {


    @Test
    public void sendTopic() throws Exception{
        Channel channel = RabbitmqUtil.getChannel();

        channel.exchangeDeclare("topic_exchange" , BuiltinExchangeType.TOPIC);

        /**
         * * 绑定关系:
         * * 交换器           路由key          队列
         * * topic_exchange  *.red.color      red_queue
         * * topic_exchange  *.green.color    green_queue
         * * topic_exchange  *.yellow.color    yellow_queue
         * * topic_exchange  #.color           color_queue
         */
        //这里运行一遍即绑定了，如需多次测试，记得清除原来绑定信息
        channel.queueBind("red_queue","topic_exchange","*.red.color");
        channel.queueBind("green_queue","topic_exchange","*.green.color");
        channel.queueBind("yellow_queue","topic_exchange","*.yellow.color");
        channel.queueBind("color_queue","topic_exchange","*.color");

        String message = "这是通配符(topic)交换机模式";

        channel.basicPublish("topic_exchange","a.color",null,message.getBytes());

    }
}
