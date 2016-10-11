package com.camel.test.demo.example.c2;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

import javax.jms.ConnectionFactory;

/**
 * 基于内容的路由CBR
 *
 * Created by keen.zhao on 2016/10/11.
 */
public class OrderRouter_CBR {
    public static void main(String[] args) throws Exception {

        CamelContext context = new DefaultCamelContext();

        //创建到ActiveMQ实例连接
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");
        //在当前上下文中加入JMS组件
        context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

        //加入新的路由
        context.addRoutes(new RouteBuilder() {
            public void configure() {

                from("file:src/data?noop=true").to("jms:incomingOrders");

                //基于内容的路由
                from("jms:incomingOrders")
                        .choice()
                        .when(header("CamelFileName")
                                .endsWith(".xml"))
                        .to("jms:xmlOrders")
                        .when(header("CamelFileName")
                                .endsWith(".csv"))
                        .to("jms:csvOrders");

                //测试路由打印消息内容
                from("jms:xmlOrders").process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        System.out.println("Received XML order: "
                                + exchange.getIn().getHeader("CamelFileName"));
                    }
                });

                //测试路由打印消息内容
                from("jms:csvOrders").process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        System.out.println("Received CSV order: "
                                + exchange.getIn().getHeader("CamelFileName"));
                    }
                });
            }
        });

        context.start();
        Thread.sleep(10000);
        context.stop();
    }
}
