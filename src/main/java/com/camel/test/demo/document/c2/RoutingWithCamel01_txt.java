package com.camel.test.demo.document.c2;

/**
 *
 * Created by keen.zhao on 2016/10/3.
 */
public class RoutingWithCamel01_txt {

    /**
     *  Routing with Camel（Camel的路由）
     *
     *    Camel最重要的特点之一是路由，没有她，Camel基本上就是一个传输连接器库，这一章，我们将深入到Camel路由中去。
     *    路由发生在日常生活的许多方面。例如，当你邮寄一封信时，在到达它的最终地址之前，它可能会经过几个城市。
     *    您发送的电子邮件将被路由通过许多不同的计算机网络系统到达它的最终目的地之前。在所有情况下，路由器的功能是
     *    有选择地性将消息进行转发。
     *    在企业消息传递系统的上下文中，路由是一个消息从输入队列中取出并根据一组条件发送到几个输出队列中其中一个的
     *    过程，如图2.1所示。这有意味着输入和输出队列都不知道在它们之间存在一些条件。条件逻辑与消息消费者和生产者
     *    是分离的。
     *    在Apache Camel中，路由是一个比较笼统的概念。它被定义为源于消费者角色中的endpoint的消息的一步一步地移动。
     *    消费者可能会收到来自外部服务的消息，甚至创建消息本身。这一消息再流经处理组件，这可能是一个企业集成模式
     *    （EIP），一个处理器，一个拦截器，或其他一些自定义的创作。该消息最终被发送到生产者角色中的目标endpoint。
     *    一个路由可能有许多处理组件，这些组件修改消息或发送消息到另一个位置或什么也不做，在这种情况下，它将是一个简
     *    单的管道。
     *
     *    在本章中，我们将首先介绍一个虚构的公司，我们将使用它作为整个书的运行的例子。依靠公司的使用案例，你将学会
     *    使用Camel的endpoint与FTP和java消息服务（JMS）进行通讯。接下来，我们将深入探索基于java的领域特定语言
     *   （DSL）和基于Spring的配置格式创建路由。我们也会让你看到如何利用EIP和Camel来设计和实现企业集成问题的解决方案。
     *    到本章结束时，你将精通到足以创建有用的Camel的路由应用程序。
     *
     *    开始吧，让我们看下例子公司（我们将通过这本书来展示整个概念的虚拟公司）。
     *
     * 2.1 介绍Rider汽车配件公司
     *     我们虚构的摩托车配件业务，Rider汽车配件公司，向摩托车制造商供应零部件。这些年来，他们已经数次改变了接受
     *     订单的方法，最初，订单通过逗号分离值 (CSV)文件上传到FTP服务器。消息格式后来又改为XML。目前它们提供了一
     *     个网站，通过它基于HTTP提交像XML消息一样的订单。Rider汽车配件要求新的客户使用Web界面下订单，但是由于现有
     *     客户的服务等级协议（SLA）的原因，他们必须保持所有的旧消息格式和接口并正常运行。所有这些信息在处理前都转
     *     换为一个内部普通java对象（POJO）格式。一个订单处理系统的高级视图如图2.2所示。
     *
     *     Rider汽车配件公司面临着一个非常常见的问题：多年来的运作，他们已经获得了当时流行的软件包运送方式和数据格
     *     式。虽然这对像Camel这样的集成框架是没有问题的。通过本书，在本章中你将要使用Camel来帮助Rider汽车配件公司
     *     实现他们的目前的要求和新的功能。
     *
     *     作为第一个任务，你需要在Rider订单系统的前端系统实现FTP模块。在本章的后面，您将看到如何实现后端服务。
     *     FTP模块的实现将包括以下步骤：
     *        1、轮询FTP服务器并下载新的订单
     *        2、转换订单文件为JMS消息
     *        3、发送消息到JMS接收订单队列
     *     为了完成步骤1和步骤3，你需要了解如何使用Camel的endpoint与FTP和JMS来通讯。完成整个任务，你需要了解java
     *     的DSL路由。让我们先来看看你如何使用Camel的端点。
     *
     * 2.2 理解endpoints
     *     当你在第1章中读到，一个端点是一个抽象模型，一个系统可以发送或接收消息的消息通道的最后部分。在本节中，我们
     *     将解释如何使用URIs来配置Camel与FTP和JMS通信。让我们先看看FTP。
     *
     * 2.2.1 通过FTP文件工作（Working with files over FTP）
     *       一个让Camel容易使用的东西是endpoint的URI，通过指定一个URI，你可以确定你要使用的组件以及组件是如何配置的。
     *       然后你可以决定是发送消息到这个URI配置的组件，或者从它消费消息。
     *       以你的第一个Rider汽车配件公司的任务为例。从FTP服务器下载新的订单，你需要做到以下几点：
     *           1、以默认的FTP端口21连接到rider.com的FTP服务器
     *           2、提供"rider"用户名和"secret"密码
     *           3、改变目录到orders
     *           4、下载所有新的订单文件
     *       如图2.3所示（注意原书图的option部分少了"="号）。你可以通过URI标记法轻松地配置Camel做这些工作。
     *
     *       Camel将首先在组件注册表查找ftp方案，ftp将决定为FtpComponent，FtpComponent则作为一个工厂，创建基于余下
     *       的上下文路径和选项的FtpEndpoint。上下文路径rider.com/orders告诉FtpComponent，它应该在rider.com上使用
     *       默认的FTP端口登录到FTP服务器并更改目录到"orders"。最后，唯一被指定的选项是用于登录到FTP服务器的用户名
     *       和密码。
     *
     *            小窍门：对于FTP组件，你也可以在URI的上下文路径里指定用户名和密码。
     *            以下URI等效于图2.3中的URI:
     *            ftp://rider:secret@rider.com/orders
     *
     *       FtpComponent不是Camel核心模块，因此你需要添加一个额外的依赖到你的项目中，使用Maven你只需要添加以下
     *       依赖到POM即可：
     *          <dependency>
     *              <groupId>org.apache.camel</groupId>
     *              <artifactId>camel-ftp</artifactId>
     *              <version>2.5.0</version>
     *          </dependency>
     *
     *       尽管这个endpoint的URI在一个用它来从FTP服务器上下载订单的消费者或生产者场景中会工作得同样好。要这样做，
     *       你需要在Camel的DSL的节点from中使用它(URI)：
     *             from("ftp://rider.com/orders?username=rider&password=secret")
     *       这就是你要从一个FTP服务器来消费文件而所有需要做的。下一步你需要做的事情，因为你可能会从图2.2回想起，是那
     *       就是发送你从FTP服务器下载的订单到一个JMS队列。这个过程需要多一点的设置，但它仍然很容易。
     *
     * 2.2.2 发送到JMS队列（Sending to a JMS queue）
     *       Camel为JMS实现提供者提供了广泛的连接支持，在第7章我们将涵盖所有的细节。但是现在，我们只要涉及足够你能完成Rider
     *       汽车配件公司的第一个任务就可以了。回想一下，你需要从FTP服务器上下载订单并将其发送到JMS队列。
     *
     *       ★ JMS是什么？（WHAT IS JMS?）
     *          MS（java消息服务）是一个java的API，允许你创建、发送、接收、和阅读消息。它还规定，消息传递是异步的，
     *          具有特定的可靠性元素，像有保证的和一次且仅一次投递。在java社区,JMS是事实上的消息传递解决方案。
     *          在JMS中，消息的消费者和生产者彼此交谈是通过中间人 - 一个JMS目的地。如图2.4所示，一个JMS目的地可以
     *          是一个队列或一个主题。队列是严格的点对点，每个消息只有一个消费者。主题用于发布/订阅体系，一个单一
     *          的消息可能会被投递给许多消费者，如果它们已经订阅了这个主题。JMS也提供了ConnectionFactory，客户端
     *          （像Camel）可以用它来创建一个到JMS提供者的连接。JMS提供者通常称为经纪人（brokers），因为他们在生
     *          产者和消费者之间管理消息的沟通。
     *
     *       ★ 如何配置Camel使用JMS提供者（HOW TO CONFIGURE CAMEL TO USE A JMS PROVIDER）
     *          为了将Camel连接到一个特定的JMS提供者，你需要使用一个适当ConnectionFactory来配置Camel的JMS组件。
     *          Apache ActiveMQ是一个最流行的开源JMS提供者，并且它是Camel团队用来测试JMS组件的主要JMS broker。
     *          因此，在本书中我们用它来说明JMS概念。对于Apache ActiveMQ的更多信息，我们推荐由Bruce Snyder、
     *          Dejan Bosanac和Rob Davies合著的《ActiveMQ in Action》这本书。
     *
     *          因此在Apache ActiveMQ的案例中，你能创建一个指向运行ActiveMQ broker位置的ActiveMQConnectionFactory：
     *
     *             ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");
     *
     *          vm://localhost这个URI表示在你该连接到一个内嵌在当前的JVM中的指定为"localhost"的正在运行的broker。
     *          在ActiveMQ中，如果没有运行，vm传输连接器根据需要随时创建broker，所以对于快速测试JMS应用是非常好用的；
     *          对于生产环境，建议您连接到已经运行的broker（代理）。此外，在生产环境中，我们推荐在连接到JMS broker时
     *          使用连接池。详见第7章的这些替换配置的详细信息。
     *
     *          接下来，当你创建你的CamelContext，您可以添加JMS组件如下：
     *
     *             CamelContext context = new DefaultCamelContext();
     *             context.addComponent("jms",JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
     *
     *          JMS组件和ActiveMQ专用的连接工厂不是Camel的核心模块。为了使用这些，你需要添加一些依赖到你的基于Maven
     *          的项目中去。对于普通的JMS组件，你要添加的是：
     *              <dependency>
     *                  <groupId>org.apache.camel</groupId>
     *                  <artifactId>camel-jms</artifactId>
     *                  <version>2.5.0</version>
     *              </dependency>
     *
     *          连接工厂直接来自ActiveMQ，所以你需要以下依赖:
     *              <dependency>
     *                  <groupId>org.apache.activemq</groupId>
     *                  <artifactId>activemq-core</artifactId>
     *                  <version>5.3.2</version>
     *              </dependency>
     *
     *          现在你已经配置JMS组件连接到一个真实的JMS的broker，是时候看看URIs如何能被用于指定目的地。
     *
     *       ★ 使用URIS指定目的地（USING URIS TO SPECIFY THE DESTINATION）
     *          一旦JMS组件被配置好，你就可以在你的空闲时间开始发送和接收JMS消息。因为你正在使用的URIs，对配置而言
     *          这是一个轻而易举的事。比方说，你想送一个JMS消息到命名为incomingOrders队列。在这种情况下URI将是：
     *
     *                jms:queue:incomingOrders
     *
     *          这是相当明显的。“JMS”前缀表明你使用之前配置的JMS组件，通过指定“queue”, JMS组件知道将消息发送到一个
     *          名为incomingOrders的队列。你甚至可以省略队列限定符，因为默认的行为是发送到队列而不是一个主题。
     *
     *                注意：一些端点可能有一个令人生畏的端点URI属性列表。例如，JMS组件约有60个选项，其中有许多是
     *                只能用于特定的JMS场景。Camel总是试图提供内置的默认值以适合大多数情况，通过浏览联机Camel文档中
     *                的组件页面你总是可以找到那些默认值。
     *                JMS组件的讨论在这：http://camel.apache.org/jms.html
     *
     *           使用Camel的java DSL，通过使用关键字to你可以发送一条消息到incomingOrders，像这样：
     *
     *                ...to("jms:queue:incomingOrders")；
     *
     *           这可以解读为：发送到名为incomingOrders的JMS队列。
     *
     *           现在你知道了FTP和JMS与Camel通信的基本知识，你可以回到本章的路由主题和开始路由一些信息了！
     *
     。
     */

}
