package com.camel.test.demo.document.c1;

/**
 * 粗糙翻译一下，后续调整，感兴趣同学，自行阅读英文原版《Camel in action》
 *
 * Created by keen.zhao on 2016/10/2.
 */
public class CamelIntroduction04_txt {

    /**
     * 1.4、Camel的体系结构
     *      现在让我们把注意力转移到Camel的体系结构上来。我们先看一下高水平的架构然后深入到具体的概念。
     *      在读完这段内容后，你应该领悟集成术语并为第二章节准备，那里我们将深入研究Camel的路由能力。
     *
     *  1.4.1 1万英尺高的建筑
     *      我们认为架构最好的视图首先从更高层次看起。图1.6显示了组成Camel体系结构主要概念的高层次的视图。
     *      图见illustration包下的camel1_6.png。
     *
     *      路由引擎使用路由作为消息路由的规范，Routes（路由）使用camel的DSL定义。Processors(处理器)用于
     *      在路由期间转换和操作消息并实现了所有的EIP模式，在DSL语言中都有对应的关键字。Components是添加
     *      连接到其他系统的扩展点，为了将这些（外部）系统暴露给Camel的其余部分，Components提供了endpoint
     *      （端点）接口。
     *      有了高层次的视图的方式，让我们仔细看看在图1.6中的个别概念。
     *
     * 1.4.2 Camel概念
     *      图1.6显示了许多新的概念，因此让我们花些时间一个一个理解它。我们从CamelContext开始，它是Camel的
     *      运行环境。
     *           ★ CamelContext（Camel上下文）
     *              从图1.6判断，你可能猜到了，CamelContext是一个分类容器。你可以理解为是Camel的运行环境系统，
     *              把所有的部件放在一起。
     *              图1.7把CamelContext最为显著的服务放在一起显示出来。从图1.7可以看到，有诸多的服务与Camel
     *              保持联系，这些都在表1.1中被描述。贯穿本书这些服务的每一个细节都将被讨论。让我们先看看路由
     *              和路由引擎。
     *
     *              表1.1 CamelContext提供的服务
     *              ====================================================================================
     *              Service（服务）                               Description（描述）
     *              ====================================================================================
     *              Components（部件）            包含所使用的组件。Camel能在运行中装载components，通过在类
     *                                            路径中自动发现，或者在OSGi容器中当一个新的Bundle（OSGi的
     *                                            模块化的物理单元，以jar文件的形式包含代码、资源、元数据）
     *                                            被激活的时候。第7章将讨论components的更多细节。
     *
     *              Endpoints（端点）             包含已创建的endpoints。
     *
     *              Routers（路由）               包含已添加的路由，第二章将涉及routers。
     *
     *              Type Converters（类型转换器）  包含已加载的类型转换器。Camel有一个机制，允许您手动或自动
     *                                            从一种类型转换到另一种类型，类型转化器在第3章涉及。
     *
     *              Data formats（数据格式）      包含已加载的数据格式。数据格式在第3章涉及。
     *
     *              Registry（注册表）            包含一个允许你查找bean的注册表。默认情形下是一个JNDI注册。
     *                                            如果你从Spring使用Camel，它将是Spring的ApplicationContext。
     *                                            如果在OSGi容器中使用Camel，它也可能是一个OSGi注册表。
     *
     *              Languages（语言）             包含已加载的语言。Camel允许你使用多种语言来创建表达式。
     *                                            当我们涉及DSL时可以一睹XPath language in action这本书。在附录A
     *                                            中有完整的Camel自身可用的简单表达式语言的参考。
     *
     *           ★ Routing Engine（路由引擎）
     *              Camel的引擎实际上是在底层移动消息的，引擎并不暴露给开发者的，但你应该意识到它的存在并且它做了所有
     *              确保消息正确路由的重活。
     *
     *           ★ Routes（路由）
     *              Camel的Routes显然是一个核心抽象概念。定义一个路由的最简单的办法是当作一系列处理器。在消息应用程序
     *              中使用路由器的原因有很多。出自客户端从服务器以及生产者从消费者的解耦的需要，routes能做下面这些：
     *                    ■ 动态决定客户端将调用什么服务器
     *                    ■ 提供一种灵活的方式来增加额外的处理
     *                    ■ 允许客户端和服务器是独立开发
     *                    ■ 允许服务器的客户端无需存根（使用Mock）来达到测试目的
     *                    ■ 通过连接不同的系统做好一件事来促进更好的设计实践
     *                    ■ 增强一些系统的特点和功能（如message brokers和ESBs）
     *
     *              Camel的路由有一个唯一的标识符用于日志、调试、监控以及启动和停止路由。路由也要有确切的一个消息
     *              输入源，它们被有效地绑定到一个输入端点上（endpoint）。
     *              为了定义一个路由，需要使用一个DSL。
     *
     *           ★ DOMAIN-SPECIFIC LANGUAGE (DSL) 领域特定语言
     *              Camel定义一个DSL将processors和endpoint连接在一起形成路由，术语DSL用在这里有点随意。在Camel中
     *              DSL意味着一个包含符合EIP术语的命名方法的优雅的java API。
     *                 考虑这个例子：
     *                      from("file:data/inbox")
     *                          .filter().xpath("/order[not(@test)]")
     *                          .to("jms:queue:order")
     *                 这里在单一的java语句里，你定义了一个从文件端点消费文件的路由。消息然后路由到EIP过滤器，此过
     *                 滤器使用一个XPath断言来测试消息是否是一个order测试，如果消息通过测试，它将被转发到JMS端点。
     *                 过滤器测试失败消息将被删除。
     *
     *              Camel提供多种DSL语言，因此也可以使用Spring DSL来定义与上面相同的路由，像这样：
     *                     <route>
     *                         <from uri="file:data/inbox"/>
     *                         <filter>
     *                             <xpath>/order[not(@test)]</xpath>
     *                             <to uri="jms:queue:order"/>
     *                         </filter>
     *                     </route>
     *              DSLs为Camel用户创建应用提供了很好的抽象,尽管在底层，一个路由实际上是由一个processors图组成的。
     *
     *            ★ PROCESSOR（处理器）
     *               处理器是Camel的一个核心概念，代表一个节点有使用、创建或修改传入的Exchange的能力。
     *               在路由期间，exchange流是从一个processor到里一个processor的；这样，你可以把一个路由当做一张图
     *               来理解，图中的每个节点都是特定的processor，图中的连线是连线是连接一个processor的输出到另一个
     *               processor的输入。许多processor是EIPs的实现，但可以很容易实现自己定制的processor并插入到路由中。
     *               那么这个processor图的exchanges是如何输入(get in)和输出(get out)的呢？要理解这个，我们需要看下
     *               components和endpoint。
     *
     *            ★ COMPONENT（组件）
     *               在Camel中，components是主要的扩展点。到目前为止，在Camel的生态系统中有超过80个（实际现在已经超过
     *               150个了）components，功能范围从数据传输、DSLs、数据格式等等。你甚至可以为Camel创建自己的组件。
     *               在第11章我们将讨论它。
     *               从编程的观点看，components是相当简单的：关联一个在URI中使用的名字，充当一个端点工厂。例如，一个
     *               FileComponent通过在uri中的file来指定，并且它创建了一个FileEndpoints。
     *               endpoint也许是Camel中更基本的概念。
     *
     *            ★ ENDPOINT（端点）
     *
     *
     *
     *
     */

}
