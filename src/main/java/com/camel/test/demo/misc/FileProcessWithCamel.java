package com.camel.test.demo.misc;

import org.apache.camel.CamelContext;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;


/**
 * 一个从from到to有中间流程process处理的例子
 * 测试时候用txt文件，UTF-8编码
 *
 * Created by keen.zhao on 2016/9/30.
 */
public class FileProcessWithCamel {

    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                Processor processor =new FileConvertProcessor();
                from("file:d:/temp/inbox?noop=true&delay=10000").process(processor).to("file:d:/temp/outbox");
            }
        });

        context.start();

        boolean loop =true;
        while(loop){
            Thread.sleep(25000);
        }

        context.stop();
    }
}


