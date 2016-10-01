package com.camel.test.demo.document;

/**
 *
 * Created by keen.zhao on 2016/10/1.
 */
public class CamelIntroduction03_txt {
    /**
     * 1.3 Camel消息模型
     *     在Camel中有两个消息模型的抽象概念。本节我们将会涉及到。如下：
     *          Org.apache.camel.Message ----这个基础的实体包含需要在camel中路由的数据。
     *          Org.apache.camel.Exchange----消息交换是camel的抽象概念。
     *                                       消息交换有个"in"消息，作为回复，还有一个"out"消息。
     *
     *     我们开始通过查看消息来理解数据是怎样在Camel中被建模和传输的。
     *     我们将看到在Camel中"conversation"是怎样被夹建模的。
     *
     * 1.3.1 消息
     *     消息是用于系统相互通信时消息信道的实体，是从发送者到接收者的一个方向上的消息流，如下示意：
     *              Sender  ----> Message ----> Receiver   (EIP有一套图语义，自行参考)
     *              ● 消息是用于从一个系统到另一个系统发送数据的实体

     *      消息有一个body(一个有效载荷，消息体)，headers（头）和可选的attachment(附件）
     *      Message｛Headers,Attachment,Body｝
     *
     *      消息通过一个java.lang.String类型的标识符被唯一标识，标识符的唯一性由消息的创建者强制保证的，
     *      消息是依赖协议的，它没有强制的格式。这些协议并没有定义一种独特的消息识别方案，Camel用自己的
     *      UID生成器。
     *
     *      ★ Headers和Attachment（ 头信息和附件）
     *         Headers是与Message关联的一些值，如发送者标识符、内容编码的提示、身份验证信息诸如此类的信息。
     *         Headers是name-value对的（类似于键值对），name是唯一的，不区分大小写的字符串，value是
     *         java.lang.Object类型。这意味着Camel在headers的类型上不做任何限制。Headers在Message里被存储为
     *         map。一个消息也可以有可选的Attachment(附件)，其中通常用于Web服务和电子邮件组件(components)。
     *
     *      ★ Body（消息体）
     *         Body(消息体)是java.lang.Object类型。
     *
     *
     *
     *
     *
     *
     *
     *
     */
}
