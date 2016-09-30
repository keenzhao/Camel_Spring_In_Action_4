package com.camel.test.demo;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;


/**
 * 一个从from到to有中间流程process处理的例子
 * 测试时候用txt文件，UTF-8编码
 * <p>
 * Created by keen.zhao on 2016/9/30.
 */
public class FileProcessWithCamel {

    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {

                Processor processor = new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        try {

                            InputStream body = exchange.getIn().getBody(InputStream.class);
                            BufferedReader in = new BufferedReader(new InputStreamReader(body,"UTF-8"));
                            StringBuilder sb = new StringBuilder("");
                            String str;
                            str = in.readLine();
                            while (str != null) {
                                System.out.println(str);
                                sb.append(str);
                                sb.append(" ");
                                str = in.readLine();
                            }
                            exchange.getOut().setHeader(Exchange.FILE_NAME, "converted"+ new Random().nextInt(100)+".txt");
                            //设置输出到文件
                            exchange.getOut().setBody(sb.toString());

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                };

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


