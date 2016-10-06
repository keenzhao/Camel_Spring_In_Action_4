package com.camel.test.demo.example.c2;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * 模拟处理器例子
 *
 * Created by keen.zhao on 2016/10/7.
 */
public class DownloadLogger implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("======》 你刚刚下载了文件: "
                + exchange.getIn().getHeader("CamelFileName"));
    }
}
