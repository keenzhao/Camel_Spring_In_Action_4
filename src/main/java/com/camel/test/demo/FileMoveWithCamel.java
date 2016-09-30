package com.camel.test.demo;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Apache Camel是Apache基金会下的一个开源项目,它是一个基于规则路由和处理的引擎，
 * 提供企业集成模式的Java对象的实现，通过应用程序接口或称为陈述式的Java领域特定语言(DSL)
 * 来配置路由和处理的规则。
 * <p>
 * 其核心的思想就是从一个from源头得到数据,通过processor处理,再发到一个to目的的.
 * 这个from和to可以是我们在项目集成中经常碰到的类型:
 * 一个FTP文件夹中的文件
 * 一个MQ的queue
 * 一个HTTP request/response
 * 一个webservice等等。
 * Camel可以很容易集成到standalone的应用,在容器中运行的Web应用,以及和Spring一起集成。
 * <p>
 * 下面用一个示例,介绍怎么开发一个最简单的Camel应用。
 * 例子体现了一个最简单的路由功能,比如d:/temp/inbox/是某一个系统FTP到Camel所在的系统的一个接收目录。
 * d:/temp/outbox为Camel要发送的另一个系统的接收目录。
 * <p>
 * 运行后完成的工作是将d:/temp/inbox/下的所有文件移到d:/temp/outbox
 * <p>
 * Created by keen.zhao on 2016/9/30.
 */
public class FileMoveWithCamel {

    static Logger log= LoggerFactory.getLogger(FileMoveWithCamel.class);

    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();
        context.addRoutes(
                new RouteBuilder() {
                    @Override
                    public void configure() throws Exception {
                        //delay=30000是每隔30秒轮询一次文件夹中是否有文件
                        from("file:d:/temp/inbox?delay=10000").to("file:d:/temp/outbox");
                    }
                }
        );

        context.start();
        boolean loop = true;

        while (loop) {
            Thread.sleep(25000);
            log.info("测试运行中......");
        }

        context.stop();

    }


}
