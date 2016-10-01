package com.camel.test.demo.example.c1;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * 使用Camel来处理文件拷贝的例子
 * 当你使用Camel时候，大多数这类代码都是公式化的。每一个Camel应用使用CamelContext中的方法进行启动或者停止。
 * 你也可以添加一个sleep方法为你copy文件准备时间。
 *
 * 例子说明：
 * Camel路由是以边读边流动的方式来定义的。
 * from("file:data/inbox?noop=true&delay=5000").to("file:data/outbox");
 * 上面这个路由是以这样的方式来读的：
 * 消费消息来自位置为data/inbox（设置noop选项）文件夹下面的文件，接着把文件路由到位置data/outbox中。
 * noop选项告诉Camel离开（保留）资源文件。若有你不使用noop选项，这个文件会被删除掉。
 * delay选项表示隔多少毫秒轮询一次。从没有见过Camel的人们能够理解camel的路由做了些什么。
 *
 * 你可能也注意到了，除了Camel的公式化的代码，你仅仅使用一行java代码就创建了一个文件轮询路由。
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
