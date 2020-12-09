package com.mud.game.handler;

import com.mud.game.algorithm.AttackAlgorithm;
import com.mud.game.algorithm.HarmInfo;
import com.mud.game.combat.CombatSense;
import com.mud.game.messages.SkillCastMessage;
import com.mud.game.messages.SkillCdMessage;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.manager.SkillObjectManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.object.typeclass.WorldNpcObject;
import com.mud.game.statements.buffers.AddBuffer;
import com.mud.game.statements.skills.*;
import com.mud.game.statements.skills.huashan.JianzhangWuLianHuan;
import com.mud.game.structs.SkillCastInfo;
import com.mud.game.structs.SkillCdInfo;
import com.mud.game.utils.StateConstants;

import java.lang.reflect.Constructor;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.mud.game.utils.StateConstants.CHECK_XUEHAIWUBIAN_STATE;
import static com.mud.game.utils.StateConstants.CHECK_XUEMODAOFAXI_STATE;

/**
 * 技能函数缓存类
 * <p>
 * 这个类中定义的门派名称将在服务器启动的时候被初始化  参见：{@link com.mud.game.server.ServerManager} <br>
 * <p>
 * 游戏中会定义会多技能函数，在游戏管理人员维护期间可在后台自由组合这些函数已实现新的技能<br>
 * 游戏维护人员会将函数以字符串的形式存储在模板数据库中，在执行期间则解析字符串<br>
 * 根据解析所得的函数名称（key）来查找预先定义的对应实现类（class） <br>
 */
public class SkillFunctionHandler {

    /**
     * 主动技能函数集合 key为函数名称 value为对应的技能函数类
     */
    private static final Map<String, Class> actionSkillFunctionSet = new HashMap<>();
    /**
     * 被动技能函数集合 key为函数名称 value为对应的技能函数类
     */
    private static final Map<String, Class> passiveSkillFunctionSet = new HashMap<>();

    /**
     * 使用效果
     *
     * @param caller      技能的释放者
     * @param target      技能作用的目标
     * @param skillObject 技能对象
     */
    public static void useSkill(CommonCharacter caller, CommonCharacter target, SkillObject skillObject) {
        if (skillObject == null) {
            return;
        }
        String functionStr = skillObject.getSkillFunction();
        if (functionStr == null || functionStr.trim().equals("")) {
            return;
        }
        for (String function : functionStr.split(";")) {
            String[] functionSplited = function.split("\\(");
            String key = functionSplited[0].replaceAll("\"", "").replaceAll("\\'", "");
            String[] args = functionSplited[1].replaceAll("\\)", "").replaceAll("\\'", "").replaceAll("\"", "").split(",");
            // 根据技能是否是被动技能选择对应的functionSet；对于被动技能只返回技能属性加成
            // 对于主动技能，直接执行
            //TODO 技能返回战斗场景信息
            if (target != null) {
                //计算伤害
                HarmInfo harmInfo = AttackAlgorithm.computeFinalHarm(caller, target, skillObject);
                //应用伤害
                GameCharacterManager.changeStatus(target, "hp", harmInfo.finalHarm * -1, caller);
                //判断是否吸血
                if (!StateConstants.checkState(caller, CHECK_XUEMODAOFAXI_STATE)) {
                    double duration = 0.1;
                    //判断是否在 血海无边的状态下
                    if (StateConstants.checkState(caller, CHECK_XUEHAIWUBIAN_STATE)) {
                        duration = 0.15;
                    }
                    GameCharacterManager.changeStatus(caller, "hp", new Double(harmInfo.finalHarm * duration).intValue(), caller);                }
                //构建战斗输出
                String combatCastStr = SkillObjectManager.getCastMessage(caller, target, skillObject, harmInfo);
                SkillCastInfo skillCastInfo = new SkillCastInfo(caller, target, skillObject, combatCastStr);
                // GameCharacterManager.saveCharacter(target);
                CombatSense sense = null;
                if (caller instanceof WorldNpcObject) {
                    sense = NpcCombatHandler.getNpcCombatSense(caller.getId(), target.getId());
                } else {
                    sense = CombatHandler.getCombatSense(caller.getId());
                }
                if (sense == null) {
                    //切磋场景
                    sense = CombatHandler.getCombatSense(caller.getId() + target.getId());
                }
                sense.msgContents(new SkillCastMessage(skillCastInfo));
                //设置技能cd
                SkillCdHandler.addSkillCd(caller.getId() + skillObject.getDataKey(), new Date());
                //返回技能冷却时间
                if (skillObject.getCd() != 0) {
                    float skillCd = Float.parseFloat(caller.getCustomerAttr().get("skill_cd").get("value").toString());
                    sense.msgContents(new SkillCdMessage(new SkillCdInfo(skillObject.getCd() * skillCd, skillObject.getId(), skillObject.getDataKey())));
                }
                //技能未命中
                if (null == harmInfo) {
                    return;
                }
            }
            executionFunction(caller, target, skillObject, key, args, true);
        }
    }

    /**
     * 执行技能函数
     *
     * @param caller
     * @param target
     * @param skillObject
     * @param key
     * @param args
     * @param canXY
     */
    private static void executionFunction(CommonCharacter caller, CommonCharacter target, SkillObject skillObject, String key, String[] args, boolean canXY) {
        //函数
        Class clazz = null;
        if (skillObject.isPassive()) clazz = passiveSkillFunctionSet.get(key);
        else clazz = actionSkillFunctionSet.get(key);
        try {
            Constructor c = clazz.getConstructor(CommonCharacter.class, CommonCharacter.class, SkillObject.class, String.class, String[].class);
            c.newInstance(caller, target, skillObject, key, args);
        } catch (Exception e) {
            System.out.println(String.format("玩家在执行命令%s的时候触发了异常", key));
            e.printStackTrace();
        }
        //星移
        if (canXY && StateConstants.checkState(target, "星移")) {
            executionFunction(target, caller, skillObject, key, args, false);
        }
    }

    /**
     * 外部方法  初始化技能函数库 <br>
     * 这个方法总会在服务器启动的时候被调用  参见 {@link com.mud.game.server.ServerManager} {@code start} <br>
     */
    public static void initSkillFunctionSet() {
        initPassiveSkillFunctionSet();
        initActionSkillFunctionSet();
    }

    /**
     * 内部方法  初始化被动技能函数库 <br>
     * 如果你新建了被动技能函数类，请务必记得在这里注册
     */
    private static void initPassiveSkillFunctionSet() {
        // 所有被动技能的实现映射
        passiveSkillFunctionSet.put("increaments_attr", IncrementsAttr.class);
    }

    /**
     * 内部方法  初始化被动技能函数库 <br>
     * 如果你新建了主动技能函数类，请务必记得在这里注册
     */
    private static void initActionSkillFunctionSet() {
        //基本攻击技能
        actionSkillFunctionSet.put("hit", NormalHit.class);
        // 追加buffer
        actionSkillFunctionSet.put("add_buffer", AddBuffer.class);
        // 华山技能 剑掌✋五连环
        actionSkillFunctionSet.put("jianzhangwulianhuan", JianzhangWuLianHuan.class);
        // 忙乱
        actionSkillFunctionSet.put("mangluan", MangLuan.class);
        // 无视忙乱释放技能
        actionSkillFunctionSet.put("wushi_mangluan", WuShiMangLuan.class);
        // 中毒
        actionSkillFunctionSet.put("zhongdu", ZhongDu.class);
        // 逃跑
        actionSkillFunctionSet.put("taopao", TaoPao.class);
        // 灭绝剑法_绝
        actionSkillFunctionSet.put("hit_persent", HitPersent.class);
        // 岳家枪法_长河里落日
        actionSkillFunctionSet.put("add_buffer_scale", AddBufferScale.class);
        // 追击
        actionSkillFunctionSet.put("zhuiji", ZhuiJi.class);
        // 超连击
        actionSkillFunctionSet.put("chaolianji", ChaoLianJi.class);
        // 反击
        actionSkillFunctionSet.put("fanji", FanJi.class);
        // 眩晕
        actionSkillFunctionSet.put("xuanyun", XuanYun.class);
        // 天山折梅手_空手入白刃
        actionSkillFunctionSet.put("xiawu", XiaWu.class);
        // 暴击
        actionSkillFunctionSet.put("baoji", BaoJi.class);
        // 天羽奇剑_天女散花
        actionSkillFunctionSet.put("lingjiugong_tiannvsanhua", LingJiuGongTianNvSanHua.class);
        // 天羽奇剑_龙战与野
        actionSkillFunctionSet.put("lingjiugong_longzhanyuye", LingJiuGongLongZhanYuYe.class);
        // 金刀刀法_刺目
        actionSkillFunctionSet.put("shiming", Shiming.class);
        // 追魂
        actionSkillFunctionSet.put("zhuihun", ZhuiHun.class);
        // 破甲
        actionSkillFunctionSet.put("pojia", PoJia.class);
        // 金蛇剑法_金蛇飞舞
        actionSkillFunctionSet.put("hunmi", HunMi.class);
        // 倒地
        actionSkillFunctionSet.put("daodi", DaoDi.class);
        // 鸳鸯刀法_仁者
        actionSkillFunctionSet.put("add_hp_persent", AddHpPersent.class);
        // 血魔刀法_吸
        actionSkillFunctionSet.put("xuemodaofa_xi", XueMoDaoFaXi.class);
        // 血魔刀法_敕
        actionSkillFunctionSet.put("xuemodaofa_chi", XueMoDaofaChi.class);
        // 庖丁解牛_解牛
        actionSkillFunctionSet.put("tiandihui_jieniu", TianDiHuiJieNiu.class);
        // 倚天屠龙
        actionSkillFunctionSet.put("yitiantulong", YiTianTuLong.class);
        // 飞星
        actionSkillFunctionSet.put("xingxiupai_feixing", XingXiuPaiFeiXing.class);
        // 暴雨
        actionSkillFunctionSet.put("baoyu", BaoYu.class);
        // 弹指神通_挥斥方遒
        actionSkillFunctionSet.put("huichifangqiu", HuiChiFangQiu.class);
        // 无中生有
        actionSkillFunctionSet.put("wuzhongshengyou", WuZhongShengYou.class);
        // 呆若木鸡
        actionSkillFunctionSet.put("chihuan", ChiHuan.class);
        // 玉女心经_清心
        actionSkillFunctionSet.put("clean_debuffer", CleanDebuffer.class);
        // 降龙十八掌_潜龙勿用
        actionSkillFunctionSet.put("qianlongwuyong", QianLongWuYong.class);
        // 降龙十八掌-》飞龙在天
        actionSkillFunctionSet.put("feilongzaitian", FeiLongZaiTian.class);
        // 降龙十八掌-》亢龙有悔
        actionSkillFunctionSet.put("kanglongyouhui", KangLongYouHui.class);
        // 妙手
        actionSkillFunctionSet.put("touqie", TouQie.class);
        // 蛤蟆功-》吼
        actionSkillFunctionSet.put("hama_hou", Hama_Hou.class);
        // 逆转经脉-》逆转
        actionSkillFunctionSet.put("jianshaocd", JianShaoCd.class);
        // 天山六阳掌_白虹
        actionSkillFunctionSet.put("hit_mp_persent", HitMpPersent.class);
        // 北冥神功_北冥真气
        actionSkillFunctionSet.put("attack_to_hp", AttackToHp.class);
        // 小无相功_无相
        actionSkillFunctionSet.put("learn_random_skill", LearnRandomSkill.class);
        // 小无相功_无我
        actionSkillFunctionSet.put("use_learned_skill", UseLearnedSkill.class);
        // 九阳神功_九阳护体
        actionSkillFunctionSet.put("jiuyanghuti", JiuYangHuTi.class);
        // 九阳神功_九阳真炎
        actionSkillFunctionSet.put("jiuyangzhenyan", JiuYangZhenYan.class);
        // 金刚伏魔圈_金刚伏魔
        actionSkillFunctionSet.put("jingangfumo", JinGangFuMo.class);
        // 绝户虎爪手_绝户
        actionSkillFunctionSet.put("juehu", JueHu.class);
        // 纯阳无极功_纯阳
        actionSkillFunctionSet.put("chunyang", ChunYang.class);
        // 纯阳无极功_无极
        actionSkillFunctionSet.put("wuji", WuJi.class);
        // 真武七截阵_真武七截
        actionSkillFunctionSet.put("zhenwuqijie", XhenWuQiJie.class);
        // 八荒六合唯我独尊功_不老长春
        actionSkillFunctionSet.put("lingjiugong_bulaochangchun", LingJiuGongBuLaoChangChun.class);
        // 八荒六合唯我独尊功_八荒六合
        actionSkillFunctionSet.put("lingjiugong_bahuangliuhe", LingJiuGongBaHuangLiuHe.class);
        // 化功大法_化功
        actionSkillFunctionSet.put("xingxiupai_huagong", XingXiuPaiHuaGong.class);
        // 葵花神功_隐字诀
        actionSkillFunctionSet.put("yinshen", YinShen.class);
        // 奇门遁术_千里追踪
        actionSkillFunctionSet.put("qianlizhuizong", QianLiZhuiZong.class);
        // 火焰刀_欲火焚身
        actionSkillFunctionSet.put("yuhuofenshen", YuHuoFenShen.class);
        // 龙象波若功_龙相
        actionSkillFunctionSet.put("longxiang", LongXiang.class);
        // 龙象波若功_象相
        actionSkillFunctionSet.put("xiangxiang", XiangXiang.class);
        // 枯荣神功_枯荣
        actionSkillFunctionSet.put("kurong", KuRong.class);
        // 六脉神剑_六脉剑气
        actionSkillFunctionSet.put("liumaijianqi", LiuMaiJianQi.class);
        // 六脉神剑_六脉齐发
        actionSkillFunctionSet.put("liumaiqifa", LiuMaiQiFa.class);
        // 玉箫剑法_碧海潮声曲
        actionSkillFunctionSet.put("bihaichaoshengqu", BiHaiChaoShengQu.class);
        // 血海魔攻_血海无边
        actionSkillFunctionSet.put("xuehaiwubian", XueHaiWuBian.class);
        // 参合指_离合三伤
        actionSkillFunctionSet.put("lihesanshang", LiHeSanShang.class);
        // 斗转星移_斗转
        actionSkillFunctionSet.put("gusumurong_douzhuan", GuSuMuRongDouZhuan.class);
        // 斗转星移_星移
        actionSkillFunctionSet.put("gusumurong_xingyi", GuSuMuRongXingYi.class);
        // 神行百变_神行
        actionSkillFunctionSet.put("shenxing", ShenXing.class);
        // 烟雨飘渺剑_杀意决
        actionSkillFunctionSet.put("shayi", ShaYi.class);
        // 吸星大法_吸星
        actionSkillFunctionSet.put("xixing_mp", XiXingMp.class);
        // 吸星大法_吸腥
        actionSkillFunctionSet.put("xixing_hp", XiXingHp.class);
    }
}
