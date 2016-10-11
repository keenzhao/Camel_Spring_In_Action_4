package com.camel.test.demo.document.c2;

/**
 * 粗糙翻译一下，后续调整，感兴趣同学，自行阅读英文原版《Camel in action》
 *
 * Created by keen.zhao on 2016/10/10.
 */
public class RoutingWithCamel04_txt {

    /**
     * 2.5 路由和EIPs（Routing and EIPs）
     *         到目前为止，我们还没有接触到更多的由Camel构建实现的EIPs。这是有意的。在变换到更复杂例子之前，我们想确保你
     *     能很好地理解在最简单的情况下Camel在做什么。
     *         至于EIPs做的，我将看到基于内容的路由，消息过滤器，多播，收件人列表以及即时监听。其他的模式通过本书将被引入，
     *     以及第8章我们将涵盖最复杂的EIPs。Camel支持的EIP完整列表来自Camel站点：
     *     （(http://camel.apache.org/enterprise-integration-patterns.html）
     *
     *        现在，让我们来看下最著名的EIP，基于内容的路由。
     *
     * 2.5.1 使用基于内容的路由（Using a content-based router）
     *           顾名思义，一个基于内容的路由（CBR）是一种根据它的内容路由一个消息到目的地的消息路由。内容可以是一个消息头，
     *       有效载荷的数据类型，有效载荷自身的一部分 --- 几乎是消息exchange中的任何东西都可以。
     *           为了证明，让我们回到Rider汽车配件公司。一些客户开始使用新的XML格式而不是CSV上传订单到FTP服务器，这意味着有
     *       2种消息类型传入到incomingOrders队列。在这之前我们没有碰到过，但你需要将收到的订单转换成一个内部的POJO格式。
     *       你显然要为不同类型的新进订单做不同的转换。
     *           作为一个可能的解决方案，你可以使用文件扩展名来确定一个特定的订单消息是应该被发送到CSV订单的队列还是XML订
     *       单的队列。如图2.10所示。
     *           正如你前面看到的，你可以通过FTP的消费者使用CamelFileName标头设置获取文件名。依据CBR的规定做条件路由，
     *       Camel在DSL中引入了几个关键字。choice方法创建一个CBR处理器，并且条件被加入是通过跟着一个when方法和一个谓词
     *       组合的choice来完成的。
     *           Camel的创造者可以选择contentBasedRouter作为方法名来匹配EIP，但是他们坚持用choice是因为它读起来更自然。
     *       它看起来像这样：
     *
     *           from("jms:incomingOrders")
     *          .choice()
     *              .when(predicate)
     *                  .to("jms:xmlOrders")
     *              .when(predicate)
     *                  .to("jms:csvOrders");
     *
     *           你可能已经注意到，我们没有填写每一个方法所需的谓词。Camel中的一个谓词是一个简单的接口，只有一个matches方法：
     *
     *          public interface Predicate {
     *              boolean matches(Exchange exchange);
     *          }
     *
     *           例如，你可以把一个谓词理解成java中if语句的布尔表达式。
     *
     *           你自己可能不想看到exchange内容并来做一个比较。幸运地是，谓词往往是从表达式中建立起来的，而表达式是用来从
     *       基于表达式内容的exchange中提取结果的。在Camel中有许多不同的表达式语言供选择，其中包括Simple，EL，JXPath，
     *       Mvel，OGNL,PHP,BeanShell,JavaScript, Groovy, Python, Ruby,XPath, and XQuery。正如你在第4章看到的那样，
     *       在Camel中，你甚至可以使用一个方法调用一个bean作为一个表达式。在这种情况下，你将要使用作为Java DSL的一部分的
     *       表达式生成器方法。
     *           在RouteBuilder内，你可以通过使用标头来启用返回一个对标头值进行求值的表达式的方法。例如，
     *       header("camelfilename")将创建一个在传入的exchange上将决定CamelFileName标头值的表达式，在这个表达式中，
     *       你可以调用一些方法来创建一个谓词。因此，检查文件扩展名是否是等于.xml，您可以使用以下谓词：
     *
     *              header("CamelFileName").endsWith(".xml")
     *
     *       完整的CBR显示在这里。
     *
     *              context.addRoutes(new RouteBuilder() {
     *                  public void configure() {
     *
     *                      from("file:src/data?noop=true").to("jms:incomingOrders");
     *
     *                      from("jms:incomingOrders") //基于内容的路由
     *                      .choice()
     *                          .when(header("CamelFileName").endsWith(".xml"))
     *                              .to("jms:xmlOrders")
     *                          .when(header("CamelFileName").endsWith(".csv"))
     *                              .to("jms:csvOrders");
     *
     *                      from("jms:xmlOrders").process(new Processor() {  //测试路由打印消息内容
     *                                  public void process(Exchange exchange) throws Exception {
     *                                          System.out.println("Received XML order: "
     *                                                       + exchange.getIn().getHeader("CamelFileName"));
     *                                  }
     *                      });
     *
     *                      from("jms:csvOrders").process(new Processor() {  //测试路由打印消息内容
     *                                  public void process(Exchange exchange) throws Exception {
     *                                          System.out.println("Received CSV order: "
     *                                                      + exchange.getIn().getHeader("CamelFileName"));
     *                                  }
     *                      });
     *                  }
     *              });
     *
     *       为了运行这个例子，请转到在本书的源代码chapter2/cbr目录下并运行这个Maven命令：
     *              mvn clean compile exec:java -Dexec.mainClass=camelinaction.OrderRouter_CBR
     *       这将消费在chapter2/cbr/src/data目录下的2个订单文件并输出：
     *              Received CSV order: message2.csv
     *              Received XML order: message1.xml
     *
     *
     *
     *
     *
     *
     */
}
