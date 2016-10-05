package com.camel.test.demo.example.c2;

/**
 * Created by keen.zhao on 2016/10/6.
 */
public class EnglishGreeter implements Greeter{
    @Override
    public String sayHello() {
        return "Hello " + System.getProperty("user.name");
    }
}
