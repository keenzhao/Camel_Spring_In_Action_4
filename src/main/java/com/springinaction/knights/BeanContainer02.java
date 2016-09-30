package com.springinaction.knights;

/**
 *
 * Created by keen.zhao on 2016/9/29.
 */
public class BeanContainer02 {
    /**
     * 1.2.2 bean的生命周期
     * 传统的java应用中，bean的声明周期很简单，使用java关键字new进行bean实例化，然后该bean就可以使用了。
     * 一旦该bean不在使用，则由java自动进行垃圾回收。
     *
     * 相比之下，Spring容器中的bean的声明周期就显得相对复杂多了。正确理解Spring bean的声明周期非常重要。
     * 因为你或许要利用Spring提供的扩展点来自定义bean的创建过程。
     * Spring的bean装载到Spring应用上下文中的一个典型的声明周期过程：
     *
     * 1、Spring对bean进行实例化；
     * 2、Spring将值和bean的引用注入到bean对应的属性中；
     * 3、如果bean实现了BeanNameAware接口，Spring将bean的ID传递给setBeanName()方法；
     * 4、如果bean实现了BeanFactoryAware接口，Spring将调用setBeanFactory()方法，将BeanFactory容器实例传入；
     * 5、如果bean实现了ApplicationContextAware接口，Spring将调用setApplicationContext()方法，将bean所在
     *    的应用上下文的引用传入进来；
     * 6、如果bean实现了BeanPostProcessor接口，Spring将调用它们的postProcessorBeforeInitialization()方法；
     * 7、如果bean实现了InitializingBean接口，Spring将调用它们的afterPropertiesSet()方法。类似地，
     *    如果bean使用init-method声明了初始化方法，该方法也会调用；
     * 8、如果bean实现了BeanPostProcessor接口，Spring将调用它们的postProcessorAfterInitialization()方法；
     * 9、此时，bean已经准备就绪，可以被应用程序使用了，它们一直驻留在应用上下文中，直到该应用上下文被销毁；
     * 10、如果bean实现了DisposableBean接口，Spring将调用它的destroy()接口方法。同样地，如果bean使用
     *     destroy-method声明了销毁方法，该方法也会被调用。
     *
     * 现在你了解了如何创建和加载一个Spring容器。但是一个空的容器并没有太大价值，在你把东西放进去之前，它什么
     * 也没有，为了从Spring的DI中受益，我们必须将应用对象装配近Spring容器。
     *
     * 1.3 Spring的体系结构
     * 在Spring框架之外还存在一个构建在核心框架之上的庞大生态圈，将Spring扩展到不同的领域，例如Web服务、REST、
     * 移动开发以及NoSQl。
     *
     * 1.3.1 Spring模块
     * 在Spring4的发行版中包括了20个不同的模块：
     *      spring-aop-4.0.0.RELEASE.jar
     *      spring-aspects-4.0.0.RELEASE.jar
     *      spring-beans-4.0.0.RELEASE.jar
     *      spring-context-4.0.0.RELEASE.jar
     *      spring-context-support-4.0.0.RELEASE.jar
     *      spring-core-4.0.0.RELEASE.jar
     *      spring-expression-4.0.0.RELEASE.jar
     *      spring-instrument-4.0.0.RELEASE.jar
     *      spring-instrument-tomcat-4.0.0.RELEASE.jar
     *      spring-jdbc-4.0.0.RELEASE.jar
     *      spring-jms-4.0.0.RELEASE.jar
     *      spring-messaging-4.0.0.RELEASE.jar
     *      spring-orm-4.0.0.RELEASE.jar
     *      spring-oxm-4.0.0.RELEASE.jar
     *      spring-test-4.0.0.RELEASE.jar
     *      spring-tx-4.0.0.RELEASE.jar
     *      spring-web-4.0.0.RELEASE.jar
     *      spring-webmvc-4.0.0.RELEASE.jar
     *      spring-webmvc-portlet-4.0.0.RELEASE.jar
     *      spring-websocket-4.0.0.RELEASE.jar
     *
     * 这些模块依据所属的功能可以划分为6类不同的功能：
     *  1）数据集成访问
     *     JDBC、Transaction、ORM、OXM、Messaging、JMS
     *  2）Web与远程调用
     *     Web、Web Servlet、Web Portlet、WebSocket
     *  3）面向切面编程
     *     AOP、Aspects
     *  4）Instrumentation
     *     Instrument、Instrument Tomcat
     *  5）Spring核心容器
     *     Beans、Core、Context、Expression、Context-support
     *  5）测试
     *     Test
     *
     *
     * 现在逐一浏览Spring的模块，看看它们是如何构建起Spring整体蓝图的。
     *
     * ★Spring核心容器
     *        容器是Spring框架最核心的部分，它管理着Spring应用中bean的创建、配置和管理。在该模块中，
     *    包括了Spring bean工厂，它为Spring提供了DI功能。基于bean工厂，还会发现有多种Spring上下文的实现，
     *    每一种都是提供了配置Spring的不同方式。
     *        除了bean工厂和应用上下文，该模块也提供了许多企业服务，例如E-mail、JNDI访问、EJB集成和调度。
     *        所有的Spring模块都构建于核心容器之上。当你配置应用时，其实你隐式地使用了这些类。贯穿全部，
     *    我们都会涉及到核心模块。后面会深入讨论Spring的DI。
     *
     * ★Spring的AOP模块
     *        在AOP模块中，Spring对面向切面编程提供了丰富的支持。这个模块是Spring应用系统中开发切面的基础。
     *    与DI一样，AOP可以帮助应用对象解耦。借助于AOP，可以将遍布系统的关注点（例如事务和安全）从它们所在应用
     *    的对象中解耦出来。
     *
     * ★数据集成和访问
     *        使用JDBC编写代码通常会导致大量的样板式代码，Spring的JDBC和DAO模块抽象了这些样板代码，使我们的
     *    数据库代码变得简单明了，还可以避免因为关闭数据库资源失败引发的问题。
     *        该模块在
     *
     */



}
