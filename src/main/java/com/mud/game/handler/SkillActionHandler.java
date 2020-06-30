package com.mud.game.handler;

import java.util.ArrayList;
import java.util.List;

public class SkillActionHandler {

    public static List<String> actionDaoList = new ArrayList<String>();
    public static List<String> actionJianList = new ArrayList<String>();
    public static List<String> actionQimenList = new ArrayList<String>();
    public static List<String> actionAnqiList = new ArrayList<String>();
    public static List<String> actionQuanjiaoList = new ArrayList<String>();
    public static List<String> actionPreciseList = new ArrayList<String>();
    public static List<String> actionDamageList = new ArrayList<String>();

    public static void initSkillActionList(){
        // 刀的招式
        actionDaoList.add("持刀式");
        actionDaoList.add("藏刀式");
        actionDaoList.add("拔刀式");
        actionDaoList.add("对刀式");
        actionDaoList.add("丁字回杀");
        actionDaoList.add("左弓扎刀");
        // 剑的招式
        actionJianList.add("挽花点挑");
        actionJianList.add("换把云扫");
        actionJianList.add("顺风扫叶");
        actionJianList.add("乌龙绞柱");
        actionJianList.add("白蛇吐信");
        actionJianList.add("独步九天");
        // 奇门招式
        actionQimenList.add("莲花舞");
        actionQimenList.add("白猴亮棍");
        actionQimenList.add("鹿倒弹蹄");
        actionQimenList.add("白虎拦路");
        actionQimenList.add("金鸡独立");
        // 暗器招式
        actionAnqiList.add("满天星");
        actionAnqiList.add("含沙射影");
        actionAnqiList.add("暴雨梨花");
        actionAnqiList.add("满天飞羽");
        // 拳脚招式
        actionQuanjiaoList.add("丹凤朝阳");
        actionQuanjiaoList.add("黑虎掏心");
        actionQuanjiaoList.add("白鹤亮翅");
        actionQuanjiaoList.add("灵猴采桃");
        actionQuanjiaoList.add("猛虎伏案");
        actionQuanjiaoList.add("镜里观影");
        actionQuanjiaoList.add("移山填海");
        // 躲避语句
        actionPreciseList.add("可是{g%s{n一招{g「大鹏展翅」{n侧身一让，{r%s{n这一招扑了个空。");
        actionPreciseList.add("但是{g%s{n一招{p「白鹤冲天」{n身形飘忽，轻轻一纵，早已避开{r%s{n。。");
        actionPreciseList.add("只见{g%s{n一招{p「白鹤冲天」{n，身体向上笔直地纵起丈余，躲过了{r%s{n这一招。");
        actionPreciseList.add("但是{g%s{n身子一侧，闪了开去。{r%s{n扑了个空，结果差点摔倒。");
        // 受到攻击语句
        actionDamageList.add("结果一击命中，{g%s{n的腰间登时肿了一块老高，造成{r%s{n点伤害!");
        actionDamageList.add("结果一击命中，{g%s{n的左肩登时肿了一块老高，造成{r%s{n点伤害!");
        actionDamageList.add("结果一击命中，{g%s{n的右肩登时肿了一块老高，造成{r%s{n点伤害!");
        actionDamageList.add("结果一击命中，{g%s{n的左脚登时肿了一块老高，造成{r%s{n点伤害!");
        actionDamageList.add("结果一击命中，{g%s{n的右脚登时肿了一块老高，造成{r%s{n点伤害!");
        actionDamageList.add("结果一击命中，{g%s{n的肚子登时瘪下去好大一个洞，造成{r%s{n点伤害!");
        actionDamageList.add("结果一击命中，{g%s{n的胸口登时多了一块大脚印，造成{r%s{n点伤害!");
        actionDamageList.add("结果伤到了{g%s{n的皮肉，造成{r%s{n点伤害!");
        actionDamageList.add("结果只是轻轻碰到{g%s{n，比苍蝇拍重了一点，造成{r%s{n点伤害!");
    }

}
