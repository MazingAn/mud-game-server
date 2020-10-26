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
import com.mud.game.statements.skills.IncrementsAttr;
import com.mud.game.statements.skills.MangLuan;
import com.mud.game.statements.skills.ZhongDu;
import com.mud.game.statements.skills.huashan.JianzhangWuLianHuan;
import com.mud.game.statements.skills.NormalHit;
import com.mud.game.structs.SkillCastInfo;
import com.mud.game.structs.SkillCdInfo;

import java.lang.reflect.Constructor;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
                HarmInfo harmInfo = AttackAlgorithm.computeFinalHarm(caller, target);
                //应用伤害
                GameCharacterManager.changeStatus(target, "hp", harmInfo.finalHarm * -1, caller);
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
                SkillCdHandler.addSkillCd(caller.getId()+skillObject.getDataKey(), new Date());
                //返回技能冷却时间
                if (skillObject.getCd() != 0) {
                    sense.msgContents(new SkillCdMessage(new SkillCdInfo(skillObject.getCd(), skillObject.getId(), skillObject.getDataKey())));
                }
                //技能未命中
                if (null == harmInfo) {
                    return;
                }
            }

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
        // 中毒
        actionSkillFunctionSet.put("zhongdu", ZhongDu.class);
    }

}
