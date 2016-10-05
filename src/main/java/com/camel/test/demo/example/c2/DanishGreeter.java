package com.camel.test.demo.example.c2;

/**
 * Created by keen.zhao on 2016/10/6.
 */
public class DanishGreeter implements Greeter {
    @Override
    public String sayHello() {
        return "Davs " + System.getProperty("user.name");
    }
}
