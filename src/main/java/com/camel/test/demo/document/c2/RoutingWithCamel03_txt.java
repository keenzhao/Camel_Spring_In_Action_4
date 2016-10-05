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
     *
     *
     */



}