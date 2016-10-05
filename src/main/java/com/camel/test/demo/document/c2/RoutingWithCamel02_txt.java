package com.camel.test.demo.document.c2;

/**
 * 粗糙翻译一下，后续调整，感兴趣同学，自行阅读英文原版《Camel in action》
 *
 * Created by keen.zhao on 2016/10/4.
 */
public class RoutingWithCamel02_txt {
    /**
     *
     * 2.3 使用Java创建路由（Creating routes in Java）
     *     在1章中，你看到每个CamelContext可以包含多个路由以及一个RouteBuilder如何被用来用来创建一个路由。
     *     尽管它可能不是很显著，RouteBuilder不是CamelContext在运行时刻的最终路由，它是一个或更多的被加入
     *     到CamelContext中的路由的生成器。如图2.5所示。
     *     CamelContext的addRoutes方法接受一个RoutesBuilder，不只是一个RouteBuilder。RoutesBuilder接口有
     *     一个单一的方法定义：
     *
     *         void addRoutesToCamelContext(CamelContext context) throws Exception;
     *
     *     这意味着你可以使用自己的自定义类来建立Camel路由。但创建路由最常见的方式是用实现了RoutesBuilder的
     *     RouteBuilder类。RouteBuilder类也给了你访问Camel的Java DSL进行路由创建的权力。在接下来的章节中，
     *     你将学习如何使用RouteBuilder和java DSL来创建简单的路由。一旦你知晓这些，你会有充分的准备去接受
     *     在2.4节的Spring DSL以及2.5节的使用EIPs路由。
     *
     * 2.3.1 使用RouteBuilder（Using the RouteBuilder）
     *       Camel中的org.apache.camel.builder.RouteBuilder是你经常看到的一个抽象类，在java中创建一个路由你
     *       需要随时使用它。
     *       使用RouteBuilder类，你需要从它继承扩展出一个类，并实现configure方法，像这样：
     *
     *              class MyRouteBuilder extends RouteBuilder {
     *                  public void configure() throws Exception {
     *                          ...
     *                  }
     *              }
     *
     *       你需要使用addRoutes方法将类加入到CamelContext中：
     *
     *              CamelContext context = new DefaultCamelContext();
     *              context.addRoutes(new MyRouteBuilder());
     *
     *       作为一种选择，你你能结合RouteBuilder和CamelContext配置通过添加一个RouteBuilder的匿名类直接到
     *       CamelContext中，像这样：
     *
     *              CamelContext context = new DefaultCamelContext();
     *              context.addRoutes(new RouteBuilder() {
     *                  public void configure() throws Exception {
     *                          ...
     *                  }
     *              });
     *
     *       在configure方法内，你使用Java DSL定义你的路由。我们将在下一节详细讨论Java DSL，但你现在可以
     *       开始一个路由去了解它是如何工作的。
     *
     *       （译者注：这里涉及书中IDE的操作部分略去，把其中一些有价值的段落翻译过来）
     *       from方法接受一个endpoint URI作为参数。你可以加入一个FTP的endpoint URI连接到Rider汽车配件公司的
     *       订单服务器，像下面这样：
     *             from("ftp://rider.com/orders?username=rider&password=secret")
     *       from方法返回一个RouteDefinition对象，你可以在调用许多不同的方法，实现EIPs和其他的消息概念。
     *       恭喜你，你现在用Camel的java DSL！让我们仔细看看这里发生什么事了。
     *
     * 2.3.2 Java DSL（The Java DSL）
     *       领域特定语言（DSL）是针对特定的问题域的计算机语言，而不是一个通用的领域，如大多数编程语言那样。
     *       例如，你可能使用正则表达式DSL匹配的文本中的字符串并发现这是一个清晰和简明的匹配字符串的方式。
     *       做相同的字符串匹配在java中就不会那么容易。正则表达式是一个外部DSL，它具有自定义语法并且因此需要
     *       一个单独的编译器或者解释器来执行。
     *       相比之下，内部的DSL使用现有的通用语言，如java，以这样的一种方式，DSL感觉像来自特定域语言。做这个
     *       最明显的方式是通过命名方法和参数来匹配来自问题域的概念。
     *       其他实现内部DSLs的流行方式是使用fluent interface(流式接口)(aka fluent builders）。当使用一个流式
     *       接口时，你用一些方法逐步建立对象，这些方法执行一个操作返回当前对象实例，然后在这个对象实例上执行其
     *       他的方法，依此类推。
     *       （译者注：要想了解fluent接口请自行参考资料，这里不宜过多叙说，不是一段话能说明白的，作者表达的思想是
     *       Java DSL实现的是fluent接口，这不是本书的关注点！）
     *
     *       Camel的领域是企业集成，因此Java DSL本质上是一组包含用EIP术语来命名方法的fluent接口。在eclipse编辑
     *       器中，在RouteBuilder中from方法后面使用自动完成看看什么是可以使用的。你应该看到像如图2.7所示的东西。
     *       截图显示几个EIPs--Pipeline、Enricher和Recipient List，还有很多其他的，我们以后会讨论。
     *
     *       现在，选择方法并用一个分号结束路由。在RouteBuilder中创建新路由的每个Java语句都以from方法开始。这个
     *       新的路由完成了你的第一个Rider汽车配件公司的任务---从FTP服务器消费订单并发送它们到JMS队列incomingOrders
     *       。如果你想，你可以从书中的源代码中加载完成的例子。该代码如清单2.1所示。
     *       代码见：com.camel.test.demo.example.c2.FtpToJMSExample
     *
     *          注意：因为要消费的ftp://rider.com不存在，这个例子你不能跑起来，他仅仅对Java DSL的构成有示范作用。
     *          可运行的FTP例子，见第7章。
     *
     *       正如你所看到的，这个代码清单包括一点公式化的设置和配置，但问题实际的解决方案在configure方法内部作为
     *       一个单一的java语句被简明的定义。from方法告诉Camel从FTP endpoint上消费消息，并且to方法通知Camel发送
     *       消息到一个JMS endpoint。
     *
     *       在这个简单路由中的消息流可以被看成一个基本管道，其中的消费者的输出被馈送到生产者的输入，如图2.8所示。
     *
     *       你可能已经注意到一件事是从FTP文件类型的JMS消息类型我们没有任何转换---这个是由Camel的TypeConverter功能
     *       自动去做的。在路由过程中，你随时可以发生强制类型转换，但通常你不必担心它们。在第3章中详细介绍数据转换
     *       和类型转换。
     *
     *        你现在可能在想，虽然这条路由是很好的和简单的，真的很高兴看到路由的中间发生了什么。幸运的是，Camel总是
     *        通过提供挂钩到流（Hook不翻译成中文更好理解）的方式或注入行为特性来让开发人员保持控制。通过使用一个处理
     *        器有一个非常简单的方式获得访问的消息，我们下一个将讨论下。
     *
     *        ★ 加入一个处理器（ADDING A PROCESSOR）
     *          Camel的处理器接口是复杂路由的一个重要组成模块。它是一个简单的接口，有一个单一方法：
     *
     *             public void process(Exchange exchange) throws Exception;
     *
     *          这个给了你全权访问消息exchange的能力，无论你想要payload还是Headers，基本上都可以做到。
     *          Camel中的所有的EIPs都是以processor实现的。您甚至可以添加一个简单的processor到您的路由内联，像这样：
     *
     *               from("ftp://rider.com/orders?username=rider&password=secret").
     *                  process(new Processor() {
     *                          public void process(Exchange exchange) throws Exception {
     *                              System.out.println("We just downloaded: "
     *                              + exchange.getIn().getHeader("CamelFileName"));
     *                          }
     *                  }).
     *                  to("jms:incomingOrders");
     *
     *           现在这个路由在发送订单文件到JMS队列之前，将打印输出下载的订单文件名。
     *
     *           通过添加这个processor到路由中，你已经有效地把它添加到早先提到的概念管道，如图2.9所示。
     *           FTP消费者的输出馈送到Processor作为输入，Processor没有修改消息的payload和headers，所以exchange移动
     *           到JMS生产者作为输入。
     *
     *                注意：许多组件，如FileComponent和FtpComponent,在incoming消息上设置有用的headers描述有效载荷。
     *                在前面的例子中，你使用了CamelFileName标头（headers）检索了通过FTP下载的文件的文件名。component
     *                页面的联机文档包含了每个单独组件的标头集合，关于FTP组件的信息你可以在在这里找到：
     *                http://camel.apache.org/ftp2.html
     *
     *           Camel创建路由的主要方法是通过Java DSL。毕竟，它是Camel核心模块内置的。虽然有其他的方式创建路由，它们
     *           中一些可能更适合你的情况。比如，Camel为在Groovy, Scala中写路由提供了扩展，以及我下节讨论的Spring XML。
     *
     */

}
