package com.mud.game.handler;

import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.statements.buffers.AddBuffer;
import com.mud.game.statements.skills.IncrementsAttr;
import com.mud.game.statements.skills.huashan.JianzhangWuLianHuan;
import com.mud.game.statements.skills.NormalHit;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * 技能函数缓存类
 *
 * 这个类中定义的门派名称将在服务器启动的时候被初始化  参见：{@link com.mud.game.server.ServerManager} <br>
 *
 * 游戏中会定义会多技能函数，在游戏管理人员维护期间可在后台自由组合这些函数已实现新的技能<br>
 * 游戏维护人员会将函数以字符串的形式存储在模板数据库中，在执行期间则解析字符串<br>
 * 根据解析所得的函数名称（key）来查找预先定义的对应实现类（class） <br>
 *
 * */
public class SkillFunctionHandler {

    /**主动技能函数集合 key为函数名称 value为对应的技能函数类 */
    private static final Map<String, Class> actionSkillFunctionSet = new HashMap<>();
    /**被动技能函数集合 key为函数名称 value为对应的技能函数类 */
    private static final Map<String, Class> passiveSkillFunctionSet = new HashMap<>();

    /**
     * 使用效果
     *
     * @param caller 技能的释放者
     * @param target 技能作用的目标
     * @param skillObject 技能对象
     *
     * */
    public static void useSkill(CommonCharacter caller, CommonCharacter target, SkillObject skillObject){
        String functionStr = skillObject.getSkillFunction();
        if(functionStr == null || functionStr.trim().equals("")){
            return;
        }
        for(String function : functionStr.split(";")){
            String[] functionSplited = function.split("\\(");
            String key = functionSplited[0].replaceAll("\"", "").replaceAll("\\'", "");
            String[] args = functionSplited[1].replaceAll("\\)", "").replaceAll("\\'","").replaceAll("\"", "").split(",");
            // 根据技能是否是被动技能选择对应的functionSet；对于被动技能只返回技能属性加成
            // 对于主动技能，直接执行
            Class clazz = null;
            if(skillObject.isPassive()) clazz = passiveSkillFunctionSet.get(key);
            else clazz = actionSkillFunctionSet.get(key);
            try{
                Constructor c = clazz.getConstructor(CommonCharacter.class, CommonCharacter.class, SkillObject.class, String.class, String[].class);
                c.newInstance(caller, target, skillObject, key, args);
            }catch (Exception e){
                System.out.println(String.format("玩家在执行命令%s的时候触发了异常", key));
                e.printStackTrace();
            }
        }
    }

    /**
     * 外部方法  初始化技能函数库 <br>
     *     这个方法总会在服务器启动的时候被调用  参见 {@link com.mud.game.server.ServerManager} {@code start} <br>
     * */
    public static void initSkillFunctionSet(){
        initPassiveSkillFunctionSet();
        initActionSkillFunctionSet();
    }

    /**
     * 内部方法  初始化被动技能函数库 <br>
     *     如果你新建了被动技能函数类，请务必记得在这里注册
     * */
    private static void initPassiveSkillFunctionSet(){
        // 所有被动技能的实现映射
        passiveSkillFunctionSet.put("increaments_attr", IncrementsAttr.class);
    }

    /**
     * 内部方法  初始化被动技能函数库 <br>
     *     如果你新建了主动技能函数类，请务必记得在这里注册
     * */
    private static void initActionSkillFunctionSet(){
        //基本攻击技能
        actionSkillFunctionSet.put("hit", NormalHit.class);
        // 追加buffer
        actionSkillFunctionSet.put("add_buffer", AddBuffer.class);
        // 华山技能 剑掌✋五连环
        actionSkillFunctionSet.put("jianzhangwulianhuan", JianzhangWuLianHuan.class);
    }

}
