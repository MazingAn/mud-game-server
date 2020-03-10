package com.mud.game.condition;

import com.mud.game.object.typeclass.PlayerCharacter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 条件检测类的基类
 *
 * <p>
 * 条件检测类一般被用做游戏中的条件检测，它是动态执行的，根据游戏内容和游戏配置获取一个字符串的内容决定运行哪一个判断
 * </p>
 * 可以理解为，判断类是对游戏中多有可能要进行的判断的一个预先实现，然后使用字符串解析的方式获取参数执行判断
 *
 * */

public abstract class BaseCondition {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /** 执行判断的玩家， 条件检测最终影响玩家的游戏进程 */
    private PlayerCharacter playerCharacter;
    /** 检测类的key 根据这个key可以反射到对应的检测类并执行检测
     *  关于key与对应的检测类的映射关系 参考  {@link com.mud.game.handler.ConditionHandler} */
    private String key;
    /** 检测类进行检测的时候所需要的参数数组 */
    private String[] args;

    /**
     * 构造函数
     *
     * 构造函数用于捕获参数
     *
     * @param playerCharacter  检测的主体对象（要被检测的玩家）
     * @param key 检测类的健
     * @param args
     *
     * */
    public BaseCondition(String key, PlayerCharacter playerCharacter, String[] args) {
        this.playerCharacter = playerCharacter;
        this.args = args;
        this.key = key;
    }

    /**
     * 虚方法  执行检测
     *
     * 这个方法要求分会一个bool值 以反应对应的检测是否通过
     *
     * 一般的实现方法是，解析对应的字符串并执行字符串里面的函数<br>
     * 游戏中多数检测都是对玩家状态的检测  玩家状态通过反射获得<br>
     * 因为方法的执行 是在程序之外的游戏数据配置文件中给定的 ，所以可能会引发无法进行对应检测的异常
     *
     * @throws NoSuchFieldException 要检测玩家的某一个属性 但反射不到这个属性的时候抛出
     * @throws IllegalAccessException 要检测玩家的某一个属性 但是反射过来并没有对应的访问权限
     *
     * <p>
     *     为了解决反射访问权限的问题，在设计Character类的时候所有的属性都被设置为了public级别 <br>
     *         可参见： {@link com.mud.game.object.supertypeclass.CommonCharacter} <br>
     *             {@link PlayerCharacter}
     *             {@link com.mud.game.object.typeclass.WorldNpcObject}
     * </p>
     *
     * */
    public abstract boolean match() throws NoSuchFieldException, IllegalAccessException;


    public PlayerCharacter getPlayerCharacter() {
        return playerCharacter;
    }

    public void setPlayerCharacter(PlayerCharacter playerCharacter) {
        this.playerCharacter = playerCharacter;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }
}
