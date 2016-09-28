package com.springinaction.knights;

/**
 *
 * Created by keen.zhao on 2016/9/27.
 */
public class BeanContainer01 {

    /**
     * 1.2 容纳你的Bean
     *
     * 在基于Spring的应用中，你的应用对象生存于Spring容器(container)中，Spring容器负责创建对象，装配它们，
     * 配置它们并管理它们的整个生命周期，从生存到死亡（在这里，可能就是new到finalize）。
     *
     * 首先最重要的是了解容纳对象的容器。理解容器将有助于理解对象是如何被管理的。
     * 容器是Spring框架的核心，Spring容器使用DI管理构成应用的组件，它会创建相互协作的组件之间的关联。
     * 毫无疑问，这些对象更简单干净，更易于理解，更易于重用并且更易于进行单元测试。
     *
     * Spring容器并不是只有一个。
     * Spring自带了多个容器实现，可以归为两种不同的类型。
     *
     * 1）bean工厂（由org.springframework.beans.factory.BeanFactory接口定义）是最简单的容器，
     * 提供基本的DI支持。
     *
     * 2）应用上下文（由org.springframework.context.ApplicationContext接口定义）基于BeanFactory构建，并提供
     * 应用框架级别的服务，例如从属性文件解析文本信息以及发布应用事件给感兴趣的事件监听者。
     *
     * 虽然可以在bean工厂和应用上下文之间任选一种，但bean工厂对大多数应用来说往往太低级了，
     * 因此，应用上下文要比bean工厂更受欢迎。所以主要关注应用上下文。
     *
     * 1.2.1 使用应用上下文
     * Spring自带了多种类型的应用上下文。下面罗列的几个是经常遇到的。
     *
     *      AnnotationConfigApplicationContext：从一个或多个基于java的配置类加载Spring上下文。
     *
     *      AnnotationConfigWebApplicationContext：从一个或多个基于java的配置类加载Spring Web上下文。
     *
     *      ClassPathXmlApplicationContext：从类路径下的一个或多个XML配置文件中加载上下文定义，
     *                                      将应用上下文的定义文件作为类资源。
     *
     *      FileSystemXmlApplicationContext：从文件系统下的一个或多个XML配置文件中加载上下文定义
     *
     *      XmlWebApplicationContext：从Web应用下的一个或者多个XML配置文件中加载上下文定义。
     *
     * 在讨论基于Web的Spring应用时，再详细讨论AnnotationConfigWebApplicationContext和XmlWebApplicationContext。
     * 这里先简单使用FileSystemXmlApplicationContext从文件系统中加载应用上下文或者使用ClassPathXmlApplicationContext
     * 从类路径中加载应用上下文。
     *
     * 无论从文件系统还是类路径加载应用上下文，加bean加载到bean工厂的过程都是相似的。
     *
     * 例如，下面代码展示如何加载一个FileSystemXmlApplicationContext：
     *      ApplicationContext context=new FileSystemXmlApplicationContext("c:/knight.xml");
     * 类似地，你可以使用ClassPathXmlApplicationContext从应用的类路径下加载应用上下文：
     *      ApplicationContext context=new ClassPathXmlApplicationContext("knight.xml");
     *
     * 使用FileSystemXmlApplicationContext和使用ClassPathXmlApplicationContext的区别在于：
     * FileSystemXmlApplicationContext在指定的文件系统下查找knight.xml文件；
     * ClassPathXmlApplicationContext是在所有的类路径（包括JAR）下查找knight.xml文件。
     *
     * 如果想从java配置中加载应用上下文，那么可以使用AnnotationConfigApplicationContext：
     *      ApplicationContext context=new AnnotationConfigApplicationContext(
     *                                       com.springinaction.knights.config.KnightConfig.class);
     * 这里没有指定加载Spring应用上下文所需的XML文件，AnnotationConfigApplicationContext通过指定一个配置类加载bean。
     *
     * 应用上下文准备就绪后，我们就可以调用上下文的getBean()方法从Spring容器中获取bean。
     *
     */
}
