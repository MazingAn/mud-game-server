package com.mud.game.commands.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.commands.BaseCommand;
import com.mud.game.messages.LearnByObjectStatusMessage;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.manager.PlayerScheduleManager;
import com.mud.game.object.manager.WorldNpcObjectManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.WorldNpcObject;
import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.NormalObject;
import com.mud.game.worlddata.db.models.NpcLearnObjectList;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LearnSkillByObjectCheck extends BaseCommand {
    /*
    * @ 玩家通过给npc物品学习技能的先期检查工作
    * @ 玩家学习状态返回给客户端
    * @ 请求方式：
    * {"cmd":
    *       "learn_by_object",
    *       "args": {
    *           "skill_key" : "<skill key>",
    *            "from": "<target dbref>"
    *       }
    *   }
    * */
    public LearnSkillByObjectCheck(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException, JsonProcessingException {
        Session session = getSession();
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        JSONObject args = getArgs();
        String teacherId = args.getString("args");
        WorldNpcObject teacher = MongoMapper.worldNpcObjectRepository.findWorldNpcObjectById(teacherId);
        // 检查是否能够学习

        // 检查玩家是否在这个NPC这里学习过
        if(caller.getLearnByObjectRecord().containsKey(teacher.getName())){
            //如果学习过，而且玩家充值的能量还有，则表示可以学习
            if(caller.getLearnByObjectRecord().get(teacher.getName()) > 0){
                //可以学习，则返回学习的技能列表
                WorldNpcObjectManager.getCanTeachSkills(teacher, caller, session);
                // 返回玩家在这里的能量充值状态
                LearnByObjectStatusMessage message  = new LearnByObjectStatusMessage(teacher.getName(), caller.getLearnByObjectRecord().get(teacher.getName()));
                session.sendText(JsonResponse.JsonStringResponse(message));
            } else{
                // 玩家学习过，但是在这个NPC这里能余额已经不足
                // 返回玩家在这里的能量充值状态
                NpcLearnObjectList record = DbMapper.npcLearnObjectListRepository.getByNpcKey(teacher.getDataKey());
                NormalObject normalObject = DbMapper.normalObjectRepository.findNormalObjectByDataKey(record.getNeededObject());
                LearnByObjectStatusMessage message = new LearnByObjectStatusMessage(teacher.getName(), 0, normalObject.getDataKey(), normalObject.getName() );
                session.sendText(JsonResponse.JsonStringResponse((message)));
            }
        } else{
            //玩家没有在这个NPC的学习记录，首次学习
            // 获得需要的物品
            try {
                NpcLearnObjectList record = DbMapper.npcLearnObjectListRepository.getByNpcKey(teacher.getDataKey());
                NormalObject normalObject = DbMapper.normalObjectRepository.findNormalObjectByDataKey(record.getNeededObject());
                LearnByObjectStatusMessage message = new LearnByObjectStatusMessage(teacher.getName(), 0, normalObject.getDataKey(), normalObject.getName() );
                session.sendText(JsonResponse.JsonStringResponse(message));
            }catch (Exception e){
                System.out.println("在从npc" + teacher.getName() + "(" + teacher.getDataKey() +")这里学习技能的时候，没有办法充值潜能！可能是没有配置等价交换的物品！");
                e.printStackTrace();
            }
        }


    }
}
