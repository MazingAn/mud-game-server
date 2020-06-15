package com.mud.game.commands.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.commands.BaseCommand;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.manager.PlayerScheduleManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *  玩家学习自己师傅的技能
 * 请求示例：
 * <pre>
 *  {
 *       "cmd":  "learn_skill_from_teacher",
 *      "args": {
 *           "skill_key":"skill_zhishi_wakuang",  //要学习的技能的key
 *          "from":"5e0b2979b5de8a0a2787d9d1" //教授技能的人的ID
 *      }
 *  }
 * </pre>
 * */
public class LearnSkillFromTeacher extends BaseCommand {

    public LearnSkillFromTeacher(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        Session session = getSession();
        JSONObject args = getArgs();
        String teacherId = args.getString("from");
        String skillKey = args.getString("skill_key");
        Runnable runnable = PlayerCharacterManager.learnSkillFromTeacher(caller, skillKey, teacherId, session);
        if(runnable != null) {
            ScheduledExecutorService service = PlayerScheduleManager.createOrGetExecutorServiceForCaller(caller.getId());
            service.scheduleAtFixedRate(runnable, 0, 2, TimeUnit.SECONDS);
        }
    }
}
