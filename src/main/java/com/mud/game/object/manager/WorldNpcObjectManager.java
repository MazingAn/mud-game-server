package com.mud.game.object.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.Mongo;
import com.mud.game.handler.SkillTypeHandler;
import com.mud.game.messages.MsgMessage;
import com.mud.game.messages.TeachersSkillMessage;
import com.mud.game.object.account.Player;
import com.mud.game.object.typeclass.*;
import com.mud.game.structs.EmbeddedCommand;
import com.mud.game.structs.GameObjectAppearance;
import com.mud.game.structs.NpcAppearance;
import com.mud.game.structs.SimpleSkill;
import com.mud.game.utils.jsonutils.Attr2Map;
import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.utils.resultutils.GameWords;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.*;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.yeauty.pojo.Session;

import java.util.*;

public class WorldNpcObjectManager {

    public static WorldNpcObject build(WorldNpc template) throws JsonProcessingException {
        WorldNpcObject obj = new WorldNpcObject();
        obj.setDataKey(template.getDataKey());
        obj.setDescription(template.getDescription());
        obj.setLocation(template.getLocation());
        obj.setTeacher(template.isTeacher());
        obj.setLearnByObject(template.isCanLearnByObject());
        obj.setTeachCondition(template.getTeachCondition());
        // 初始化npc信息
        // 根据注册的信息设置角色信息
        obj.setName(template.getName());
        obj.setGender(template.getGender());
        obj.setArm(20);
        obj.setBody(20);
        obj.setBone(20);
        obj.setSmart(20);
        obj.setLooks(20);
        obj.setLucky(20);
        // 从玩家模版加载初始化信息
        CharacterModel npcModel = DbMapper.characterModelRepository.findCharacterModelByDataKey(template.getModel());
        obj.setCustomerAttr(Attr2Map.characterAttrTrans(npcModel.getAttrs()));
        obj.setTitle(template.getTitle());
        obj.setSchoolTitle(template.getSchoolTitle());
        obj.setSchool(template.getSchool());
        // 玩家信息的初始化设置
        obj.setAfter_arm(0);
        obj.setAfter_body(0);
        obj.setAfter_bone(0);
        obj.setAfter_smart(0);
        obj.setAfter_looks(0);
        obj.setAfter_lucky(0);
        obj.setShowCondition(template.getShowCondition());
        MongoMapper.worldNpcObjectRepository.save(obj);
        // 加载默认技能信息
        bindDefaultSkills(obj);
        // TODO 加载默认装备信息
        // 加载掉落信息
        bindLootList(obj);
        // 加载绑定的事件
        bindEvents(obj);
        // 把npc放到房间内
        WorldRoomObject room = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectByDataKey(obj.getLocation());
        WorldRoomObjectManager.updateNpc(room, obj);
        // 设置NPC的基本属性
        resetHealth(obj);
        return obj;
    }

    public static void update(WorldNpcObject obj, WorldNpc template) throws JsonProcessingException {
        obj.setDescription(template.getDescription());
        obj.setLocation(template.getLocation());
        obj.setTeacher(template.isTeacher());
        obj.setLearnByObject(template.isCanLearnByObject());
        obj.setTeachCondition(template.getTeachCondition());
        // 初始化npc信息
        // 根据注册的信息设置角色信息
        obj.setName(template.getName());
        obj.setGender(template.getGender());
        obj.setArm(20);
        obj.setBody(20);
        obj.setBone(20);
        obj.setSmart(20);
        obj.setLooks(20);
        obj.setLucky(20);
        // 从玩家模版加载初始化信息
        CharacterModel npcTemplate = DbMapper.characterModelRepository.findCharacterModelByDataKey(template.getModel());
        obj.setCustomerAttr(Attr2Map.characterAttrTrans(npcTemplate.getAttrs()));
        obj.setTitle(template.getTitle());
        obj.setSchoolTitle(template.getSchoolTitle());
        obj.setSchool(template.getSchool());
        // 玩家信息的初始化设置
        obj.setAfter_arm(0);
        obj.setAfter_body(0);
        obj.setAfter_bone(0);
        obj.setAfter_smart(0);
        obj.setAfter_looks(0);
        obj.setAfter_lucky(0);
        obj.setShowCondition(template.getShowCondition());
        // 删除旧的技能
        clearSkills(obj);
        //加载默认技能信息
        bindDefaultSkills(obj);
        // TODO 加载默认装备信息
        // 加载掉落信息
        bindLootList(obj);
        // 加载绑定的事件
        bindEvents(obj);
        // 把npc放到房间内
        WorldRoomObject room = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectByDataKey(obj.getLocation());
        WorldRoomObjectManager.updateNpc(room, obj);
        // 设置NPC的基本属性
        resetHealth(obj);
    }

    public static void resetHealth(WorldNpcObject obj) {
        obj.setMax_hp(obj.getLimit_hp());
        obj.setHp(obj.getMax_hp());
        obj.setMax_mp(obj.getMax_mp());
        obj.setMp(obj.getMp());
    }

    public static void onPlayerLook(WorldNpcObject npc, PlayerCharacter playerCharacter, Session session) throws JsonProcessingException {
        /*
        * @ 当玩家查看NPC的时候返回NPC信息和可用命令
        * */
        Map<String, Object> lookMessage = new HashMap<>();
        NpcAppearance appearance = new NpcAppearance(npc);
        // 设置玩家可以对此物体执行的命令
        appearance.setCmds(WorldNpcObjectManager.getAvailableCommands(npc, playerCharacter));
        lookMessage.put("look_obj", appearance);
        session.sendText(JsonResponse.JsonStringResponse(lookMessage));
    }


    public static List<EmbeddedCommand> getAvailableCommands(WorldNpcObject npc, PlayerCharacter playerCharacter){
        /*
         * @ 获取NPC可执行的命令
         * @ NPC对应的命令还是比较多的，不同身份的NPC对不同玩家会有不通的可执行命令
         * */
        List<EmbeddedCommand> cmds = new ArrayList<>();
        // 拜师命令
        if(npc.isTeacher() && !(playerCharacter.getTeacher().equals(npc.getDataKey()))){
           cmds.add(new EmbeddedCommand("拜师", "find_teacher", npc.getId()));
        }
        // 请教与出师命令,师门任务命令
        if(npc.isTeacher() && playerCharacter.getTeacher().equals(npc.getDataKey())){
            cmds.add(new EmbeddedCommand("请教", "learn_from_teacher", npc.getId()));
            cmds.add(new EmbeddedCommand("出师", "left_teacher", npc.getId()));
            cmds.add(new EmbeddedCommand("师门任务", "teacher_quest", npc.getId()));
        }
        // 学艺 通过特定物品或者交钱学习技能，不用拜师
        if(npc.isLearnByObject()){
            cmds.add(new EmbeddedCommand("学艺", "learn_by_object", npc.getId()));
        }

        // TODO: 交易命令
        // TODO: 攻击命令
//        if(npc.isCanAttack()){
//            cmds.add(new EmbeddedCommand("攻击", "attack", npc.getId()));
//        }
        // TODO: 副本传送命令
        // TODO：地图传送命令
        return cmds;
    }

    private static void bindEvents(WorldNpcObject obj){
        /*
         * 绑定NPC对应的事件（可用命令）
         * */
        Iterable<EventData> eventData = DbMapper.eventDataRepository.findEventDataByTriggerObject(obj.getDataKey());
        Set<String> events = new HashSet<>();
        for(EventData event : eventData){
            events.add(event.getDataKey());
        }
        obj.setEvents(events);
    }

    private static void bindLootList(WorldNpcObject obj){
        /*
        * @ TODO:设置物品NPC死亡掉落表
        * */
    }

    private static void bindEquipments(WorldNpcObject obj){
        /*
        *  为npc绑定默认的装备
        * */
    }


    private static void clearSkills(WorldNpcObject npc){
        // 删除技能实体
        MongoMapper.skillObjectRepository.removeSkillObjectsByOwner(npc.getId());
        // 清空玩家技能
        npc.setSkills(new HashSet<>());
    }

    private static void bindDefaultSkills(WorldNpcObject npc) throws JsonProcessingException {
        /*
        * 为NPC绑定默认技能
        * 查询npc默认技能记录
        * 创建技能，把技能的id增加到npc的
        * */
        //删除所有旧的技能
        clearSkills(npc);
        // 创建新技能并把新技能的id追加过来
        Set<String> skills = new HashSet<>();
        Iterable<DefaultSkills> defaultSkills = DbMapper.defaultSkillsRepository.findDefaultSkillsByTarget(npc.getDataKey());
        for(DefaultSkills skillRecord : defaultSkills){
            try{
                SkillObject skillObject = SkillObjectManager.create(skillRecord.getSkillKey());
                skillObject.setOwner(npc.getId());
                skillObject.setLevel(skillRecord.getLevel());
                // 设置技能的属性效果
                SkillObjectManager.calculusEffects(npc, null, skillObject);
                MongoMapper.skillObjectRepository.save(skillObject);
                skills.add(skillObject.getId());
                // 如果有子技能就绑定子技能
                SkillObjectManager.bindSubSkills(skillObject, npc.getId(), skillRecord.getLevel());
                // 如果默认技能是可装备的 则直接装备
                if(skillRecord.isEquipped()){
                    String position = skillRecord.getPosition();
                    SkillObjectManager.equipTo(skillObject, npc, position, null);
                }
            }catch (Exception e){
                System.out.println("角色 " + npc.getName() + " 在绑定技能 " + skillRecord.getSkillKey() + "的时候出现异常");
            }

        }
        npc.setSkills(skills);
    }

    public static void getCanTeachSkills(WorldNpcObject npc, PlayerCharacter playerCharacter, Session session) throws JsonProcessingException {
        /*
        * @ 获得npc可以教授的技能列表，这个列表提供给玩家以便于学习技能
        * */
        // 创建一个字典容器，把技能按照categoryType分类，并初始化
        Map<String, Set<SimpleSkill>> skills = new HashMap<>();
        for(String categoryTypeKey : SkillTypeHandler.categoryTypeMapping.keySet()){
            skills.put(categoryTypeKey, new HashSet<>());
        }

        // 遍历目标所有技能，然后把技能放到对应的容器里面去
        if(npc.isTeacher() || npc.isLearnByObject()){
            for(String skillId : npc.getSkills()){
                SkillObject skillObject = MongoMapper.skillObjectRepository.findSkillObjectById(skillId);
                if(skillObject.isPassive()){
                    SimpleSkill skillInfo = new SimpleSkill(skillObject);
                    // 获得技能可以执行的命令
                    skillInfo.setCmds(SkillObjectManager.getAvailableCommands(skillObject, playerCharacter));
                    skills.get(skillObject.getCategoryType()).add(skillInfo);
                }
            }
            session.sendText(JsonResponse.JsonStringResponse(new TeachersSkillMessage(skills)));
        }else{
            session.sendText(JsonResponse.JsonStringResponse(new MsgMessage(String.format(GameWords.NPC_NOT_TEACH, npc.getName()))));
        }
    }
}
