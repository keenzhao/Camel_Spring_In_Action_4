package com.camel.test.demo.example.c2;

import org.apache.camel.builder.RouteBuilder;

/**
 * FtpToJMSRoute路由
 *
 * Created by keen.zhao on 2016/10/6.
 */
public class FtpToJMSRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("ftp://rider.com" +
                "/orders?username=rider&password=secret")
                .to("jms:incomingOrders");
    }
}
