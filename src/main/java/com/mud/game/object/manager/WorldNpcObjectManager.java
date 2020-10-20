package com.mud.game.object.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.Mongo;
import com.mud.game.combat.NpcBoundItemInfo;
import com.mud.game.handler.QuestStatusHandler;
import com.mud.game.handler.SkillTypeHandler;
import com.mud.game.handler.TrophyHandler;
import com.mud.game.messages.MsgMessage;
import com.mud.game.messages.TeachersSkillMessage;
import com.mud.game.messages.ToastMessage;
import com.mud.game.object.account.Player;
import com.mud.game.object.builder.CommonObjectBuilder;
import com.mud.game.object.supertypeclass.CommonCharacter;
import com.mud.game.object.supertypeclass.CommonObject;
import com.mud.game.object.typeclass.*;
import com.mud.game.structs.*;
import com.mud.game.utils.jsonutils.Attr2Map;
import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.utils.resultutils.GameWords;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.*;
import com.mud.game.worlddata.db.models.supermodel.BaseCommonObject;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.yeauty.pojo.Session;

import java.util.*;

import static com.mud.game.constant.PostConstructConstant.CRIME_VALUE_CMDS;
import static com.mud.game.constant.PostConstructConstant.CUT_BACKCRIME_NPC_DATAKEY;
import static com.mud.game.object.manager.GameCharacterManager.npcBoundItemSet;

public class WorldNpcObjectManager {

    /**
     * 创建npc
     */
    public static WorldNpcObject build(WorldNpc template) {
        WorldNpcObject obj = new WorldNpcObject();
        obj.setDataKey(template.getDataKey());
        obj.setDescription(template.getDescription());
        obj.setLocation(template.getLocation());
        obj.setTeacher(template.getIsTeacher());
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
        obj.setRebornTime(template.getRebornTime());
        obj.setCanAttack(template.isCanAttack());
        obj.setTransfer(template.isTransfer());
        obj.setCrimeControlCmd(template.getCrimeControlCmd());
        obj.setCanAttackByCrime(template.getCanAttackByCrime());
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
        // 加载掉落信息
        bindLootList(obj);
        // 加载绑定的事件
        bindEvents(obj);
        //加载默认对话
        bindDialogues(obj);
        // 绑定npc商店
        bindShops(obj);
        // 把npc放到房间内
        WorldRoomObject room = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectByDataKey(obj.getLocation());
        WorldRoomObjectManager.updateNpc(room, obj);
        // 设置NPC的基本属性
        resetHealth(obj);
        return obj;
    }

    public static void update(WorldNpcObject obj, WorldNpc template) {
        obj.setDescription(template.getDescription());
        obj.setLocation(template.getLocation());
        obj.setTeacher(template.getIsTeacher());
        obj.setLearnByObject(template.isCanLearnByObject());
        obj.setTeachCondition(template.getTeachCondition());
        obj.setRebornTime(template.getRebornTime());
        obj.setCrimeControlCmd(template.getCrimeControlCmd());
        obj.setCanAttackByCrime(template.getCanAttackByCrime());
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
        obj.setCanAttack(template.isCanAttack());
        obj.setTransfer(template.isTransfer());
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
        // 加载对话
        bindDialogues(obj);
        // 绑定npc商店
        bindShops(obj);
        // 把npc放到房间内
        WorldRoomObject room = MongoMapper.worldRoomObjectRepository.findWorldRoomObjectByDataKey(obj.getLocation());
        WorldRoomObjectManager.updateNpc(room, obj);
        // 设置NPC的基本属性
        resetHealth(obj);
    }

    public static void resetHealth(WorldNpcObject obj) {
        if (obj.getLimit_hp() == 0) obj.setLimit_hp(200);
        if (obj.getLimit_mp() == 0) obj.setLimit_mp(200);
        obj.setMax_hp(obj.getLimit_hp());
        obj.setHp(obj.getMax_hp());
        obj.setMax_mp(obj.getLimit_mp());
        obj.setMp(obj.getMp());
    }

    /**
     * 玩家查看本实例时候的回调
     *
     * @param npc             玩家查看的NPC
     * @param playerCharacter 玩家
     * @param session         玩家通信隧道
     */
    public static void onPlayerLook(WorldNpcObject npc, PlayerCharacter playerCharacter, Session session) {
        /*
         * @ 当玩家查看NPC的时候返回NPC信息和可用命令
         * */
        Map<String, Object> lookMessage = new HashMap<>();
        //  查看门派
        if (null != npc.getSchool()) {
            School school = DbMapper.schoolRepository.findSchoolByDataKey(npc.getSchool());
            if (null != school) {
                npc.setSchool(school.getName());
            }
        }

        NpcAppearance appearance = new NpcAppearance(npc);
        // 设置玩家可以对此物体执行的命令
        appearance.setCmds(WorldNpcObjectManager.getAvailableCommands(npc, playerCharacter));
        lookMessage.put("look_obj", appearance);
        session.sendText(JsonResponse.JsonStringResponse(lookMessage));
    }

    /**
     * 获取NPC可执行的命令
     * NPC对应的命令还是比较多的，不同身份的NPC对不同玩家会有不通的可执行命令
     */
    public static List<EmbeddedCommand> getAvailableCommands(WorldNpcObject npc, PlayerCharacter playerCharacter) {
        List<EmbeddedCommand> cmds = new ArrayList<>();
        if (npc.getHp() <= 0) {
            cmds.add(new EmbeddedCommand("查看", "pick_up_list", npc.getId()));
            return cmds;
        }
        if (npc.canAttack && npc.getHp() > 0) {
            cmds.add(new EmbeddedCommand("攻击", "attack", npc.getId()));
        }
        //出售
        NpcDangPu npcDangPu = DbMapper.npcDangPuRepository.findNpcDangPuByNpc(npc.getDataKey());
        if (null != npcDangPu) {
            cmds.add(new EmbeddedCommand("出售", "open_pawn_shop", npc.getId()));
        }
        //减少犯罪值
        if (npc.getDataKey().equals(CUT_BACKCRIME_NPC_DATAKEY)) {
            cmds.add(new EmbeddedCommand("减少犯罪值", "cut_back_crimevalue", npc.getId()));
        }
        //如果玩家犯罪值大于阈值，则城镇不能操作npc
        // 判断npc是否可以无操作（城镇npc）
        if (playerCharacter.getCrimeValue() >= CRIME_VALUE_CMDS && npc.getCrimeControlCmd()) {
            return cmds;
        }
        //切磋
        if (npc.canAttack && npc.getHp() > 0) {
            cmds.add(new EmbeddedCommand("切磋", "attack_test_npc", npc.getId()));
        }
        // 拜师命令
        if (npc.isTeacher() && !(playerCharacter.getTeacher().equals(npc.getDataKey()))) {
            cmds.add(new EmbeddedCommand("拜师", "find_teacher", npc.getId()));
        }
        // 请教与出师命令,师门任务命令
        if (npc.isTeacher() && playerCharacter.getTeacher().equals(npc.getDataKey())) {
            cmds.add(new EmbeddedCommand("请教", "learn_from_teacher", npc.getId()));
            cmds.add(new EmbeddedCommand("出师", "left_teacher", npc.getId()));
            cmds.add(new EmbeddedCommand("师门任务", "teacher_quest", npc.getId()));
        }
        //cmds.add(new EmbeddedCommand("出师", "left_teacher", npc.getId()));

        // 学艺 通过特定物品或者交钱学习技能，不用拜师
        if (npc.isLearnByObject()) {
            cmds.add(new EmbeddedCommand("学艺", "learn_by_object", npc.getId()));
        }
        // 对话
        if (!npc.getDialogues().isEmpty()) {
            cmds.add(new EmbeddedCommand("交谈", "talk", npc.getId()));
        }
        // 交易命令
        if (!npc.getShops().isEmpty()) {
            for (String shopKey : npc.getShops()) {
                Shop shop = DbMapper.shopRepository.findShopByDataKey(shopKey);
                cmds.add(new EmbeddedCommand(shop.getName(), "shopping", shop.getDataKey()));
            }
        }

        // TODO: 副本传送命令

        // 地图传送命令
        if (npc.transfer) {
            for (TransList record : DbMapper.transListRepository.findTransListByNpc(npc.getDataKey())) {
                WorldRoom room = DbMapper.worldRoomRepository.findWorldRoomByDataKey(record.getRoom());
                if (room != null) {
                    Map<String, String> args = new HashMap<>();
                    args.put("npc", npc.getDataKey());
                    args.put("room", room.getDataKey());
                    WorldArea area = DbMapper.worldAreaRepository.findWorldAreaByDataKey(room.getLocation());
                    String displayTargetName = (area != null) ? area.getName() : room.getName();
                    cmds.add(new EmbeddedCommand(displayTargetName, "trans", args));
                }
            }
        }
        return cmds;
    }

    /**
     * 检测NPC是否能够提交任务给玩家
     */
    public static boolean canProvideQuest(WorldNpcObject npc, PlayerCharacter playerCharacter) {
        for (String dialogueKey : npc.getDialogues()) {
            Iterable<QuestDialogueDependency> questDialogueDependencies = DbMapper.questDialogueDependencyRepository.findQuestDialogueDependenciesByDialogue(dialogueKey);
            for (QuestDialogueDependency dependency : questDialogueDependencies) {
                if (GameQuestManager.canAcceptQuest(playerCharacter, dependency.getDependency())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 检测NPC处是否可以提交任务
     *
     * @param playerCharacter 玩家
     * @param npc             角色
     * @return boolean 是否可以提交任务
     */
    public static boolean canTurnInQuest(WorldNpcObject npc, PlayerCharacter playerCharacter) {
        for (String questObjectId : playerCharacter.getCurrentQuests()) {
            QuestObject questObject = MongoMapper.questObjectRepository.findQuestObjectById(questObjectId);
            if (GameQuestManager.isQuestAccomplished(playerCharacter, questObject.getDataKey())) {
                for (String dialogueKey : npc.getDialogues()) {
                    Iterable<QuestDialogueDependency> questDialogueDependencies = DbMapper.questDialogueDependencyRepository.findQuestDialogueDependenciesByDialogue(dialogueKey);
                    for (QuestDialogueDependency dependency : questDialogueDependencies) {
                        if (dependency.getType().equals(QuestStatusHandler.ACCOMPLISHED) && dependency.getDialogue().equals(dialogueKey)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 绑定默认事件
     *
     * @param obj npc对象
     */
    private static void bindEvents(WorldNpcObject obj) {

        Iterable<EventData> eventData = DbMapper.eventDataRepository.findEventDataByTriggerObject(obj.getDataKey());
        Set<String> events = new HashSet<>();
        for (EventData event : eventData) {
            events.add(event.getDataKey());
        }
        obj.setEvents(events);
    }

    /**
     * 设置物品NPC死亡掉落表
     */
    private static void bindLootList(WorldNpcObject obj) {

    }

    /**
     * 绑定对话
     *
     * @param obj npc对象
     */
    private static void bindDialogues(WorldNpcObject obj) {
        Set<String> dialogues = new HashSet<>();
        Iterable<NpcDialogue> npcDialogues = DbMapper.npcDialogueRepository.findNpcDialoguesByNpc(obj.getDataKey());
        for (NpcDialogue npcDialogue : npcDialogues) {
            dialogues.add(npcDialogue.getDialogue());
        }
        obj.setDialogues(dialogues);
    }

    /**
     * 绑定NPC与商店
     */
    private static void bindShops(WorldNpcObject obj) {
        Set<String> shops = obj.getShops();
        for (NpcShop npcShop : DbMapper.npcShopRepository.findNpcShopsByNpc(obj.getDataKey())) {
            shops.add(npcShop.getShop());
        }
        obj.setShops(shops);
    }

    private static void bindEquipments(WorldNpcObject obj) {
        /*
         *  为npc绑定默认的装备
         * */
    }

    private static void clearSkills(WorldNpcObject npc) {
        // 删除技能实体
        MongoMapper.skillObjectRepository.removeSkillObjectsByOwner(npc.getId());
        // 清空玩家技能
        npc.setSkills(new HashSet<>());
    }

    private static void bindDefaultSkills(WorldNpcObject npc) {
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
        for (DefaultSkills skillRecord : defaultSkills) {
            try {
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
                if (skillRecord.isEquipped()) {
                    String position = skillRecord.getPosition();
                    SkillObjectManager.equipTo(skillObject, npc, position, null);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("角色 " + npc.getName() + " 在绑定技能 " + skillRecord.getSkillKey() + "的时候出现异常");
            }

        }
        npc.setSkills(skills);
    }

    public static void getCanTeachSkills(WorldNpcObject npc, PlayerCharacter playerCharacter, Session session) {
        /*
         * @ 获得npc可以教授的技能列表，这个列表提供给玩家以便于学习技能
         * */
        // 创建一个字典容器，把技能按照categoryType分类，并初始化
        Map<String, Set<SimpleSkill>> skills = new HashMap<>();
        for (String categoryTypeKey : SkillTypeHandler.categoryTypeMapping.keySet()) {
            skills.put(categoryTypeKey, new HashSet<>());
        }

        // 遍历目标所有技能，然后把技能放到对应的容器里面去
        if (npc.isTeacher() || npc.isLearnByObject()) {
            for (String skillId : npc.getSkills()) {
                SkillObject skillObject = MongoMapper.skillObjectRepository.findSkillObjectById(skillId);
                if (skillObject.isPassive()) {
                    SimpleSkill skillInfo = new SimpleSkill(skillObject);
                    // 获得技能可以执行的命令
                    skillInfo.setCmds(SkillObjectManager.getAvailableCommands(skillObject, playerCharacter));
                    skills.get(skillObject.getCategoryType()).add(skillInfo);
                }
            }
            session.sendText(JsonResponse.JsonStringResponse(new TeachersSkillMessage(skills)));
        } else {
            session.sendText(JsonResponse.JsonStringResponse(new MsgMessage(String.format(GameWords.NPC_NOT_TEACH, npc.getName()))));
        }
    }

    /**
     * npc死亡生成掉落物品
     *
     * @param character
     */
    public static void getTrophy(CommonCharacter character, CommonCharacter commonCharacter) {
        List<NpcBoundItem> npcBoundItemList = DbMapper.npcBoundItemRepository.findNpcBoundItemByNpcDataKey(character.getDataKey());
        if (npcBoundItemList.size() > 0) {
            Random random = new Random();
            Map<String, Integer> npcBoundItemMap = new HashMap<>();
            for (int i = 0; i < npcBoundItemList.size(); i++) {
                float a = random.nextFloat();
                // 检查获取概率a
                if (a < npcBoundItemList.get(i).getAcquisitionProbability()) {
                    BaseCommonObject baseCommonObject = CommonObjectBuilder.findObjectTemplateByDataKey(npcBoundItemList.get(i).getObjectDataKey());
                    if (baseCommonObject != null && baseCommonObject.getCategory().equals("TYPE_OBJECTTYPE_EQUIPMENT")) {
                        //生成装备强化等级
                        int level = randomInterval(npcBoundItemList.get(i).getLevelMinLevel(), npcBoundItemList.get(i).getLevelMaxLevel());
                        int quality = randomInterval(npcBoundItemList.get(i).getQualityMinLevel(), npcBoundItemList.get(i).getQualityMaxLevel());
                        EquipmentObject equipmentObject = MongoMapper.equipmentObjectRepository.save(EquipmentObjectManager.createSpecifyLevelAndQuality(npcBoundItemList.get(i).getObjectDataKey(), level, quality));
                        npcBoundItemMap.put(equipmentObject.getId(), 1);
                    }
                }
            }
            npcBoundItemSet.put(character.getId(), new NpcBoundItemInfo(new Date(), commonCharacter.getId(), npcBoundItemMap));
        }
    }

    //生成区间内随机数
    private static int randomInterval(int levelMinLevel, int levelMaxLevel) {
        Random random = new Random();
        return random.nextInt(levelMaxLevel - levelMinLevel + 1) + levelMinLevel;
    }

    /**
     * 尸体是否已复活、在固定时间内只有击杀者可以拾取
     *
     * @param caller
     * @param worldNpcObject
     * @param npcBoundItemInfo
     * @return
     */
    public static boolean getTrophyCheck(PlayerCharacter caller, WorldNpcObject worldNpcObject, NpcBoundItemInfo npcBoundItemInfo) {
        if (worldNpcObject.getHp() > 0) {
            caller.msg(worldNpcObject.getName() + "已复活！");
            return false;
        }
        //判断当前时间是否在开放拾取的时间内
        if (calLastedTime(npcBoundItemInfo.getCreateTime()) <= 50) {
            if (!caller.getId().equals(npcBoundItemInfo.getKillerId())) {
                return false;
            }
        }
        return true;
    }


    public static boolean getTrophyCheckOne(PlayerCharacter caller, WorldNpcObject worldNpcObject, NpcBoundItemInfo npcBoundItemInfo, String objectDataKey) {
        if (worldNpcObject.getHp() > 0) {
            caller.msg(worldNpcObject.getName() + "已复活！");
            return false;
        }
        //判断当前时间是否在开放拾取的时间内
        if (calLastedTime(npcBoundItemInfo.getCreateTime()) <= 50) {
            if (!caller.getId().equals(npcBoundItemInfo.getKillerId())) {
                return false;
            }
        }
        if (null == npcBoundItemInfo.getNpcBoundItemMap() || !npcBoundItemInfo.getNpcBoundItemMap().containsKey(objectDataKey)) {
            return false;
        }
        return true;
    }

    //判断当前时间是否在开放拾取的时间内
    public static int calLastedTime(Date startDate) {
        long a = new Date().getTime();
        long b = startDate.getTime();
        int c = (int) ((a - b) / 1000);
        return c;
    }
}
