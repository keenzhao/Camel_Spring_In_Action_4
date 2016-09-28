package com.springinaction.knights.config;

import com.springinaction.knights.BraveKnight;
import com.springinaction.knights.Knight;
import com.springinaction.knights.Quest;
import com.springinaction.knights.SlayDragonQuest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 与knight.xml等效的Java方式的bean装配配置类
 *
 * 不管使用基于XML的配置还是基于Java的配置，DI所带来的收益是等效的。
 * 尽管BraveKnight依赖于Quest，但它并不知道传递给它的是什么类型的Quest，也不知道这个Quest来自哪里。
 * 与之类似，SlayDragonQuest依赖与PrintStream，但在编码时它并不需要知道这个PrintStream是什么样子。
 * 只有Spring通过它的配置，能够了解这些组成部分是如何装配起来的。
 * 这样的话，就可以不改变所依赖的类的情况下，修改依赖关系。
 *
 * Created by keen.zhao on 2016/9/21.
 */
@Configuration
public class KnightConfig {

    @Bean
    public Quest quest(){
        return new SlayDragonQuest(System.out);
    }

    @Bean
    public Knight Knight(){
        return new BraveKnight(quest());
    }

}

/**
 * 现在这些展示不要关注细节，这些只是简单的例子，会专门详细讲述Spring的配置文件，和其他的Bean的装配方式。
 * 甚至包括一种让Spring自动发现bean并在在这些bean之间建立关联关系的方式。
 *
 * 现在已经声明了BraveKnight和Quest的关系，下面就只需要装载XML配置文件，并把应用启动起来！
 *
 */