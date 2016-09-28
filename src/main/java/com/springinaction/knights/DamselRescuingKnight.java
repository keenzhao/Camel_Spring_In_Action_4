package com.springinaction.knights;

/**
 * 在了解DI之前先看下紧耦合的例子
 *
 * 任何一个有实际意义的应用，都会由两个或者更多的类组成，这些类相互之间进行协作来完成特定的业务逻辑。
 * 传统的做法，每个对象负责管理与自己相互协作的对象（即它依赖的对象）的引用，这将导致高耦合和难以测试。
 * 如下面这个类DamselRescuingKnight，永远只能执行类RescueDamselQuest的任务：
 *
 * Created by keen.zhao on 2016/9/20.
 */
public class DamselRescuingKnight implements Knight {

    private RescueDamselQuest quest;

    public DamselRescuingKnight() {

        quest = new RescueDamselQuest();   // 与RescueDamselQuest紧耦合
    }

    @Override
    public void embarkOnQuest() {

        quest.embark();
    }
}
/**
 * 可以看到，DamselRescuingKnight在它的构造函数中自行创建了RescueDamselQuest。
 * 这使得DamselRescuingKnight紧密地和RescueDamselQuest耦合到一起，因此极大地限制了这个骑士的能力。
 * 这个骑士类，如果一个少女需要救援，召之即来，但要杀掉一个恶龙或者让圆桌滚起来，这个骑士就不行了，这太可笑了。
 *
 * 更糟糕的是，这个骑士类编写单元测试将出奇地困难。
 * 在这样一个测试中：
 * 你必须保证当骑士的embarkOnQuest()方法被调用时，探险的embark()方法也要被调用，但没有一个简明的方式能实现
 * 这点。很遗憾，DamselRescuingKnight将无法进行测试。
 *
 */
