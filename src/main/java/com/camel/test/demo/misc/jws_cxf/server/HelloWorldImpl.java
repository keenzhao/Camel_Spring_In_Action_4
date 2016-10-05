package com.camel.test.demo.misc.jws_cxf.server;

import com.camel.test.demo.misc.jws_cxf.model.User;

import javax.jws.WebService;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by keen.zhao on 2016/10/5.
 */
@WebService(endpointInterface = "com.camel.test.demo.misc.jws_cxf.server.HelloWorld",
        serviceName = "HelloWorld")
public class HelloWorldImpl implements HelloWorld {
    Map<Integer, User> users = new LinkedHashMap<Integer, User>();


    @Override
    public String sayHi(String text) {
        System.out.println("sayHi called");
        return "Hello " + text;
    }

    @Override
    public String sayHiToUser(User user) {
        System.out.println("sayHiToUser called");
        users.put(users.size() + 1, user);
        return "Hello " + user.getName();
    }

    @Override
    public Map<Integer, User> getUsers() {
        System.out.println("getUsers called");
        return users;
    }
}
