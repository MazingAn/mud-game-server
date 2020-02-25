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

public class LearnSkillFromTeacher extends BaseCommand {
    /*
    * @ 玩家从师傅那里学习技能
    * */
    public LearnSkillFromTeacher(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException, JsonProcessingException {
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
