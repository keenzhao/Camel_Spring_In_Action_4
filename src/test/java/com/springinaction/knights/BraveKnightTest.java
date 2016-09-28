package com.springinaction.knights;

import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * 你可以使用mock框架Mockito去创建一个Quest接口的mock实现。通过这个mock对象，然后可以创建一个新的
 * BraveKnight实例，并通过构造器注入这个mockQuest。当调用embarkOnQuest()方法时，你可以要求Mockito
 * 框架验证Quest的mock实现的embark()方法仅仅被调用一次。
 *
 * Created by keen.zhao on 2016/9/21.
 */
public class BraveKnightTest {

    @Test
    public void knightShouldEmbarkOnQuest() throws Exception {
        Quest mockQuest = mock(Quest.class);  //创建mock Quest
        BraveKnight knight = new BraveKnight(mockQuest);  //注入mock Quest
        knight.embarkOnQuest();
        verify(mockQuest, times(1)).embark();
    }

}