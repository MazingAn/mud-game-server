package com.mud.game.utils.overflow;

/*
 * 涉及到溢出处理的决策类
 * 溢出的常见情况有，玩家血量等不能超出最大血量，玩家的背包仓库最大堆叠溢出
 */
public class OverFlowDecision {

    public static OverFlowDesc make(int added, int current, int max) {
        /*根据当前值，增加数值和最大允许的数值返回溢出信息
        *溢出数flowNumber等于 当前值+要增加的值-最大值
        *合法值validNumber等于 最大值-当前值
        */
        int flowNumber  =  0;
        int validNumber = added;
        if (current + added - max > 0){
            validNumber = max - current;
            flowNumber = current + added - max;
        }
        return new OverFlowDesc(flowNumber, validNumber);
    }
}
