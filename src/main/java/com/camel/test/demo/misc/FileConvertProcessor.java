package com.camel.test.demo.misc;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

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
            exchange.getOut().setHeader(Exchange.FILE_NAME, "converted" + new Random().nextInt(100) + ".txt");
            //设置输出到文件
            exchange.getOut().setBody(sb.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
