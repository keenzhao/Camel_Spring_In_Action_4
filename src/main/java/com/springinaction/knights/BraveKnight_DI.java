package com.springinaction.knights;

/**
 * 这个修改后的类可以达到预期的效果。只要在Spring配置把Minstrel bean配置好并注入到BraveKnight_DI的构造器中。
 * 但是，这里有些不太对，管理吟游诗人真的是骑士的责任吗？
 * 在我看来，吟游诗人应该做它份内的事，根本不需要骑士命令它这么做。毕竟，用诗歌记载骑士的探险事迹，这是吟游诗人的职责。
 * 为什么骑士还需要提醒吟游诗人去做他份内的事呢？
 *
 * 此外，因为骑士需要知道吟游诗人，所以就必须把诗人注入到BraveKnight_DI中去，这不仅使BraveKnight_DI代码变复杂了，
 * 而且还让我们怀疑是否还需要一个不需要吟游诗人的骑士呢？如果Minstrel为null会发生什么呢？是否应该引入一个空值校验来覆盖
 * 该场景？
 *
 * 简单的BraveKnight_DI开始变得复杂，如果要应对没有吟游诗人的场景，那代码会变得复杂。但利用AOP，你可以声明吟游诗人、
 * 必须歌颂骑士的探险事迹，而骑士并不用直接访问minstrel的方法。
 *
 * 将Minstrel抽象成一个切面，所需要做的事就是在一个Spring的配置文件中声明它。
 * 具体声明见knight.xml的升级版本
 *
 * Created by keen.zhao on 2016/9/27.
 */
public class BraveKnight_DI implements Knight {

    private Quest quest;
    private Minstrel minstrel;

    public BraveKnight_DI(Quest quest, Minstrel minstrel) {   //Quest,Minstrel被注入进来
        this.quest = quest;
        this.minstrel = minstrel;
    }

    @Override
    public void embarkOnQuest() {
        minstrel.singBeforeQuest(); //Knight应该管理它的Minstrel吗？
        quest.embark();
        minstrel.singAfterQuest();
    }
}


