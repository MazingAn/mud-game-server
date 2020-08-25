package com.mud.game.commands.character;

import com.mud.game.commands.BaseCommand;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.manager.PlayerScheduleManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *  玩家通过物品换取潜能之后学习技能
 * <p> 参数： npc的id  学习的技能的 key
 * 返回： 玩家进入挂机状态，开始学习技能 </p>
 * 请求示例：
 * <pre>
 *  {
 *       "cmd":  "learn_skill_by_object",
 *      "args": {
 *           "skill_key":"skill_zhishi_wakuang",  //要学习的技能的key
 *          "from":"5e0b2979b5de8a0a2787d9d1" //教授技能的人的ID
 *      }
 *  }
 * </pre>
 * */
public class LearnSkillByObject extends BaseCommand {


    public LearnSkillByObject(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        Session session = getSession();
        PlayerCharacter caller = (PlayerCharacter)getCaller();
        JSONObject args = getArgs();
        try{
            String skillKey = args.getString("skill_key");
            String teacherId = args.getString("from");
            Runnable runnable = PlayerCharacterManager.learnSkillByObject(caller, skillKey, teacherId, session);
            if(runnable != null) {
                ScheduledExecutorService service = PlayerScheduleManager.createOrGetExecutorServiceForCaller(caller.getId());
                service.scheduleAtFixedRate(runnable, 0, 2, TimeUnit.SECONDS);
            }
        }catch (Exception e){
            System.out.println("在通过物品学习技能的时候发生了错误！");
        }
    }
}
