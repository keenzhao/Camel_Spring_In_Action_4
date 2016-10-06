package com.camel.test.demo.example.c2;

import org.apache.camel.CamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by keen.zhao on 2016/10/7.
 */
public class CamelSpringDSL {

    public static void main(String[] args) throws Exception {

        //这个例子运行需要安装和启动JMS ActiveMQ
        ApplicationContext context = new ClassPathXmlApplicationContext("SpringDSL_CamelBean.xml");
        CamelContext camelContext = context.getBean(CamelContext.class);

        camelContext.start();
        Thread.sleep(10000);
        camelContext.stop();


    }
}
