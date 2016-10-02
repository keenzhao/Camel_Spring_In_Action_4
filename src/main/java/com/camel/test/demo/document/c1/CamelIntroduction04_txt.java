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
     *               150个）components，功能范围从数据传输、DSLs、数据格式等等。你甚至可以为Camel创建自己的组件。
     *               在第11章我们将讨论它。
     *               从编程的观点看，components是相当简单的：关联一个在URI中使用的名字，充当一个端点工厂。例如，一个
     *               FileComponent通过在uri中的名字file来指定，并且它创建了一个FileEndpoints。
     *               endpoint也许是Camel中更基本的概念。
     *
     *            ★ ENDPOINT（端点）
     *               endpoint是一个能发送和接受消息的系统的通道端的Camel的抽象模型，如图1.8所示。
     *               在Camel中，你使用URIs配置endpoint，例如file:data/inbox?delay=5000，并且你同样要参考这种方式。
     *               在运行时刻，Camel将基于URI标记法来查找一个endpoint，图1.9显示了这是如何工作的。
     *               scheme ① 指出Camel组件要操作endpoint的类型，在这个案例中，file的scheme表示选择了FileComponent。
     *               FileComponent然后会像工厂一样基于URI剩余部分创建FileEndpoint。上下文路径 data/inbox ② 告诉
     *               FileComponent开始的文件夹是data/inbox。选项delay=5000 ③ 表明每隔5秒对文件进行轮询。
     *               会有更多的endpoint进入我们的视线。图1.10显示了一个endpoint如何与一个exchange、生产者、消费者在
     *               一起工作的。乍看起来，图1.10有点令人不知所措，但是它立刻将一切变得都有意义。简而言之，一个endpoint
     *               充当一个工厂，创建具有接受和发送消息到特定endpoint能力的消费者和生产者。在图1.6的Camel高层次视图
     *               中，我们没有提及生产者或消费者，但它们是重要的概念，下一节，我们将认真讨论它们。
     *
     *            ★ PRODUCER（生产者）
     *               一个生产者是Camel抽象概念，意指一个能够创建和发送一个消息到一个端点的实体。图1.10说明了生产者相适应
     *               其他Camel的概念。
     *               当一个消息需要被发送到endpoint时，生产者将创建一个exchange并填充兼容那个特定endpoint的数据。例如，
     *               一个FileProducer会将消息体(message body)写到一个文件。在另一方面，一个JmsProducer，在将Camel的
     *               消息发送到JMS目的地之前会把Camel的message映射到javax.jms.Message，在Camel中，这是一个重要的特性，
     *               因为它隐藏了使用特定传输的交互复杂性。你所需要做的就是将一个消息路由到一个端点，生产者做了最重的活。
     *
     *            ★ CONSUMER（消费者）
     *               消费者是接收生产者制造出的消息的服务，把它们包装在一个exchange中，并发送它们去处理。在Camel中，
     *               消费者是被路由的exchanges的源头。
     *               回过头来看图1.10，我们可以看到消费者与其他Camel概念相适应的地方。要创建一个新的exchange，消费者
     *               将使用endpoint来包装将被消费的有效载荷。然后，使用一个处理器在Camel中使用路由引擎来启动exchange路由。
     *               在Camel中有两种消费者：事件驱动消费者和轮询消费者。这些消费者之间的差异是很重要的，因为他们有助于
     *               解决不同的问题。
     *
     *            ★ EVENT-DRIVEN CONSUMER（事件驱动消费者）
     *               大多数的消费者是事件驱动消费者，如图1.11所示。
     *               这种消费者主要是与客户/服务器架构和Web服务相关联的。在EIP中它也被称为异步接收器。一个事件驱动的消费者
     *               在一个特定的消息传递通道上监听，通常是一个TCP/IP端口或JMS队列，并等待客户端发送消息给它。当消息到达时，
     *               消费者醒来并获取消息来处理。
     *
     *            ★ POLLING CONSUMER（轮询消费者）
     *               另一种消费者是轮询的消费者，如图1.12所示。
     *               与事件驱动消费者相比之下，轮询消费者会从特定的源去主动去拿消息，例如一个FTP服务器。在EIP术语中，轮询
     *               消费者也被称为同步接收器，因为在结束当前正在处理的消息之前他不会去轮询更多的消息。轮询消费者的一个共同
     *               的特点是定期轮询消费，即在预定的时间间隔内进行轮询。文件，FTP和电子邮件传输都使定期轮询消费者。
     *               我们现在已经涵盖了所有的Camel的核心概念。有了这个新的知识，可以反复访问你的“第一次骑骆驼”，看究竟发生
     *               了什么。
     *
     *
     *  1.5 重访你的第一次骑骆驼
     *      记得在你第一次骑骆驼（第1.2.2节），你从一个目录（数据/收件箱）读文件并将结果写到另一个目录（数据/箱）。
     *      既然你知道了Camel的核心概念，你就可以把这个例子显示在脑海中。
     *      再看一看Camel的应用。
     *
     *      public class FileCopierWithCamel {
     *          public static void main(String args[]) throws Exception {
     *              CamelContext context = new DefaultCamelContext();
     *              context.addRoutes(new RouteBuilder() {
     *                      public void configure() {
     *                          from("file:data/inbox?noop=true")  // ① java DSL route
     *                              .to("file:data/outbox");
     *                      }
     *                  });
     *              context.start();
     *              Thread.sleep(10000);
     *              context.stop();
     *          }
     *      }
     *
     * 在这个例子中，您首先创建一个CamelContext，这是Camel的运行环境。然后通过DSL使用RouteBuilder和java DSL添加路由逻辑，
     * 你能干净而又简明地让Camel实例化components，endpoint，consumer，producer等等，你所有的关注点在为您的集成项目的问题
     * 定义路由。尽管在底层，Camel访问FileComponent并像工厂一样创建endpoint和它的生产者。相同的FileComponent也用于创建
     * 消费方。
     *
     *  1.6 总结
     *      在本章中你遇到了Camel，看到了依赖已知的EIPs，Camel是如何简化集成的。你也看到了Camel的DSL,其目的使Camel代码自
     *      我记录，让开发者关注中间代码做什么，而不是如何做的。我们涉及了Camel的主要特性，Camel是什么，不是什么以及它能用
     *      在那里。我们看到Camel如何提供抽象概念并工作在大范围的协议和数据格式之上的API。在这点上，你应该多Camel做什么有
     *      一个很好的理解，以及Camel背后的概念是什么。很快你将能够自信地浏览Camel的应用程序，并获得他们所做的一个好理念。
     *      在本书的其余部分中，我们将探讨Camel的特点，并给你实用的解决方案，你可以应用在日常的集成方案中去。
     *      我们还将解释在Camel强壮的表面下发生了什么事。为了确保你获取到每一章的主要概念，从现在开始，我们将向你介绍一些
     *      最佳实践和要点总结。
     *
     *      在下一章中，我们将深入研究路由，这是一个重要的功能和一个有趣的学习。
     *
     */

}
