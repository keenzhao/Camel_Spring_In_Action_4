package com.springinaction.knights;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 加载包含Knight的Spring上下文的类
 *
 * 这里的main()方法基于knight.xml文件创建了Spring应用上下文。随后它调用该应用上下文获取一个ID为knight的bean。
 * 得到knight对象的引用后，只需简单调用embarkOnQuest()方法就可以执行所赋予的探险任务了。
 * 注意，这个类完全不知道我们的英雄骑士接受哪种探险任务，而且完全没有意识到这是由BraveKnight来执行的。
 * 只有knight.xml文件知道那个骑士执行哪种探险任务。
 *
 * 对依赖注入的学习参考书见Dhanji R.Prasanna的《dependency injection》，该书覆盖了依赖注入的所有内容。
 *
 * Created by keen.zhao on 2016/9/22.
 */
public class KnightMain {

    public static void main(String[] args) throws Exception {

        //加载Spring上下文
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("knight_aop.xml");
        Knight knight=context.getBean("knight",Knight.class); //加入切面声明的配置
        //Knight knight=context.getBean("knight_di",Knight.class);
        knight.embarkOnQuest();
        context.close();


    }
}
