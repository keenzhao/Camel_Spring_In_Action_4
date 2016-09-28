package com.springinaction.knights;

/**
 * 耦合具有两面性(two-headed beast)
 * 一方面，紧耦合的代码难以测试、难以复用、难以理解，并且典型的表现出“打地鼠”的bug特性（修复一个bug，
 * 将会出现一个或者更多的bug）。
 * 另一方面，一定程度的耦合又是必须的 -- 完全没有耦合的代码什么也做不了。
 *
 * 为了完成有实际意义的功能，不同的类必须以适当的方式进行交互。
 * 总而言之，耦合是必须的，但应当被小心谨慎地管理。
 *
 * 通过DI，对象的依赖关系将由系统中负责协调各对象的第三方组件在创建对象的时候进行设定。
 * 对象无需自行创建或管理它们的依赖关系。即依赖关系被自动注入到需要它们的对象当中去。
 *
 * 为了展示这一点，看一下下面的BraveKnight骑士类，这个骑士类不但勇敢，而且能挑战任何形式的探险。
 *
 * Created by keen.zhao on 2016/9/20.
 */
public class BraveKnight implements Knight {

    private Quest quest;

    public BraveKnight(Quest quest) {   //Quest被注入进来
        this.quest = quest;
    }

    @Override
    public void embarkOnQuest() {
        quest.embark();
    }
}

/**
 * 不同与DamselRescuingKnight类，BraveKnight类没有自行创建探险任务，
 * 而是在构造的时候把探险任务作为构造器参数传入。
 * 这是依赖注入的方式之一，即构造器注入(constructor injection)
 *
 * 更重要的是：传入的探险类型是Quest，也就是所有探险任务都必须实现的一个接口。
 * 所以BraveKnight类能够响应RescueDamselQuest、SlayDragonQuest、MakeRoundTableRounderQuest等任意的Quest实现。
 *
 * 这里的要点是：BraveKnight没有与任何特定的Quest实现发生耦合。
 * 对它来说，被要求挑战的探险任务只要实现了Quest接口，那么具体是那种类型的探险就无关紧要了。
 * 这就是依赖注入带来的最大收益 -- 松耦合。
 *
 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * 如果一个对象只通过接口（而不是具体实现或初始化过程）来表明依赖关系，那么这种依赖就能够在对象毫不知情的情况下，
 * 用不同的具体实现进行替换。
 * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 *
 * 对依赖进行替换的一个常用方法就是在测试的时候使用Mock实现。我们无法充分地测试DamselRescuingKnight，因为
 * 它是紧耦合的；但可以轻松地测试BraveKnight，只需给它一个Quest的mock实现即可。
 *
 * 详细见BraveKnightTest测试类！
 *
 */
