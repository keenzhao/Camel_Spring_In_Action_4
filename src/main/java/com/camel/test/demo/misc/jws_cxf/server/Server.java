package com.camel.test.demo.misc.jws_cxf.server;

import javax.xml.ws.Endpoint;

/**
 * 服务端
 * 运行后访问：http://localhost:9000/CxfTestDemo01?wsdl
 * 可以看到wsdl内容
 * <p>
 * Created by keen.zhao on 2016/10/5.
 */
public class Server {

    protected Server() throws Exception {
        // START SNIPPET: publish
        System.out.println("Starting Server");
        HelloWorldImpl implementor = new HelloWorldImpl();
        String address = "http://localhost:9000/helloWorld";
        Endpoint.publish(address, implementor);
        // END SNIPPET: publish
    }

    public static void main(String[] args) throws Exception {
        new Server();

        System.out.println("Server ready...");

        Thread.sleep(5 * 60 * 1000);
        System.out.println("Server exiting");
        System.exit(0);
    }
}
