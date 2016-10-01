package com.camel.test.demo.document;

/**
 * 粗糙翻译一下，后续调整，感兴趣同学，自行阅读英文原版《Camel in action》
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
     * 1.3.1 Message（消息）
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
     *         Body(消息体)是java.lang.Object类型。这意味着一个Message可以存储任何类型,这也意味着，它取决于
     *         应用程序设计人员以确保接收者可以理解消息的内容。当发送者和接收者使用不同的Body格式时，Camel提供
     *         了许多机制来转换数据到一个可接受的格式，在许多情况下，使用类型转换器在幕后进行自动转换。
     *
     *      ★ FAULT FLAG（故障标记）
     *         Message也有故障标志。一些协议和规范，如WSDL和JBI，区分输出和错误消息。它们都是调用操作的有效
     *         回应，但后者表示一个不成功的结果。一般情况下,故障不被集成框架处理。它们是客户端和服务器之间的
     *         契约的一部分，故障在应用级别上处理（集成框架只负责数据传输）。
     *
     *         在路由的过程中，Message被包含在一个Exchange中。
     *
     * 1.3.2 Exchange（交换）
     *        在路由过程中，Camel的Exchange是Message的容器。Exchange在系统间也提供了支持各种类型的交互，也被
     *        称作消息交换模式(message exchange patterns,MEPs),MEPs的使用在单向和请求响应通讯方式上有区别的。
     *        Camel的Exchange能容纳两种属性中的任意一种模式。
     *             InOnly ---- 单向消息（也称为事件消息），例如，JMS消息往往是单向的信息传递。
     *             InOut ----  请求响应消息。例如，基于HTTP的传输常常是请求应答的，一个客户端请求检索网页，
     *                         等待服务器的应答。
     *        Camel中Exchange的内容包括：
     *        Exchange｛ExchangeID，MEP，Exception，Properties，In message，Out message｝
     *                   In message，Out message ====> Message｛Headers,Attachment,Body｝
     *
     *        让我们来看下Exchange的元素:
     *             Exchange ID ---- 一个识别Exchange唯一标识ID，如果你不显式地设置一个的话，Camel将默认产生
     *                              唯一ID。
     *             MEP ---- 一个模式，指示你正在使用的是InOnly或是InOut的通讯方式。
     *                      当模式是InOnly时，Exchange包含了一个"In message"。对于InOut，一个包含给调用者应答
     *                      消息的"Out message"也存在。
     *             Exception ---- 如果在路由过程中的任何时候发生错误，则将在异常字段中设置异常。
     *             Properties ---- 类似于消息的headers，但它们一直持续在整个交换期间。Properties被用来包含
     *                             全局信息，而消息的Headers是具体到特定消息的。Camel自身会在路由期间向
     *                             Exchange添加各种属性的。作为一个开发人员，您可以在Exchange的生命期中的
     *                             任何一点上存储和检索Properties。
     *             In message ---- 这是输入消息，这是强制性的。In message中包含请求消息。
     *             Out message ---- 这个仅当MEP是InOut时存在的可选消息。Out message包含应答消息。
     *
     *   在体系结构之前我们讨论了Camel的消息模型，因为我们希望你扎实地理解Camel中Message。毕竟，Camel中最重要的
     *   方面是路由消息。你现在应该准备好了学习更多的Camel和它的体系结构。
     *
     */
}
