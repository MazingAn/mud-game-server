package com.mud.game.handler;

import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.NormalObjectObject;
import com.mud.game.statements.objectfunctions.OFSAddAttr;
import com.mud.game.statements.objectfunctions.OFSMoveTo;
import com.mud.game.statements.objectfunctions.OFSRestore;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * 物品函数缓存类
 * <p>
 * 这个类中定义的门派名称将在服务器启动的时候被初始化  参见：{@link com.mud.game.server.ServerManager} <br>
 * <p>
 * 游戏中会定义会多物品函数，在游戏管理人员维护期间可在后台自由组合这些函数已实现新的技能<br>
 * 游戏维护人员会将函数以字符串的形式存储在模板数据库中，在执行期间则解析字符串<br>
 * 根据解析所得的函数名称（key）来查找预先定义的对应实现类（class） <br>
 */
public class ObjectFunctionHandler {

    /**
     * 物品效果函数缓存 key为函数名称 value为对应的物品函数类
     */
    private static final Map<String, Class> objectFunctionMap = new HashMap<>();

    /**
     * 使用效果
     *
     * @param caller             使用物品的人
     * @param target             技能作用的目标
     * @param normalObjectObject 物品对象
     */
    public static void useObject(CommonCharacter caller, CommonCharacter target, NormalObjectObject normalObjectObject) {
        String functionStr = normalObjectObject.getFunction();
        if (functionStr == null || functionStr.trim().equals("")) {
            return;
        }
        for (String function : functionStr.split(";")) {
            String[] functionSplited = function.split("\\(");
            String key = functionSplited[0].replaceAll("\"", "").replaceAll("\\'", "");
            String[] args = functionSplited[1].replaceAll("\\)", "").replaceAll("\\'", "").replaceAll("\"", "").split(",");
            //函数
            Class clazz = objectFunctionMap.get(key);
            try {
                Constructor c = clazz.getConstructor(CommonCharacter.class, CommonCharacter.class, NormalObjectObject.class, String.class, String[].class);
                c.newInstance(caller, target, normalObjectObject, key, args);
            } catch (Exception e) {
                System.out.println(String.format("玩家在执行物品函数%s的时候触发了异常", key));
                e.printStackTrace();
            }
        }
    }

    /**
     * 外部方法  初始化物品函数库 <br>
     * 这个方法总会在服务器启动的时候被调用  参见 {@link com.mud.game.server.ServerManager} {@code start} <br>
     */
    public static void initObjectFunctionSet() {
        // 传送符
        objectFunctionMap.put("goto_room", OFSMoveTo.class);
        // 使用药品恢复
        objectFunctionMap.put("huifu", OFSRestore.class);
        // 属性加成
        objectFunctionMap.put("add_attr", OFSAddAttr.class);
    }

}
