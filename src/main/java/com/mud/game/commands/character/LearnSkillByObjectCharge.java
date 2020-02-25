package com.mud.game.commands.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.util.JSON;
import com.mud.game.commands.BaseCommand;
import com.mud.game.messages.AlertMessage;
import com.mud.game.messages.LearnByObjectBalanceMessage;
import com.mud.game.messages.LearnByObjectStatusMessage;
import com.mud.game.object.manager.WorldNpcObjectManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.WorldNpcObject;
import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.WorldNpc;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import java.util.Map;

public class LearnSkillByObjectCharge extends BaseCommand {

    /*
     * @ 玩家通过物品从NPC处学习技能的充值操作，当玩家提交物品兑换的潜能用完之后，则需要充值
     * @ 接受玩家充值的信息（物品，和数量）如果充值成功，返回NPC技能列表，否则返回错误信息
     * @ 请求方式：
     * {"cmd":
     *      "give_learn_object",
     *  "args":
     *      {
     *          "target_dbref":"5e0b2979b5de8a0a2787d9d1", //从何处学习？npc的ID
     *          "object_key":"OBJECT_JINZI",
     *          "number":1
     *      } //提交的充值信息（物品KEY和数量）
     * }
    * */

    public LearnSkillByObjectCharge(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException, JsonProcessingException {
        Session session = getSession();
        PlayerCharacter caller = (PlayerCharacter)getCaller();
        try{
            JSONObject args = getArgs();
            String teacherId = args.getString("target_dbref");
            String objectKey = args.getString("object_key");
            int number = args.getInt("number");
            // 获得npc的信息
            WorldNpcObject teacher = MongoMapper.worldNpcObjectRepository.findWorldNpcObjectById(teacherId);
            // TODO：从玩家背包移除相应数量的物品
            // 如果移除成功，则给玩家充值
            int perPotential = DbMapper.npcLearnObjectListRepository.getByNpcKey(teacher.getDataKey()).getPerPotential(); //每单位物品充值量
            int currentPotentialBalance; //当前余额
            Map<String, Integer> learnByObjectRecord =  caller.getLearnByObjectRecord();
            if(learnByObjectRecord.containsKey(teacher.getName())){
                // 以前充值过
                currentPotentialBalance = learnByObjectRecord.get(teacher.getName());
            }else{
                // 首次充值精力
                currentPotentialBalance = 0;
            }
            int blance = currentPotentialBalance + number * perPotential;
            learnByObjectRecord.put(teacher.getName(), blance);
            caller.setLearnByObjectRecord(learnByObjectRecord);
            MongoMapper.playerCharacterRepository.save(caller);

            // 充值成功之后返回可以学习的技能信息和充值状态
            String objectName = DbMapper.normalObjectRepository.findNormalObjectByDataKey(objectKey).getName();
            LearnByObjectStatusMessage message = new LearnByObjectStatusMessage(teacher.getName(), blance, objectKey, objectName);
            // 返回充值状态
            session.sendText(JsonResponse.JsonStringResponse(message));
            // 返回技能列表
            WorldNpcObjectManager.getCanTeachSkills(teacher, caller, session);
            // 返回当前所剩潜能余额
            session.sendText(JsonResponse.JsonStringResponse(new LearnByObjectBalanceMessage(blance)));
        }catch (Exception e){
            session.sendText(JsonResponse.JsonStringResponse(new AlertMessage("发生错误，暂时无法从这里学习")));
        }

    }
}
