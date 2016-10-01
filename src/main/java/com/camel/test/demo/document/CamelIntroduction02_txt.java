package com.camel.test.demo.document;

/**
 * 粗糙翻译一下，后续调整，感兴趣同学，自行阅读英文原版《Camel in action》
 *
 * Created by keen.zhao on 2016/10/1.
 */
public class CamelIntroduction02_txt {

    /**
     * 1.2 快速开始
     *
     * 1.2.1 获取Camel
     *      在官方Apache Camel上可以看到Camel的下载地址（http://camel.apache.org/download.html）。
     *
     * 1.2.2 你的第一次驾驭Camel
     *          第一个例子我们会用传统的"hello world"：路由文件。假如你需要读一个目录（data/inbox）下面的文件，
     *      然后用一定的方式进行处理，然后把结果写到另外一个文件目录（data/outbox）。为简单起见，你会跳过
     *      处理，所以你的输出将仅仅是原始文件的一个副本。
     *      --------------                     ---------------
     *      | data/inbox |  -----> File -----> | data/outbox |
     *      --------------                     ---------------
     *          它看起来很简单是不是。这是用纯java的一个可能的解决方案（不用Camel）。
     *      详见: com.camel.test.demo.example.FileCopier类
     *          纯java实现是个十分简单的用例，但是用java实现仍旧需要34行代码。你不得不使用低级的文件API，
     *          确保数据流已经关闭，这样的程序很容易出错。如果你想轮询data/inbox目录获取新的文件，你需要
     *          设置一个定时器，并保持跟踪你已经复制的文件。这个简单的例子将变的越来越复杂。
     *
     *          在这之前像这样的集成任务已经做了几千次了，你不应该手工编码这些东西。我们不需要重复造轮子，
     *          让我们来看下使用像Apache Camel这样的集成框架的轮询解决方案。
     *      详见：com.camel.test.demo.example.FileCopierWithCamel类。
     *
     *
     *
     *
     */

}
