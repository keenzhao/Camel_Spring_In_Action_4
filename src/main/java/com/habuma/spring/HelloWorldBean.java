package com.habuma.spring;

/**
 * 【激发POJO的潜能】
 *
 * Spring竭力避免因自身的API而弄乱你的应用代码。不会强迫你实现Spring规范的接口或者继承Spring规范的类
 * 相反地，在基于Spring构建的应用中，它的类通常没有任何痕迹表明你使用了Spring。
 * 最坏的场景是，一个类或许会使用Spring注解，但它依旧是POJO
 *
 * Created by keen.zhao on 2016/9/20.
 */
public class HelloWorldBean {
    public String sayHello() {
        return "Hello World";
    }

}
/**
 * 可以看到，这是一个简单普通的Java类 - POJO。没有任何一个地方表明它是一个Spring组件。
 *Spring的非侵入编程模型意味着这个类在Spring应用和非Spring应用中都可以发挥同样的作用。
 * Spring赋予POJO的魔力方式之一就是通过DI来装配它们。
 *
 */
