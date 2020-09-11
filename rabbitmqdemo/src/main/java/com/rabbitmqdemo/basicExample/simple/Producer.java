package com.rabbitmqdemo.basicExample.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmqdemo.basicExample.Constant;

/**
 *  简单模式：一个生产者、一个消费者，不需要设置交换机（使用默认的交换机）
 */
/*
*   生产者
*/
public class Producer {

    public static void main(String[] args) throws Exception{

        String QUEUE_NAME = Constant.QUEUE_NAME;

        //1.创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置主机地址:默认时localhost
        connectionFactory.setHost(Constant.HOST);
        //设置端口号
        connectionFactory.setPort(Constant.PORT);
        //设置虚拟主机名称
        connectionFactory.setVirtualHost("/yhhost");
        //设置连接用户名
        connectionFactory.setUsername("yh");
        //设置连接密码
        connectionFactory.setPassword("yh");

        //2.创建连接
        Connection connection = connectionFactory.newConnection();

        //3.创建频道
        Channel channel = connection.createChannel();

        /*** 4. 声明（创建）队列 *
         * 参数1：队列名称 *
         * 参数2：是否定义持久化队列 *
         * 参数3：是否独占本次连接 *
         * 参数4：是否在不使用的时候自动删除队列 *
         * 参数5：队列其它参数 *
         * 更多说明：声明的队列是幂等的，它只在不存在时才被创建。 */
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        // 5. 发送消息
        // 5.1 要发送的信息
        String message = "我是生产者，你是谁!";
        /*** 5.2 发布消息 *
         * 参数1：交换机名称，如果没有指定则使用默认Default Exchage *
         * 参数2：路由key,简单模式可以传递队列名称 *
         * 参数3：消息其它属性 *
         * 参数4：消息内容。消息内容是字节数组，因此您可以在那里编码任何您喜欢的内容。
         * */
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        //控制台显示发送的消息（测试用）
        System.out.println("已发送消息：" + message);
        // 6. 关闭释放资源
        channel.close();
        connection.close();
    }
}
