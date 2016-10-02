package com.camel.test.demo.document.c1;

/**
 * 粗糙翻译一下，后续调整，感兴趣同学，自行阅读英文原版《Camel in action》
 *
 * Created by keen.zhao on 2016/10/2.
 */
public class CamelIntroduction04_txt {

    /**
     * 1.4、Camel的体系结构
     *      现在让我们把注意力转移到Camel的体系结构上来。我们先看一下高水平的架构然后深入到具体的概念。
     *      在读完这段内容后，你应该领悟集成术语并为第二章节准备，那里我们将深入研究Camel的路由能力。
     *
     *  1.4.1 1万英尺高的建筑
     *      我们认为架构最好的视图首先从更高层次看起。图1.6显示了组成Camel体系结构主要概念的高层次的视图。
     *      图见illustration包下的camel1_6.png。
     *
     *      路由引擎使用路由作为消息路由的规范，Routes（路由）使用camel的DSL定义。Processors(处理器)用于
     *      在路由期间转换和操作消息并实现了所有的EIP模式，在DSL语言中都有对应的关键字。Components是添加
     *      连接到其他系统的扩展点，为了将这些（外部）系统暴露给Camel的其余部分，Components提供了endpoint
     *      （端点）接口。
     *      有了高层次的视图的方式，让我们仔细看看在图1.6中的个别概念。
     *
     * 1.4.2 Camel概念
     *      图1.6显示了许多新的概念，因此让我们花些时间一个一个理解它。我们从CamelContext开始，它是Camel的
     *      运行环境。
     *           ★ CamelContext（Camel上下文）
     *              从图1.6判断，你可能猜到了，CamelContext是一个分类容器。你可以理解为是Camel的运行环境系统，
     *              把所有的部件放在一起。
     *              图1.7把CamelContext最为显著的服务放在一起显示出来。从图1.7可以看到，有诸多的服务与Camel
     *              保持联系，这些都在表1.1中被描述。贯穿本书这些服务的每一个细节都将被讨论。让我们先看看路由
     *              和路由引擎。
     *
     *              表1.1 CamelContext提供的服务
     *              ====================================================================================
     *              Service（服务）                               Description（描述）
     *              ====================================================================================
     *              Components（部件）            包含所使用的组件。Camel能在运行中装载components，通过在类
     *                                            路径中自动发现，或者在OSGi容器中当一个新的Bundle（OSGi的
     *                                            模块化的物理单元，以jar文件的形式包含代码、资源、元数据）
     *                                            被激活的时候。第7章将讨论components的更多细节。
     *
     *              Endpoints（端点）             包含已创建的endpoints。
     *
     *              Routers（路由）               包含已添加的路由，第二章将涉及routers。
     *
     *              Type Converters（类型转换器）  包含已加载的类型转换器。Camel有一个机制，允许您手动或自动
     *                                            从一种类型转换到另一种类型，类型转化器在第3章涉及。
     *
     *              Data formats（数据格式）      包含已加载的数据格式。数据格式在第3章涉及。
     *
     *              Registry（注册表）            包含一个允许你查找bean的注册表。默认情形下是一个JNDI注册。
     *                                            如果你从Spring使用Camel，它将是Spring的ApplicationContext。
     *                                            如果在OSGi容器中使用Camel，它也可能是一个OSGi注册表。
     *
     *              Languages（语言）             包含已加载的语言。Camel允许你使用多种语言来创建表达式。
     *                                            当我们涉及DSL时可以一睹XPath language in action这本书。在附录A
     *                                            中有完整的Camel自身可用的简单表达式语言的参考。
     *
     *           ★ Routing Engine（路由引擎）
     *              Camel的引擎实际上是在底层移动消息的，引擎并不暴露给开发者的，但你应该意识到它的存在并且它做了所有
     *              确保消息正确路由的重活。
     *
     *           ★ Routes（路由）
     *
     *
     */

}
