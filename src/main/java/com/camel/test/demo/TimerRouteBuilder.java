package com.camel.test.demo;

import org.apache.camel.builder.RouteBuilder;

/**
 * Apache Camel是一个非常实用的规则引擎库，能够用来处理来自于不同源的事件和信息。
 * 你可以在使用不同的协议比如VM，HTTP，FTP，JMS甚至是文件系统中来传递消息，
 * 并且让你的操作逻辑和传递逻辑保持分离，这能够让你更专注于消息的内容。
 *
 * camel-core.jar包提供了许多你可能用到的实用组件。出于日志记录的目的，使用了
 * slf4j-simple来作为日志记录的实现， * 从而我们可以从控制台上看到输出。
 * 接下来我们只需要构造一个路由类。 * 路由就好比是Camel中怎样将消息从一端传递到另一端的一个指令定义。
 *
 * 下面这个TimerRouteBuilder类每隔一秒向处理器发送一个消息，简单打印出来。
 *
 * Created by keen.zhao on 2016/9/30.
 */
public class TimerRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {

    }
}
