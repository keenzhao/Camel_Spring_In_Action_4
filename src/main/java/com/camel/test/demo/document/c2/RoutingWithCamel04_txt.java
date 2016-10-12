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
     * 2.5.3 使用组播（Using multicasting）
     *       在企业应用中，你经常需要发送一个消息的拷贝到几个不同的目的地来进行处理。当目的地列表提前知道并且是静态的时候，
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
     *              提示：为了处理在一个组播调用服务的响应，一个聚合器被使用。在第8章有关于聚合器更多的内容。
     *
     *       默认情况下，组播是顺序发送消息副本。在前面的示例中，一个消息先被发送到会计队列，然后发送到生产队列。但是如果
     *       你想并行发送它们呢？
     *
     *       ★ 并行组播（PARALLEL MULTICASTING）
     *          使用组播并行发送消息只涉及一个额外的DSL方法：parallelProcessing。扩展下前面的组播的例子，你可以添加
     *          parallelProcessing方法如下：
     *
     *              from("jms:xmlOrders")
     *                  .multicast().parallelProcessing()
     *                  .to("jms:accounting", "jms:production");
     *
     *         这将引起组播按并行方式分发消息到目的地。
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
     *          Rider汽车配件公司的组播解决方案存在一个问题：如果订单发送到会计队列失败，它可能需要较长的时间追查到生产
     *          订单和客户账单。要解决这个问题，你可以利用多播的stopOnException功能。当启用时，此功能将在第一个异常捕获上
     *          停止组播，因此你可以采取任何必要的行动。
     *          要启用此功能，使用stopOnException方法如下：
     *
     *                  from("jms:xmlOrders")
     *                      .multicast().stopOnException()
     *                      .parallelProcessing().executorService(executor)
     *                      .to("jms:accounting", "jms:production");
     *
     *          要处理从这个路由返回的异常，您将需要使用Camel的错误处理（error-handling）功能，这是在第5章中详细描述。
     *          使用Spring DSL的时候，这条路由看起来略有不同：
     *
     *                  <route>
     *                      <from uri="jms:xmlOrders"/>
     *                      <multicast stopOnException="true" parallelProcessing="true" executorServiceRef="executor">
     *                          <to uri="jms:accounting"/>
     *                          <to uri="jms:production"/>
     *                      </multicast>
     *                  </route>
     *
     *          主要的区别是用于设置标志的方法，像在Java DSL中的stopOnException方法现在是multicast元素上的属性。此外，
     *          executor service作为一个引用指向像下面这个Spring bean：
     *
     *                  <bean id="executor" class="java.util.concurrent.Executors"
     *                          factory-method="newFixedThreadPool">
     *                      <constructor-arg index="0" value="16"/>
     *                  </bean>
     *
     *          现在你知道了在Camel中如何组播消息了，但你可能会认为，这似乎是一个相当静态的解决方案，因为改变目的地意味
     *          着改变路由。让我们看看如何更动态地可以发送给多个收件人。
     *
     *
     * 2.5.4 使用收件人列表（Using recipient lists）
     *       在上一节中，你实现了一个新经理的建议并行了会计和生产队列，因此，订单可以更迅速地处理。Rider汽车零部件公司的
     *       顶级客户首先注意到这个方法的问题：现在所有的订单都直接进入生产，顶级客户没有得到高于规模较小的客户优先权。
     *       他们的订单需要更长的时间，他们正在失去商业机会。管理层建议立即回到旧的模式，但你提出一个简单的解决方案来解决
     *       问题：只有顶级客户订单被并行化，所有其他的订单必须首先去会计，从而不停滞生产。
     *
     *       该解决方案可以通过使用收件人列表EIP来实现。如图2.14所示，一个收件人列表首先检查传入的消息，然后根据消息内容
     *       生成预期的收件人的列表，并将消息发送给那些收件人。
     *       一个收件人通过一个endpoint URI来指定。注意，收件人列表与组播是不同的，因为收件人列表是动态的。
     *
     *       Camel提供了一个recipientList方法来实现收件人列表EIP。例如，下面这个路由将从一个名为recipients标头中获取
     *       收件人列表，每个收件人与下一个（收件人）都是通过逗号分割开的：
     *
     *              from("jms:xmlOrders")
     *                  .recipientList(header("recipients"));
     *
     *       这是有用的，如果你已经有了一些消息的信息，它们可以用来构建目标名称，你可以使用一个表达式来创建列表。为了给
     *       收件人列表提取有意义的endpoint URI，表达式必须是一个可迭代的结果。将工作的值是java.util.Collection,
     *       java.util.Iterator,Java arrays, org.w3c.dom.NodeList和如图所示的例子，一个用逗号分割多值的字符串。
     *       在Rider汽车零件公司的情形下，消息不包含该列表。你需要一些方法来确定信息是不是来自一个顶级客户。
     *       一个简单的解决方案可能是添加一个自定义的处理器来做这个：
     *
     *              from("jms:xmlOrders")
     *              .setHeader("customer", xpath("/order/@customer"))
     *              .process(new Processor() {
     *                      public void process(Exchange exchange) throws Exception {
     *                          String recipients = "jms:accounting";
     *                          String customer = exchange.getIn().getHeader("customer", String.class);
     *                          if (customer.equals("honda")) {
     *                              recipients += ",jms:production";
     *                          }
     *                          exchange.getIn().setHeader("recipients", recipients);
     *                      }
     *              })
     *              .recipientList(header("recipients"));
     *
     *       处理器现在只有当客户是黄金支持级别的才设置recipients标头为"jms:accounting, jms:production"。
     *       这里对黄金支持级别的检查被大大地简化了，理论上你只要查询数据库完成这个检查。任何其他订单只会被路由到会计，在
     *       检查完成之后发送到生产。
     *
     *       这条路由的Spring DSL版本非常类似下面的布局：
     *              <route>
     *                  <from uri="jms:xmlOrders" />
     *                  <setHeader headerName="customer">
     *                      <xpath>/order/@customer</xpath>
     *                  </setHeader>
     *                  <process ref="calculateRecipients" />
     *                  <recipientList>
     *                      <header>recipients</header>
     *                  </recipientList>
     *              </route>
     *
     *       正如你可能已经预期，在java DSL路由指定匿名处理器必须分离成为一个被命名的处理器。该处理器然后加载Spring bean
     *       并给定名称为calculateRecipients，然后在process元素中使用ref属性来引用。
     *
     *       收件人不被嵌入在消息中作为标头或实体的一部分是常见的，这种情况下使用一个自定义处理器是完美的功能，但是不友好。
     *       在使用一个自定义的处理器时，您必须直接操作该exchange和消息的APIs。幸运的是，Camel支持一个更好的方式来实现收
     *       件人列表。
     *
     *       ★ 收件人列表注解（RECIPIENT LIST ANNOTATION）
     *          你可以添加一个@RecipientList注解在一个普通的java类（一个java bean）的方法上，而不是在DSL中使用
     *          recipientList方法。这个注解告诉Camel被注解的方法应该使用从exchange中生成的收件人列表。然而，如果该类被
     *          用于与Camel的bean集成的话，此行为只被调用。
     *
     *          例如，再一个大大简化的路由中使用一个注解bean更换您在上一节中使用的自定义处理器的结果：
     *
     *                  from("jms:xmlOrders").bean(RecipientListBean.class);
     *
     *          现在所有的计算接收者和发送消息的业务逻辑都会在类RecipientListBean中捕获，像这样：
     *
     *                  public class RecipientListBean {
     *                      @RecipientList
     *                      public String[] route(@XPath("/order/@customer") String customer) {
     *                          if (isGoldCustomer(customer)) {
     *                              return new String[] {"jms:accounting", "jms:production"};
     *                          } else {
     *                              return new String[] {"jms:accounting"};
     *                          }
     *                      }
     *
     *                      private boolean isGoldCustomer(String customer) {
     *                          return customer.equals("honda");
     *                      }
     *
     *                  }
     *
     *          注意，返回bean的类型是想得到的收件人的列表。Camel将采用此列表，并发送一份消息副本到列表中的每一个目的地。
     *
     *          这种（注解）方法实现收件人列表的一个很好的事情是，它被彻底地从路由中分离出来，这使得它更易阅读。您还可以
     *          访问Camel的bean-binding注解，它允许您使用表达式从消息中提取数据，所以你不必手动探究exchange。本示例使用
     *          @XPath bean-binding注解，在实体中抓取order元素中的customer属性，在第4章将涵盖这些关于使用bean的全部注解。
     *
     *          要运行这个例子，到书中的源代码chapter2/recipientlist目录并运行这个命令：
     *
     *                  mvn clean compile exec:java -Dexec.mainClass=camelinaction.OrderRouterWithRecipientListBean
     *
     *          这将在命令行中输出下面内容：
     *
     *                  Accounting received order: message1.xml
     *                  Production received order: message1.xml
     *                  Accounting received order: message2.xml
     *
     *          你为什么得到这个输出？你在src/data目录下有以下两个订单：
     *
     *              ■ message1.xml
     *                  <?xml version="1.0" encoding="UTF-8"?>
     *                  <order name="motor" amount="1000" customer="honda"/>
     *              ■ message2.xml
     *                  <?xml version="1.0" encoding="UTF-8"?>
     *                  <order name="motor" amount="2" customer="joe's bikes"/>
     *
     *          第一个消息是来自一个黄金客户，依据Rider汽车零件公司的规则，所以它被路由到会计和生产。第二个订单来自
     *          一个较小的客户，所以它送到会计那验证客户的信誉。
     *
     *          这个系统现在缺乏一种方法来检查这些在路由中流动的消息，而不是等待直到它们到达路由的终点。让你给我们来看下
     *          侦听是如何能帮助我们（来解决这个问题)的。
     *
     *
     * 2.5.5 使用侦听方法（Using the wireTap method）
     *       通常在企业应用程序中，对于检查在系统中流动的消息它是有用的和必要的。例如，当一个订单失败时，您需要一个方法
     *       来查看接收到的消息以确定故障的原因。
     *
     *       正如你以前所做的那样，您可以使用一个简单的处理器，将传入消息的信息输出到控制台或将其追加到一个文件中。这里是
     *       一个将消息正文输出到控制台的处理器，：
     *
     *              from("jms:incomingOrders")
     *              .process(new Processor() {
     *                  public void process(Exchange exchange) throws Exception {
     *                      System.out.println("Received order: " + exchange.getIn().getBody());
     *                  }
     *              })
     *              ...
     *
     *       用于调试的目的这没什么问题，但在生产环境使用的话它是一个很差的解决方案用。如果你想要消息headers，exchange属性，
     *       或是在消息exchange中的其他数据呢？理想情况下，您可以复制整个传入的exchange，并将其发送到另一个通道去审计。
     *       如图2.15所示，侦听EIP定义了这样一个解决方案。
     *
     *       通过在Java DSL中使用wireTap方法，你可发一份exchange的拷贝到一个次要的的目的地，而不影响路由的其他的行为：
     *
     *              from("jms:incomingOrders")
     *              .wireTap("jms:orderAudit")
     *              .choice()
     *                  .when(header("CamelFileName").endsWith(".xml"))
     *                      .to("jms:xmlOrders")
     *                  .when(header("CamelFileName").regex("^.*(csv|csl)$"))
     *                      .to("jms:csvOrders")
     *                  .otherwise()
     *                      .to("jms:badOrders");
     *
     *       前面的代码发送一份exchange的拷贝到orderAudit队列，原始的exchange继续穿越路由，就好像你根本没有用过
     *       侦听一样。Camel不等待来自侦听的响应，因为侦听设置消息交换模式（MEP）为InOnly。这意味着消息将使用即发即弃
     *       的方式被发送 --- 它不会等待一个回应。
     *
     *       在Spring DSL中，你一样可以容易地配置一个侦听：
     *
     *              <route>
     *                  <from uri="jms:incomingOrders"/>
     *                  <wireTap uri="jms:orderAudit"/>
     *                  ..
     *
     *       你能用一个侦听到的消息做什么？在这一点上可以做一些事情：
     *          ■ 你可以像你之前做的那样打印信息到控制台。对于简单调试的目的这是有用的。
     *          ■ 你可以保存信息到在持久性存储中（在一个文件或数据库中）为以后检索所用。
     *
     *       侦听是一个非常有用的监控工具，但它留下了大部分的工作给你去做。
     *       我们将在第12章讨论一些Camel更强大的跟踪和审计工具。
     *
     *
     *
     *
     *
     */
}
