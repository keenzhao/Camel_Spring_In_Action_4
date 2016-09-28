package com.springinaction.knights;

import java.io.PrintStream;

/**
 * 应用切面，基于切面进行声明式编程
 * <p>
 * DI能够让相互协作的软件组件保持松散耦合，而面向切面编程（Aspect-oriented programming，AOP）
 * 允许你把遍布应用各处的功能分离出来形成可重用的组件。
 * <p>
 * 面向切面编程往往被定义为促使软件系统实现关注点的分离的一项技术。系统由许多不同组件组成，
 * 每个组件各负责一块特定功能。除了实现自身核心的功能之外，这些组件还经常承担着额外的职责。
 * 诸如日志、事务管理和安全这样的系统服务经常融入到自身具有核心业务逻辑的组件中去。
 * <p>
 * 如果将这些关注点分散到多个组件中去，你的代码会带来双重的复杂性。
 * 1、实现系统关注点功能的代码会重复出现在多个组件中。这意味着你要改变这些关注点的逻辑，必须修改各个模块中
 * 的相关实现。即使你把这些关注点抽象为一个独立的模块，其他模块只是调用它的方法，但方法的调用还是会重复
 * 出现在各个模块中。
 * 2、组件会因为那些与自身核心业务无关的代码而变得混乱。
 * 一个向地址簿增加地址条目的方法应该只关注如何添加地址，而不是应该关注它是不是安全的或者是否需要支持事务。
 * <p>
 * AOP能够使这些服务模块化，并以声明的方式将它们应用到它们需要影响的组件中去。所造成的结果就是这些组件会具有
 * 更高的内聚性并且会更加关注自身的业务。
 * 完全不需要了解涉及系统服务所带来复杂性，总之AOP能确保POJO的简单性。
 * <p>
 * 我们可以把切面想象为覆盖在很多组件之上的一个外壳。应用是由那些实现业务功能的模块组成的。
 * 借助AOP，可以使用各种功能层去包裹核心业务层。
 * 这些层以声明的方式灵活地应用到系统中，你的核心应用甚至不知道它们的存在。这是一个非常强大的理念。
 * <p>
 * 为了示范在Spring中如何应用切面，重回骑士的例子，并为它添加一个切面。
 * <p>
 * AOP应用：每个人都熟知骑士所做的事情，这是吟游诗人用诗歌记载了骑士的事迹并进行传唱。
 * 假设我们需要使用吟游诗人这个服务类来记载骑士的所有事迹，那么我们来设计类Minstrel
 * （吟游诗人是中世纪的音乐记录器）
 * <p>
 * Created by keen.zhao on 2016/9/22.
 */
public class Minstrel {
    private PrintStream stream;

    public Minstrel(PrintStream stream) {
        this.stream = stream;
    }

    public void singBeforeQuest() {
        stream.println("Fa la la,the knight is so brave!");
    }

    public void singAfterQuest() {
        stream.println("Tee hee hee,the brave knight did embark on a quest");
    }

}

/**
 * 正如上面的代码，Minstrel类是只有两个方法的简单类。在骑士执行每一个探险任务之前，singBeforeQuest()方法会被
 * 调用；在骑士完成探险之后，singAfterQuest()方法会被调用。在这两种情况下，Minstrel都会通过一个PrintStream类
 * 来歌颂骑士的事迹，这个类是通过构造器注入进来的。
 *
 * 这里来改造下BraveKnight类让它与Minstrel组合起来尝试一次。
 *
 */
