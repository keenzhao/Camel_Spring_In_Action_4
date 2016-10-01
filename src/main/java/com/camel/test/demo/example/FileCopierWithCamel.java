package com.camel.test.demo.example;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * 使用Camel来处理文件拷贝的例子
 * 当你使用Camel时候，大多数这类代码都是公式化的。每一个Camel应用使用CamelContext 中的方法进行启动或者停止。
 * 你也可以添加一个sleep方法为你copy文件准备时间。
 *
 * 例子说明：
 *
 *
 * Created by keen.zhao on 2016/10/1.
 */
public class FileCopierWithCamel {
    public static void main(String[] args) throws Exception {
        // 创建Camel的上下文
        CamelContext context = new DefaultCamelContext();
        // 加入路由规则,这里创建了一个匿名路由
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("file:data/inbox?noop=true&delay=5000").to("file:data/outbox");
            }
        });

        //启动Camel应用
        context.start();
        //让应用运行10秒后停止，为拷贝文件准备时间，注意，这是演示代码，生产环境下不能这么做。
        Thread.sleep(10000);
        //停止Camel应用
        context.stop();
    }
}
