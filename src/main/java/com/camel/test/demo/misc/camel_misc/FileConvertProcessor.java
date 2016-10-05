package com.camel.test.demo.misc.camel_misc;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * Created by keen.zhao on 2016/9/30.
 */
public class FileConvertProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        try {

            InputStream body = exchange.getIn().getBody(InputStream.class);
            BufferedReader in = new BufferedReader(new InputStreamReader(body, "UTF-8"));
            StringBuilder sb = new StringBuilder("");
            String str;
            str = in.readLine();
            while (str != null) {
                System.out.println(str);
                sb.append(str);
                sb.append(" ");
                str = in.readLine();
            }

            System.out.println("=======>"+exchange.getProperties().keySet().toString());
            System.out.println("=======>"+exchange.getIn().getHeaders().keySet().toString());
            System.out.println("=======>"+exchange.getIn().getHeader(Exchange.FILE_NAME_ONLY).toString());

            //文件名保留为原文件名，关于文件的信息，存在与Headers中
            exchange.getOut().setHeader(Exchange.FILE_NAME, exchange.getIn().getHeader(Exchange.FILE_NAME_ONLY).toString());
            //设置输出到文件
            exchange.getOut().setBody(sb.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
