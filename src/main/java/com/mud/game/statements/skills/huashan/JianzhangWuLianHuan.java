package com.mud.game.statements.skills.huashan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.algorithm.AttackAlgorithm;
import com.mud.game.messages.AlertMessage;
import com.mud.game.messages.MsgMessage;
import com.mud.game.messages.SkillCastMessage;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.statements.BaseAttackSkillStatement;
import com.mud.game.structs.SkillCastInfo;
import org.json.JSONException;

/**
 * 华山派技能 剑掌五连环
 * <pre>
 * 执行条件：
 * **必须华山剑和华山拳同时装备
 * 执行效果：
 * **连击5
 * </pre>
 * */
public class JianzhangWuLianHuan extends BaseAttackSkillStatement {
    /**
     * 攻击技能基类的构造函数
     *
     * @param caller      调用者
     * @param target      受攻击者
     * @param skillObject 使用的技能
     * @param key         技能函数的名称
     * @param args        技能函数的参数列表
     */
    public JianzhangWuLianHuan(CommonCharacter caller, CommonCharacter target, SkillObject skillObject, String key, String[] args) {
        super(caller, target, skillObject, key, args);
    }

    @Override
    public void attack() throws JSONException, JsonProcessingException {
        CommonCharacter caller = getCaller();
        CommonCharacter target = getTarget();
        SkillObject skillObject = getSkillObject();
        // 检测是否装备了华山剑法和华山掌法
//        if(GameCharacterManager.hasEquippedSkill(caller, "skill_teshu_huashanjianfa") &&
//                GameCharacterManager.hasEquippedSkill(caller, "skill_teshu_huashanquanfa")){
//            AttackAlgorithm.lianji(caller, target, skillObject, 5);
//        }else{
//            caller.msg(new AlertMessage("必须同时装备华山剑发与华山拳法才可使用！"));
//        }
        try{
            beforeAttack();
            AttackAlgorithm.lianji(caller, target, skillObject, 5);
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("执行技能--剑掌五连环--执行失败");
        }
    }
}
