package com.springinaction.knights;

/**
 * 拯救少女Damsel的探险任务类
 * Created by keen.zhao on 2016/9/20.
 */
public class RescueDamselQuest implements Quest {

    @Override
    public void embark() {
        System.out.println("拯救少女Damsel成功！");
    }
}
