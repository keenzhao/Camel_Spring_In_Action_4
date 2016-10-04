package com.camel.test.demo.example.c2;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

import javax.jms.ConnectionFactory;

/**
 * 从FTP服务器消费订单并发送它们到JMS队列incomingOrders
 *
 * Created by keen.zhao on 2016/10/4.
 */
public class FtpToJMSExample {

    public static void main(String[] args) throws Exception {

        CamelContext context=new DefaultCamelContext();

        //创建到ActiveMQ实例连接
        ConnectionFactory connectionFactory=new ActiveMQConnectionFactory("vm://localhost");
        //在当前上下文中加入JMS组件
        context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

        //加入新的路由
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("ftp://rider.com/orders?username=rider&password=secret") //java语句构成的路由
                        .to("jms:queue:incomingOrders");

            }
        });

        context.start();
        Thread.sleep(10000);
        context.stop();

    }

}
