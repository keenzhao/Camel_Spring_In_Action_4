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
     *
     *              mvn clean compile exec:java -Dexec.mainClass=camelinaction.OrderRouter_CBR
     *
     *       这将消费在chapter2/cbr/src/data目录下的2个订单文件并输出：
     *
     *              Received CSV order: message2.csv
     *              Received XML order: message1.xml
     *
     *       输出来自于configure方法尾部的2个路由。这些路由消费来自xmlOrders和svOrders队列的消息并打印输出消息。
     *       您使用这些路由来测试基于内容路由的工作预期。更高级的路由测试技术将在第6章中讨论。
     *
     *       ★ 使用OTHERWISE从句（USING THE OTHERWISE CLAUSE）
     *          一个Rider汽车配件公司的客户使用.csl扩展名发送CSV订单。当前路由只能处理.csv和.xml文件并将丢弃使用其他
     *          文件扩展名的所有文件。这不是一个好的解决方案，因此你需要做一些改进。
     *          一个处理额外扩展名的办法是使用一个正则表达式作为一个谓词而不是简单的endsWith调用。下面的路由能处理额外
     *          的扩展名：
     *
     *                  from("jms:incomingOrders")
     *                  .choice()
     *                      .when(header("CamelFileName").endsWith(".xml"))
     *                          .to("jms:xmlOrders")
     *                      .when(header("CamelFileName").regex("^.*(csv|csl)$"))
     *                          .to("jms:csvOrders");
     *
     *          尽管这个解决方案仍然存在同样的问题。任何不符合文件扩展名模式的订单将会被丢弃。事实上，你应该处理那些进来的
     *          坏订单为了让有人可以修复这个问题。你可以使用otherwise从句来解决这个：
     *
     *                  from("jms:incomingOrders")
     *                  .choice()
     *                      .when(header("CamelFileName").endsWith(".xml"))
     *                          .to("jms:xmlOrders")
     *                      .when(header("CamelFileName").regex("^.*(csv|csl)$"))
     *                          .to("jms:csvOrders")
     *                      .otherwise()
     *                          .to("jms:badOrders");
     *
     *          现在，所有没有.csv,.csl,或.xml扩展名的订单会被发送到badOrders队列处理。为了运行这个例子，请转到在本书
     *          的源代码chapter2/cbr目录下并运行这个命令：
     *
     *                  mvn clean compile exec:java -Dexec.mainClass=camelinaction.OrderRouterOtherwise
     *
     *          这将消费在chapter2/cbr/src/data目录下的4个订单文件并输出：
     *
     *                  Received CSV order: message2.csv
     *                  Received XML order: message1.xml
     *                  Received bad order: message4.bad
     *                  Received CSV order: message3.csl
     *
     *          你现在可以看到坏的订单已经被接收了。
     *
     *          ★ 在一个CBR之后的路由（ROUTING AFTER A CBR）
     *             CBR看上去像路由的终点；消息被路由到几个目的地，别的就没有了。继续流动意味着你需要另一条路由，对吗？
     *             不过，有几种方式你可以在一个CBR之后继续路由。一种方式是通过使用另一个路由，就像清单2.4中的您将测试消息
     *             打印到控制台上一样。继续流动的另一种方式是关闭choice块并它之后的管道中加入另一个处理器。
     *             你可以使用end方法关闭choice块：
     *
     *                  from("jms:incomingOrders")
     *                  .choice()
     *                      .when(header("CamelFileName").endsWith(".xml"))
     *                          .to("jms:xmlOrders")
     *                      .when(header("CamelFileName").regex("^.*(csv|csl)$"))
     *                          .to("jms:csvOrders")
     *                      .otherwise()
     *                          .to("jms:badOrders")
     *                  .end()
     *                  .to("jms:continuedProcessing");
     *
     *             这里，choice已经被关闭，另外一个to已经加入到路由。
     *             现在，在使用choice选择各自的目的地后，该消息也会将被路由到continued-Processing队列。如图2.11所示。
     *
     *             您还可以控制choice块中的最终目的地是什么。例如，你可能不希望坏订单继续通过其余的路线。你希望他们能被
     *             路由到badOrders队列和停在那里。在这种情况下，你可以使用DSL中的stop方法：
     *
     *                  from("jms:incomingOrders")
     *                  .choice()
     *                      .when(header("CamelFileName").endsWith(".xml"))
     *                          .to("jms:xmlOrders")
     *                      .when(header("CamelFileName").regex("^.*(csv|csl)$"))
     *                          .to("jms:csvOrders")
     *                      .otherwise()
     *                          .to("jms:badOrders").stop()
     *                  .end()
     *                  .to("jms:continuedProcessing");
     *
     *             现在，任何订单进入到otherwise块将只会被送到badOrders队列 --- 不会到达continuedProcessing队列。
     *
     *             使用Spring DSL，这个路由看上去有点不同：
     *
     *                  <route>
     *                      <from uri="jms:incomingOrders"/>
     *                      <choice>
     *                          <when>
     *                              <simple>${header.CamelFileName} regex '^.*xml$'</simple>
     *                              <to uri="jms:xmlOrders"/>
     *                          </when>
     *                          <when>
     *                              <simple>${header.CamelFileName} regex '^.*(csv|csl)$'</simple>
     *                              <to uri="jms:csvOrders"/>
     *                          </when>
     *                          <otherwise>
     *                              <to uri="jms:badOrders"/>
     *                              <stop/>
     *                          </otherwise>
     *                      </choice>
     *                      <to uri="jms:continuedProcessing"/>
     *                  </route>
     *
     *             除了是在XML中而不是在Java中定义，与Java DSL版本相比，这里有两个主要的差异要注意：
     *                  ■ 你使用一个Simple表达式，而不是基于java的谓词。Simple表达式语言通常被用来替代Java DSL的谓词。
     *                    一个Simple表达式语言的完整指南在 附录A中可以找到。
     *                  ■ 你没有使用一个end()调用来结束choice块，因为XML需要用一个明确的关闭元素</choice>形式来结束块。
     *
     *
     * 2.5.2 使用消息过滤器（Using message filter）
     *       Rider汽车配件公司现在有一个新的问题，他们的QA部门表示需要能够发送测试订单到已投入使用的订单系统的Web前端模块。
     *       您目前的解决方案是当作真实的订单来接受这些订单，并将其发送到内部系统进行处理。你们已经建议QA应该在一个真实系统
     *       的开发克隆上进行测试。但管理层已经否决了这一想法，理由是预算有限。您需要的是一个将丢弃这些测试消息同时仍然
     *       在操作真实的订单的解决方案。
     *
     *       如图2.12所示，消息过滤器EIP,提供了一个很好的方式处理这类问题。如果某个条件被满足传入的消息才通过过滤器，失败
     *       条件的消息将被删除。
     *
     *       让我们看看如何使用Camel来实现这个。记得Rider汽车配件公司的前端模块只发送XML格式的订单，所以你可以把这个过滤器
     *       放在存放所有XML格式的订单的xmlOrders队列之后。测试消息有一个额外的测试属性设置，所以你可以使用这个来做过滤。
     *       测试消息看起来像这样：
     *
     *              <?xml version="1.0" encoding="UTF-8"?>
     *              <order name="motor" amount="1" customer="foo" test="true"/>
     *
     *       整个解决方案的实现在riderRouterWithFilter.java文件中，在本书的源代码发布中包含了chapter2/filter项目。
     *       这个过滤器看起来像这样：
     *
     *              from("jms:xmlOrders").filter(xpath("/order[not(@test)]"))
     *              .process(new Processor() {
     *                      public void process(Exchange exchange) throws Exception {
     *                          System.out.println("Received XML order: "
     *                          + exchange.getIn().getHeader("CamelFileName"));
     *                      }
     *              });
     *
     *       为了运行这个例子，在命令行下执行下面的Maven命令：
     *
     *              mvn clean compile exec:java -Dexec.mainClass=camelinaction.OrderRouterWithFilter
     *
     *       在命令行下将输出：
     *
     *              Received XML order: message1.xml
     *
     *       在过滤器之后您只接收一个消息，因为测试消息已被过滤掉了。
     *
     *       你可能已经注意到这个例子使用一个XPath表达式来过滤掉测试消息。XPath表达式可用于创建基于XML有效载荷的条件。
     *       在这种情况下，表达式将那些没有test属性的订单求值为true。就像你回过头来看2.4.2节一样，当Spring DSL使用的时候，
     *       你不能使用一个匿名内部类的Processor，您必须命名Processor类，并在Spring XML文件中添加一个bean条目。
     *       所以在Spring DSL中一个消息过滤器路由看起来像这样：
     *
     *              <route>
     *                  <from uri="jms:xmlOrders"/>
     *                  <filter>
     *                      <xpath>/order[not(@test)]</xpath>
     *                      <process ref="orderLogger"/>
     *                  </filter>
     *              </route>
     *
     *       这个路由的流程仍然与java DSL版本相同，但这里你要使用在Spring XML文件中定义的orderLogger bean来引用处理器。
     *       到目前为止，我们看到的EIPs只发送消息到一个单一的目标。下一步，我们将看到如何可以发送到多个目的地。
     *
     *
     * 2.5.3 使用多播（Using multicasting）
     *       在企业应用中，你经常n需要发送一个消息的拷贝到几个不同的目的地来进行处理。当目的地列表提前知道并且是静态的时候，
     *       你可以在路由中添加一个元素，从源endpoint消费消息然后将消息发送到目的地的列表中。借用计算机网络的专业术语，
     *       我们称这种行为为组播EIP。
     *
     *       目前在Rider汽车零部件公司，订单是按一步一步的方式来处理的。他们首先发送给会计来确认客户身份，然后到生产制造。
     *       一个聪明的新经理建议通过在同一时间同时发送订单到会计和生产，他们可以提高操作的速度。这将减少从会计确定到生产
     *       所涉及的等待延迟。你被要求实现这一变化的系统。
     *       使用多播，您可以设想如图2.13所示的解决方案。
     *
     *       使用Camel，你可以使用Java DSL的multicast方法来实现这个解决方案。
     *
     *              from("jms:xmlOrders").multicast().to("jms:accounting", "jms:production");
     *
     *       要运行这个例子，到书中的源代码chapter2/multicast目录并运行这个命令：
     *
     *              mvn clean compile exec:java -Dexec.mainClass=camelinaction.OrderRouterWithMulticast
     *
     *       您应该在命令行上看到以下输出：
     *
     *              Accounting received order: message1.xml
     *              Production received order: message1.xml
     *
     *       这两行输出是来自从会计和生产队列消费的两个测试路由，然后将经过修饰的文本消息输出到控制台。
     *
     *              提示：为了处理在一个多播调用服务的响应，一个聚合器被使用。在第8章有关于聚合器更多的内容。
     *
     *       默认情况下，多播是顺序发送消息副本。在前面的示例中，一个消息先被发送到会计队列，然后发送到生产队列。但是如果
     *       你想并行发送它们呢？
     *
     *       ★ 并行多播（PARALLEL MULTICASTING）
     *          使用多播并行发送消息只涉及一个额外的DSL方法：parallelProcessing。扩展下前面的多播的例子，你可以添加
     *          parallelProcessing方法如下：
     *
     *              from("jms:xmlOrders")
     *                  .multicast().parallelProcessing()
     *                  .to("jms:accounting", "jms:production");
     *
     *         这将引起多播按并行方式分发消息到目的地。
     *         如果您没有指定其他的，则使用默认的线程池大小为10。如果你想更改该默认设置，你可以设置基础的用于启动新的
     *         异步消息发送的java.util.concurrent.ExecutorService，使用executorService DSL方法。下面是一个例子：
     *
     *              ExecutorService executor = Executors.newFixedThreadPool(16);
     *
     *              from("jms:xmlOrders")
     *                  .multicast().parallelProcessing().executorService(executor)
     *                  .to("jms:accounting", "jms:production");
     *
     *         此代码将线程的最大数目增加到16个，以处理大量的传入请求。有关Camel线程模型和线程池的更多信息，请参见第10章。
     *
     *         默认情况下，组播将持续发送消息到目的地，哪怕有一个失败了。然而，在您的应用程序中，如果一个目的地失败，
     *         您可能会考虑整个过程失败。在这种情况下，你做什么？
     *
     *       ★ 停止异常的组播（STOPPING THE MULTICAST ON EXCEPTION）
     *
     *
     *
     *
     *
     *
     */
}
