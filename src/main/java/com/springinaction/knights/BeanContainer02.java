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
     *
     */



}
