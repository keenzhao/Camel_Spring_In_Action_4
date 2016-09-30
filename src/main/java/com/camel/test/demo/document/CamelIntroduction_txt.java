package com.camel.test.demo.document;

/**
 * 粗糙翻译一下，后续调整，感兴趣同学，自行阅读英文原版《Camel in action》
 *
 * Created by keen.zhao on 2016/10/1.
 */
public class CamelIntroduction_txt {
    /**
     * 1.1、介绍Camel
     *        Camel是一个整合框架，其目的是使你的所有项目更高效有趣，Camel项目始于2007年，目前已是
     *    一个成熟的开源项目。Camel关注在简易集成，随着后面的内容你将会领略到Camel的魅力。
     *
     * 1.1.1、Camel是什么？
     *        Camel框架的核心是一个路由引擎，更精确的说应该是一个路由引擎构建器（Route Engine Builder）
     *    它允许你定义自己的路由规则，决定接受那些消息，做出决定如何处理，发送这些消息给其他目标。Camel
     *    用这种集成语言允许你定义复杂的路由规则。
     *
     *        Camel的基本原则之一是不会假设任何你需要处理的数据，这一点很重要，因为它给开发者一个集成
     *    任何系统的机会，不需要将你的数据转换为另外的一种公认格式。
     *
     *        Camel提供了高水平的抽象，它允许你根据相同的API协议或者系统的数据类型集成各种各样的系统。
     *    Camel的组件提供了特殊实现的接口API，其目的是给不同的协议和数据类型服务。
     *
     *        Camel打破了传统模式，它支持80（目前多达160多种）多种不同的协议和数据类型，它的扩展性和
     *    模块性允许你实现你自己专有协议的无缝插件。这些体系结构的选择淘汰没有必要的转换，从而使camel
     *    更加的高效、易学。结果证明，Camel是适合嵌入到其它项目中的，因为它提供充足的处理能力。
     *    其它开源项目，如Apache ServiceMix和ActiveMQ已经使用Camel作为企业集成的一种处理方式。
     *
     *        我们应该提问Camel不是什么，Camel不是ESB，有人称camel是个轻量级的ESB，因为它支持路由、
     *    事务、监控、编制等。Camel没有一个容器或者一个可靠的消息总线，但它可以依赖如开源的ESB或
     *    ServiceMix,由于上述原因，我们把Camel称为超越一个ESB的集成框架。
     *
     *        理解Camel是什么，能够更好的看到它的主要功能特点。
     *
     * 1.1.2、为什么使用Camel？
     *        Camel为整合领域介绍了一些新奇的观点，这就是为什么它的的作者决定在第一领域创建Camel，代替正
     *     在使用已经存在的框架。，它的主要功能特点如此下：
     *              路由调解引擎
     *              企业集成模式（EIP）
     *              领域特定语言 Domain-Specific Language（DSL)
     *              丰富的组件库
     *              不关注路由的数据
     *              模块化和可插拔的体系结构
     *              POJO模型
     *              容易配置
     *              数据类型自动转换
     *              轻量核心
     *              测试套件
     *              充满活力的社区
     *
     *     ★ 路由和调解引擎
     *         Camel的核心特性是它的路由调解引擎，一个路由引擎可以根据路由规则来选择把消息转发到哪。
     *         在Camel的世界里，路由器的路由规则的配置要使用到EIP和DSL，接下来我们会详细介绍。
     *
     *     ★ 企业集成模式（EIP）
     *         尽管集成的问题多种多样，Gregor Hohpe 和Bobby Woolf注意到许多问题并且他们的解决方案是
     *         非常相似的。他们编写一本《Enterprise Integration Patterns》书，这本书对那些专业集成
     *         的人来说是必读的书。如果你没有读过它，建议你去读它。它将帮助你更快更容易地理解camel。
     *
     *         企业集成模式也称为EIPs，对我们是有帮助的，因为它不仅给我们提供了问题的解决方案，并且也帮
     *         我们定义和表达了问题的本身。模式具有已知的语义，这使得表达问题容易多了。使用模式语言和
     *         问题描述之间的区别类似于使用口语而不是使用手语。
     *
     *         Camel在很大程度上是基于EIPs的，尽管EIPs描述了集成问题的解决方案，同时也提供了通用的
     *         词汇列表，但这些词汇列表没有形成书面语言确定下来。Camel试图提供一种描述集成解决方案的
     *         语言来避免了这个问题。在EIPs的模式描述和camel的DSL语言之间几乎是一一对应的关系。
     *
     *     ★ 领域特定语言 Domain-Specific Language（DSL)
     *         Camel的DSL为集成领域做出了很大贡献，可能有一些其它的框架也提供了DSL的特性（比如可以例XML
     *         来定义路由规则），但不像Camel DSL基于自定义型语言。Camel之所以唯一也是因为它为常用的编程
     *         语言提供了DSL，例如Java、Scala、Groovy，同时它也提供XML的形式去描述路由规则。
     *         DSL的目的是让开发人员专注解决集成问题，而不是对某个编程语言的使用。虽然Camel大部分是用Java写的，
     *         但是它仍然支持多种语言混合编程。每项语言都有自己的优势，您可能想用不同的语言去做不同的事情。
     *         使用Camel可以让您在打造自己的方案时是自由的。
     *         下面是不同语言的DSL例子，它们做的都是同样的事情。
     *              ＊Java DSL
     *                    from("file:data/inbox").to("jms:queue:order");
     *
     *              ＊Spring DSL
     *                    <route>
     *                        <from uri="file:data/inbox"/>
     *                        <to uri="jms:queue:order"/>
     *                    </route>
     *
     *              ＊Scala DSL
     *                    from "file:data/inbox" -> "jms:queue:order"
     *
     *         这些示例都是真实可运行的代码，它们展示了把data/inbox下的文件转发到JMS Queue  order上竟此简单。
     *         这些都是在实际编程时使用DSL，您可以使用一些现在的工具来帮助您代码补全、编译错误提示等等。
     *
     *      ★ 丰富的组件库
     *         Camel提供了扩展包有80多种Component，这些Component可以让Camel连上不同的transport，
     *         或使用不同的API或识别不同的数据类型。
     *
     *      ★ 不关注路由的数据
     *         Camel可以路由各种形式的数据（消息），不局限于只有XML数据，这种自由意味着你不必把您的数据转换成
     *         某一规范格式来方便路由。
     *
     *      ★ 丰富的组件库
     *
     *
     *
     */
}
