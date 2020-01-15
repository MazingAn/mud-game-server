package com.mud.game.object.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.Mongo;
import com.mud.game.handler.SkillFunctionHandler;
import com.mud.game.messages.MsgMessage;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.object.typeclass.WorldNpcObject;
import com.mud.game.structs.EmbeddedCommand;
import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.utils.jsonutils.JsonStrConvetor;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.Skill;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.yeauty.pojo.Session;

import java.util.*;

public class SkillObjectManager {

    public static SkillObject create(String skillTemplateKey) throws JsonProcessingException {
        SkillObject skillObject = new SkillObject();
        try {
            Skill template = DbMapper.skillRepository.findSkillByDataKey(skillTemplateKey);
            skillObject.setName(template.getName());
            skillObject.setDataKey(template.getDataKey());
            skillObject.setActionTime(template.getActionTime());
            skillObject.setCd(template.getCd());
            skillObject.setActionTime(template.getActionTime());
            skillObject.setExpandMp(template.getExpandMp());
            skillObject.setLevel(1);
            skillObject.setEffects(new HashSet<>());
            skillObject.setMessage(template.getMessage());
            skillObject.setCategoryType(template.getCategoryType());
            skillObject.setFunctionType(template.getFunctionType());
            skillObject.setWeaponType(template.getWeaponType());
            skillObject.setPositions(JsonStrConvetor.ToSet(template.getPositions()));
            skillObject.setEquippedPositions(new HashSet<>());
            skillObject.setPassive(template.isPassive());
            skillObject.setIcon(template.getIcon());
            skillObject.setSkillFunction(template.getSkillFunction());
            MongoMapper.skillObjectRepository.save(skillObject);
        }catch (Exception e){
            System.out.println(skillObject.getDataKey() + "没有找到");
        }
        return skillObject;
    }

    public static void calculusEffects(CommonCharacter caller, CommonCharacter target, SkillObject skillObject){
        /*
        * 计算技能的效果
        * */
        SkillFunctionHandler.calculusEffect(caller, target, skillObject);
    }


    public static void equipTo(SkillObject skillObject, CommonCharacter character, Session session) throws JsonProcessingException {
        /*
        * @ 装备技能到角色身上（仅限于被动技能）
        * */
        String ownerId = skillObject.getOwner();
        if(!ownerId.equals(character.getId())){
            session.sendText(JsonResponse.JsonStringResponse(new MsgMessage("你没有这个技能！")));
        }
    }

    public static float calculusRemainCd(SkillObject skillObject) {
        /*
        * @ 计算技能的Cd时间
        * */
        if(skillObject.getCdFinishTime() == null)  return 0;
        float cdRemain = (System.currentTimeMillis() - skillObject.getCdFinishTime()) / 1000F;
        return  cdRemain < 0 ? 0 : cdRemain;
    }

    public static List<EmbeddedCommand> getAvailableCommands(SkillObject skillObject, PlayerCharacter caller){
        List<EmbeddedCommand> cmds = new ArrayList<>();
        if(skillObject.isPassive()){
            // 检查技能的所有者是npc还是玩家
            if(MongoMapper.worldNpcObjectRepository.existsById(skillObject.getOwner())){
                // 是npc
                WorldNpcObject owner = MongoMapper.worldNpcObjectRepository.findWorldNpcObjectById(skillObject.getOwner());
                Map<String, Object> args = new HashMap<String, Object>();
                args.put("skill_key", skillObject.getDataKey());
                args.put("from", owner.getId());
                if(owner.isLearnByObject()){
                    // 通过物品学习
                    cmds.add(new EmbeddedCommand("请教", "learn_skill_by_object", args));
                }else{
                    // 正常的通过师傅学习
                    cmds.add(new EmbeddedCommand("请教", "learn_skill_from_teacher", args));
                }
            }else if(MongoMapper.playerCharacterRepository.existsById(skillObject.getOwner())){
                // 是玩家则显示 装备命令
                PlayerCharacter owner = MongoMapper.playerCharacterRepository.findPlayerCharacterById(skillObject.getOwner());
            }else{
                // 技能没有归属
                System.out.println("技能（"+ skillObject.getName() +"）没有归属！");
            }
        }
        return cmds;
    }
}
