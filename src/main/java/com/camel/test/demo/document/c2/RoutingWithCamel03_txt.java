package com.camel.test.demo.document.c2;

/**
 * 粗糙翻译一下，后续调整，感兴趣同学，自行阅读英文原版《Camel in action》
 *
 * Created by keen.zhao on 2016/10/6.
 */
public class RoutingWithCamel03_txt {
    /**
     * 2.4 使用Spring创建路由（Creating routes with Spring）
     *     Spring是目前最流行的控制反转(IOC)容器。核心框架允许你把应用中的bean装配在一起。装配是通过一个XML配置文件来完成的。
     *     在这一节，我们将快速介绍使用Spring创建一个应用，我们推荐Craig Walls编著的《Spring in Action》。
     *     然后我们继续向你展示Camel是如何使用Spring对Java DSL形成了一个替代或互补的解决方案。
     *
     *  2.4.1 Bean注入与Spring（Bean injection and Spring）
     *     使用Spring的Beans创建一个应用程序是非常简单的。所有你需要的是几个Java bean(classes)、Spring XML配置文件和一个
     *     ApplicationContext即可。ApplicationContext同CamelContext类似，因为它是Spring运行时刻的容器。让我们来看一个简单
     *     的例子。
     *     考虑一个应用，打印输出一个后面跟着用户名的问候语。在这个应用中，你不想问候语被硬编码，因此你可以使用接口来打破这种
     *     依赖。考虑一下下面的这个接口：
     *
     *               public interface Greeter {
     *                  public String sayHello();
     *              }
     *
     *     这个接口被下面这些类实现:
     *
     *              public class EnglishGreeter implements Greeter {
     *                  public String sayHello() {
     *                      return "Hello " + System.getProperty("user.name");
     *                  }
     *              }
     *
     *              public class DanishGreeter implements Greeter {
     *                  public String sayHello() {
     *                      return "Davs " + System.getProperty("user.name");
     *                  }
     *              }
     *
     * 你现在可以像下面这样创建一个欢迎者应用：
     *
     *              public class GreetMeBean {
     *
     *                  private Greeter greeter;
     *
     *                  public void setGreeter(Greeter greeter) {
     *                      this.greeter = greeter;
     *                  }
     *
     *                  public void execute() {
     *                      System.out.println(greeter.sayHello());
     *                  }
     *              }
     *
     * 此应用将根据你的配置会输出一个不同的问候语。使用Spring配置这个应用，你应该像下面这样做：
     *      <beans xmlns="http://www.springframework.org/schema/beans"
     *             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
     *                   xsi:schemaLocation="
     *                       http://www.springframework.org/schema/beans
     *                       http://www.springframework.org/schema/beans/spring-beans-3.0.xsd">
     *
     *          <bean id="myGreeter" class="com.camel.test.demo.example.c2.EnglishGreeter"/>
     *
     *          <bean id="greetMeBean" class="com.camel.test.demo.example.c2.GreetMeBean">
     *              <property name="greeter" ref="myGreeter"/>
     *          </bean>
     *
     *      </beans>
     *
     * 这个XML文件通知Spring做一下事情：
     *      1、创建一个名为myGreeter的EnglishGreeter的实例；
     *      2、创建一个名为greetMeBean的GreetMeBean的实例；
     *      3、设置GreetMeBean的名为greeter属性的引用到名为myGreeter的bean。
     *
     * 这个bean配置过程称为装配。
     *
     * 为了加载这个XML文件到Spring，你可以使用ClassPathXmlApplicationContext，它是由Spring框架提供的ApplicationContext
     * 的具体实现。这个类从类路径中指定的位置加载Spring XML文件。
     *
     * 这里是GreetMeBean的最终版本:
     *          public class GreetMeBean {
     *              ...
     *              public static void main(String[] args) {
     *                      ApplicationContext context =
     *                          new ClassPathXmlApplicationContext("beans.xml");
     *                      GreetMeBean bean = (GreetMeBean) context.getBean("greetMeBean");
     *                      bean.execute();
     *              }
     *         }
     *
     * 在这里你实例化ClassPathXmlApplicationContext加载你先前在bean.xml文件中看到的bean定义。你可以在context上调用
     * getBean方法在Spring的注册表中用greetMeBean的ID来查找bean。所有在这个文件中定义的bean都以这种方式访问。
     *
     * 这个例子看起来很简单，但它应该给了你关于Spring是什么的理解，更一般的意义上讲实际上是一个IOC容器。
     *
     * 那么Camel是如何与它整合的呢？从本质上说，如果Camel当做是另外一种bean，也可以被配置的。比如，在2.2节，你配置JMS
     * 组件连接到ActiveMQ broker，除此之外你能用bean术语使用Spring做这个，像下面这样：
     *
     *          <bean id="jms" class="org.apache.camel.component.jms.JmsComponent">
     *              <property name="connectionFactory">
     *                  <bean class="org.apache.activemq.ActiveMQConnectionFactory">
     *                      <property name="brokerURL" value="vm://localhost" />
     *                   </bean>
     *              </property>
     *          </bean>
     *
     * 在这种情况下，Camel知道寻找类型为org.apache.camel.Component的bean并将它们自动加入到CamelContext中---在2.2.2节中
     * 你手动做的任务。
     *
     * 但是在Spring中CamelContext的定义在那？在Spring的XML文件内，Camel利用的Spring的扩展机制提供了Camel自定义的XML语法。
     * 在Spring中加载CamelContext，你可以如下去做：
     *
     *          <beans xmlns="http://www.springframework.org/schema/beans"
     *                  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
     *                  xsi:schemaLocation="
     *                      http://www.springframework.org/schema/beans
     *                      http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
     *                      http://camel.apache.org/schema/spring
     *                      http://camel.apache.org/schema/spring/camel-spring.xsd">
     *              ...
     *              <camelContext xmlns="http://camel.apache.org/schema/spring"/>
     *          </beans>
     *
     * 这样自动启动一个SpringCamelContext，它是一个你为Java DSL使用的DefaultCamelContext的子类。还要注意，在XML文件中
     * 你必须包括XML架构定义 http://camel.apache.org/schema/spring/camel-spring.xsd --这是需要导入自定义XML元素。
     * 这段独立的代码不会为了做更多的，你需要告诉Camel什么路由要使用，就像你使用Java DSL时做的那样。下面的代码使用Spring
     * 产生相同的结果，像清单2.1中的代码。
     *
     *      <beans xmlns="http://www.springframework.org/schema/beans"
     *          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
     *          xsi:schemaLocation="
     *              http://www.springframework.org/schema/beans
     *              http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
     *              http://camel.apache.org/schema/spring
     *              http://camel.apache.org/schema/spring/camel-spring.xsd">
     *
     *         <bean id="jms" class="org.apache.camel.component.jms.JmsComponent">
     *              <property name="connectionFactory">
     *                   <bean class="org.apache.activemq.ActiveMQConnectionFactory">
     *                      <property name="brokerURL" value="vm://localhost" />
     *                   </bean>
     *              </property>
     *         </bean>
     *
     *          <bean id="ftpToJmsRoute" class="com.camel.test.demo.example.c2.FtpToJMSRoute"/>
     *
     *          <camelContext xmlns="http://camel.apache.org/schema/spring">
     *              <routeBuilder ref="ftpToJmsRoute"/>
     *          </camelContext>
     *
     *      </beans>
     *
     * 你可能已经注意到，我们正在涉及的com.camel.test.demo.example.c2.FtpToJMSRoute类像RouteBuilder一样，
     * 为了重现在清单2.1的Java DSL的例子，你必须将匿名RouteBuilder类分离出来放到拥有名字的类中。
     *  FtpToJMSRoute看上去像这样：
     *
     *      public class FtpToJMSRoute extends RouteBuilder {
     *          public void configure() {
     *              from("ftp://rider.com" +
     *                   "/orders?username=rider&password=secret")
     *                   .to("jms:incomingOrders");
     *          }
     *      }
     *
     * 现在你知道了Spring的基本知识和如何在它内部加载Camel了。我们可以进一步去看如何用纯XML来写路由规则--
     * -无需java DSL。
     *
     *
     * 2.4.2 Spring DSL（The Spring DSL）
     *       我们已经看到Camel与Spring的集成是足够的的，但它没有充分利用Spring的不使用代码配置应用的一套方法。
     *       为了使用Spring XML完整地创建应用的反转控制，Camel提供了自定义的XML扩展，我们称为Spring DSL。
     *       Spring DSL允许你做任何Java DSL能做的事情。
     *       让我们继续用Rider汽车配件公司的例子来在清单2.2中展示，但这次你要在纯粹的XML中用RouteBuilder指定
     *       路由规则定义。下面的这个Spring XML将做这些。
     *          <beans xmlns="http://www.springframework.org/schema/beans"
     *              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
     *              xsi:schemaLocation="
     *                  http://www.springframework.org/schema/beans
     *                  http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
     *                  http://camel.apache.org/schema/spring
     *                  http://camel.apache.org/schema/spring/camel-spring.xsd">
     *          <bean id="jms" class="org.apache.camel.component.jms.JmsComponent">
     *              <property name="connectionFactory">
     *                  <bean class="org.apache.activemq.ActiveMQConnectionFactory">
     *                      <property name="brokerURL" value="vm://localhost" />
     *                  </bean>
     *              </property>
     *          </bean>
     *
     *          <camelContext xmlns="http://camel.apache.org/schema/spring">
     *              <route>
     *                  <from
     *                      uri="ftp://rider.com/orders?username=rider&password=secret"/>
     *                  <to uri="jms:incomingOrders"/>
     *              </route>
     *          </camelContext>
     *      </beans>
     *
     * 在这个清单中，在CamelContext元素下，你用route元素替代了RouteBuilder。在route元素中，你指定路由使用的元素名称
     * 同Java DSL RouteBuilder中使用的是类似的。这个清单是功能上等同于清单2.1中的Java DSL版本以及清单2.2中Spring加Java
     * DSL的组合。在本书的源代码中，我们改变了from方法替代消费来自本地目录的消息。新的路由看起来像这样：
     *
     *      <route>
     *          <from uri="file:src/data?noop=true"/>
     *          <to uri="jms:incomingOrders"/>
     *      </route>
     *
     * file端点将从相关的src/data目录加载订单文件。noop属性配置了端点在处理后留下文件，这个选项对测试中特别有用。在第7章，
     * 您还将看到Camel如何允许您在处理后删除或移动文件。
     * 这个路由还没有显示任何有趣的东西。您需要添加一个额外的处理步骤来测试。
     *
     * ★ 加入一个处理器（ADDING A PROCESSOR）
     *    添加额外的处理步骤是简单的，就像在Java DSL中一样。在这里你可以添加一个自定义的处理器，就像是你在第2.3.2节做的一样。
     *    因为在Spring XML中你不能指向一个匿名类，你需要将匿名处理器类提取出来放到下面这个类中：
     *
     *          public class DownloadLogger implements Processor {
     *              public void process(Exchange exchange) throws Exception {
     *                  System.out.println("We just downloaded: "
     *                  + exchange.getIn().getHeader("CamelFileName"));
     *              }
     *          }
     *
     *    你可以在你的Spring DSL中像下面这样来使用：
     *
     *          <bean id="downloadLogger" class="camelinaction.DownloadLogger"/>
     *
     *          <camelContext xmlns="http://camel.apache.org/schema/spring">
     *              <route>
     *                  <from uri="file:src/data?noop=true"/>
     *                  <process ref="downloadLogger"/>
     *                  <to uri="jms:incomingOrders"/>
     *              </route>
     *          </camelContext>
     *
     *    现在你可以准备运行这个例子了。（译者自己的例子:com.camel.test.demo.example.c2.CamelSpringDSL）
     *
     *    要是你想从incomingOrders队列消费之后打印此信息又怎样？要做到这一点，你需要创建另一个路由。
     *
     * ★ 使用多路由（USING MULTIPLE ROUTES）
     *    你可能还记得在Java DSL中每个Java语句都是以from开始创建一个新的路由。使用Spring DSL你也能创建多条路由。
     *    要做到这点，仅仅需要在CamelContext元素内添加一个route元素即可。例如，把DownloadLogger bean移到第二个
     *    route，在得到订单发送到incomingOrders之后：
     *          <camelContext xmlns="http://camel.apache.org/schema/spring">
     *              <route>
     *                  <from uri="file:src/data?noop=true"/>
     *                  <to uri="jms:incomingOrders"/>
     *              </route>
     *              <route>
     *                  <from uri="jms:incomingOrders"/>
     *                  <process ref="downloadLogger"/>
     *              </route>
     *          </camelContext>
     *
     * 现在在第二个route中消费从incomingOrders队列中的消息。因此，在订单发送到队列后下载的消息将被打印。
     *
     * ★ 选择那一种DSL来使用（CHOOSING WHICH DSL TO USE）
     *    在一个特定的场景中那一种DSL是最好用的是Camel用户的常见问题，这主要归结于个人的偏好。如果你喜欢用Spring或者
     *    在XML定义一些东西的话，你可能更喜欢纯Spring的方法。如果你想用Java动手做，也许纯java DSL方法更适合你。
     *
     *    在任何一种情况下，你都差不多可以访问所有的Camel的功能。Java DSL是一种一个稍微更丰富的语言，因为你对java语言
     *    的全部力量了如指掌。另外，一些java DSL的特征，像值生成器（创建表达式和谓词）。在Spring DSL中不能用，在另一
     *    方面，使用Spring让你获得极佳的对象构造能力以及常用的Spring事物抽象概念，像数据库连接和JMS集成。
     *
     *    一个常见的妥协（我最喜欢的用法）是Spring和Java DSL两者都用，这是我们下面要涉及的其中一个话题。
     *
     *
     * 2.4.3 使用Camel和Spring（Using Camel and Spring）
     *     无论你用Java或者Spring DSL写你的路由，在Spring容器中运行Camel给你更多的好处。其中一个，如果你使用Spring
     *     的DSL，当你想改变你的路由规则时候你不要编译任何代码，此外，您还可以访问到数据库连接器、事务支持和更多的
     *     Spring产品组合。
     *     让我们仔细看看Camel提供其他那些Spring的集成。
     *
     *     ★ 查找路由生成器（FINDING ROUTE BUILDERS）
     *        使用Spring CamelContext作为运行环境，以及使用Java DSL开发route是使用Camel最佳的方式。事实上，它是Camel
     *        最常见的用法。你之前看到的，你可以明确地告诉Spring CamelContext要加载什么路由生成器。您可以通过使用
     *        RouterBuilder元素做这个：
     *
     *              <camelContext xmlns="http://camel.apache.org/schema/spring">
     *                  <routeBuilder ref="ftpToJmsRoute"/>
     *              </camelContext>
     *
     *       这是一个明确的结果，加载什么到Camel的一个简洁明了的定义。
     *       不过有时候你可能需要多一点的动态。这是packageScan和contextScan元素加入的地方：
     *
     *              <camelContext xmlns="http://camel.apache.org/schema/spring">
     *                  <packageScan>
     *                      <package>camelinaction.routes</package>
     *                  </packageScan>
     *              </camelContext>
     *
     *        packageScan元素将会加载在camelinaction.routes包及其所有的子包中找到的全部的RouteBuilder类。
     *        你甚至可以挑选包括哪些路由生成器：
     *
     *              <camelContext xmlns="http://camel.apache.org/schema/spring">
     *                  <packageScan>
     *                      <package>camelinaction.routes</package>
     *                      <excludes>**.*Test*</excludes>
     *                      <includes>**.*</includes>
     *                  </packageScan>
     *              </camelContext>
     *
     *        在这种情况下，你将在camelinaction.routes包中加载所有的路由生成器，除了类名带了"Test"那些。
     *        匹配的语法类似Apache Ant的文件模式匹配的用法。
     *
     *        contextScan元素利用Spring组件扫描特性的优势，
     *        加载任何被标注了@org.springframework.stereotype.Component注解的Camel的路由生成器，
     *        让我们使用这个注解来修改FtpToJMSRoute类：
     *
     *              @Component
     *              public class FtpToJMSRoute extends SpringRouteBuilder {
     *                  public void configure() {
     *                      from("ftp://rider.com" +
     *                      "/orders?username=rider&password=secret")
     *                      .to("jms:incomingOrders");
     *                  }
     *              }
     *
     *        注意，这个版本使用org.apache.camel.spring.springroutebuilder类，这是包含额外的Spring实用功能的
     *        RouteBuilder的一个扩展类。您现在可以在您的Spring XML文件中使用下面的配置来启用组件扫描：
     *
     *              <context:component-scan base-package="camelinaction.routes"/>
     *
     *              <camelContext xmlns="http://camel.apache.org/schema/spring">
     *                  <contextScan/>
     *              </camelContext>
     *
     *        这将加载在camelinaction.routes包中所有的有@Component注解的Spring的路由生成器（类）。
     *        在底层，Camel的一些组件，像JMS组件，是建立在Spring抽象库顶部的。在Spring中配置这些组件容易是有道理的。
     *
     *     ★ 配置组件和端点（CONFIGURING COMPONENTS AND ENDPOINTS）
     *
     *
     */



}
