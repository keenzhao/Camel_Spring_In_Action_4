package com.springinaction.knights;

import java.io.PrintStream;

/**
 * 杀死怪龙的探险任务类
 * <p>
 * 现在BraveKnight类可以接受你传递给它的任意一种Quest的实现，但该怎样把特定的Quest实现传给它呢？
 * 假设，希望BraveKnight所要进行的探险任务是杀死一只怪龙，那么下面代码就合适：
 * <p>
 * Created by keen.zhao on 2016/9/21.
 */
public class SlayDragonQuest implements Quest {

    private PrintStream stream;

    public SlayDragonQuest(PrintStream stream) {
        this.stream = stream;
    }



    @Override
    public void embark() {
        stream.println("Embarking on quest to slay dragon!");
    }
}

/**
 * 这里可以看到，SlayDragonQuest实现了Quest接口，这样它就适合注入到BraveKnight中去了。
 * 与其他的Java入门样例不同的是，SlayDragonQuest没有使用System.out.println(),而是在
 * 构造方法中请求一个更为通用的PrintStream交给SlayDragonQuest。
 *
 * 这里最大的问题是，我们该如何将SlayDragonQuest交给BraveKnight呢？
 * 又如何将PrintStream交给SlayDragonQuest呢？
 *
 * 创建应用组件之间协作的行为通常称为装配（wiring）。
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * Spring有多种装备bean的方式，采用XML是很常见的一种装配方式。
 * knight.xml是一个简单的Spring配置文件，该文件将BraveKnight、SlayDragonQuest和PrintStream装配在一起。
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 *
 */
