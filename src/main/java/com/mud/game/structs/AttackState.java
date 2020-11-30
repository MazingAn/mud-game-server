package com.mud.game.structs;

public enum AttackState {
    STATE_ZHUIJI, //常规
    STATE_NORMAL, //追击状态
    STATE_FANJI,  //反击状态
    STATE_NOTEVADEPARRY,  //无法闪避招架状态
    STATE_SHIMING,  //失明状态 命中率为0
    STATE_ZHUIHUN,  //追魂状态
    STATE_XUNYUN,  //眩晕状态  眩晕状态下 不可攻击不可防御不可逃跑不可移动所有操作不可  防御为0
    STATE_DAODI,  //倒地状态 倒地还有不可闪避和招架
    STATE_MANGLU,  //忙碌状态 忙碌是不可移动 不可逃跑 不可攻击

}
