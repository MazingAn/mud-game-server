package com.mud.game.worlddata.db.mappings;

import com.mud.game.worlddata.db.repository.*;
import com.mud.game.worlddata.web.admin.service.SpecificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DbMapper {
    /*
     * 这是所有的仓库映射
     * 仓库会在这里被全部自动实现并开放出去
     * 为了绕开Spring boot 不能对全局静态变量进行@Autowried操作，所有仓库的@AutoWried动作都放在了Setter方法内
     * */

    public static FamilyRepository familyRepository;
    public static GameSettingRepository gameSettingRepository;
    public static HangupObjectRepository hangupObjectRepository;
    public static SchoolRepository schoolRepository;
    public static PlayerTitleRepository playerTitleRepository;
    public static SkillBookBindRepository skillBookBindRepository;
    public static WorldAreaRepository worldAreaRepository;
    public static WorldExitRepository worldExitRepository;
    public static WorldObjectRepository worldObjectRepository;
    public static WorldRoomRepository worldRoomRepository;
    public static GemRepository gemRepository;
    public static SkillBookRepository skillBookRepository;
    public static EquipmentRepository equipmentRepository;
    public static WorldNpcRepository worldNpcRepository;
    public static CharacterModelRepository characterModelRepository;
    public static EventDataRepository eventDataRepository;
    public static ActionAcceptQuestRepository actionAcceptQuestRepository;
    public static ActionAttackRepository actionAttackRepository;
    public static ActionChangeAttrRepository actionChangeAttrRepository;
    public static ActionChangeStepRepository actionChangeStepRepository;
    public static ActionCloseEventRepository actionCloseEventRepository;
    public static ActionDialogueRepository actionDialogueRepository;
    public static ActionLearnSkillRepository actionLearnSkillRepository;
    public static ActionTurnInQuestRepository actionTurnInQuestRepository;
    public static ActionMoveRepository actionMoveRepository;
    public static LootListRepository lootListRepository;
    public static SkillRepository skillRepository;
    public static SkillCategoryTypeRepository skillCategoryTypeRepository;
    public static SkillFunctionTypeRepository skillFunctionTypeRepository;
    public static SkillPositionRepository skillPositionRepository;
    public static DefaultSkillsRepository defaultSkillsRepository;
    public static DefaultEquipmentsRepository defaultEquipmentsRepository;
    public static DefaultObjectsRepository defaultObjectsRepository;
    public static NormalObjectRepository normalObjectRepository;
    public static NpcLearnObjectListRepository npcLearnObjectListRepository;
    public static EquipmentPositionRepository equipmentPositionRepository;
    public static DialogueRepository dialogueRepository;
    public static DialogueSentenceRepository dialogueSentenceRepository;
    public static NpcDialogueRepository npcDialogueRepository;
    public static QuestRepository questRepository;
    public static QuestDependencyRepository questDependencyRepository;
    public static QuestDialogueDependencyRepository questDialogueDependencyRepository;
    public static QuestRewardListRepository questRewardListRepository;
    public static QuestObjectiveRepository questObjectiveRepository;
    public static NpcShopRepository npcShopRepository;
    public static ShopRepository shopRepository;
    public static ShopGoodsRepository shopGoodsRepository;
    public static ObjectTypeRepository objectTypeRepository;
    public static HangupTypeRepository hangupTypeRepository;
    public static WeaponTypeRepository weaponTypeRepository;
    public static TransListRepository transListRepository;
    public static ConsignmentInfomationRepository consignmentInfomationRepository;
    public static CompositeMaterialRepository compositeMaterialRepository;
    public static StrengthenMaterialRepository strengthenMaterialRepository;
    public static QualityMaterialRepository qualityMaterialRepository;
    public static SlotMaterialRepository slotMaterialRepository;


    public static Map<String, SpecificationRepository> modelRepositoryMap = new HashMap<>();


    @Autowired
    public void setGameSettingRepository(GameSettingRepository gameSettingRepository) {
        DbMapper.gameSettingRepository = gameSettingRepository;
        modelRepositoryMap.put("gamesetting", gameSettingRepository);
    }

    @Autowired
    public void setFamilyRepository(FamilyRepository familyRepository) {
        DbMapper.familyRepository = familyRepository;
        modelRepositoryMap.put("family", familyRepository);
    }

    @Autowired
    public void setHangupObjectRepository(HangupObjectRepository hangupObjectRepository) {
        DbMapper.hangupObjectRepository = hangupObjectRepository;
        modelRepositoryMap.put("hangupobject", hangupObjectRepository);
    }

    @Autowired
    public void setSchoolRepository(SchoolRepository schoolRepository) {
        DbMapper.schoolRepository = schoolRepository;
        modelRepositoryMap.put("school", schoolRepository);
    }

    @Autowired
    public void setPlayerTitleRepository(PlayerTitleRepository playerTitleRepository) {
        DbMapper.playerTitleRepository = playerTitleRepository;
        modelRepositoryMap.put("playertitle", playerTitleRepository);
    }

    @Autowired
    public void setSkillBookBindRepository(SkillBookBindRepository skillBookBindRepository) {
        DbMapper.skillBookBindRepository = skillBookBindRepository;
        modelRepositoryMap.put("skillbookbind", skillBookBindRepository);
    }

    @Autowired
    public void setWorldAreaRepository(WorldAreaRepository worldAreaRepository) {
        DbMapper.worldAreaRepository = worldAreaRepository;
        modelRepositoryMap.put("worldarea", worldAreaRepository);
    }

    @Autowired
    public void setWorldExitRepository(WorldExitRepository worldExitRepository) {
        DbMapper.worldExitRepository = worldExitRepository;
        modelRepositoryMap.put("worldexit", worldExitRepository);
    }

    @Autowired
    public void setWorldObjectRepository(WorldObjectRepository worldObjectRepository) {
        DbMapper.worldObjectRepository = worldObjectRepository;
        modelRepositoryMap.put("worldobject", worldObjectRepository);
    }

    @Autowired
    public void setWorldRoomRepository(WorldRoomRepository worldRoomRepository) {
        DbMapper.worldRoomRepository = worldRoomRepository;
        modelRepositoryMap.put("worldroom", worldRoomRepository);
    }

    @Autowired
    public void setGemRepository(GemRepository gemRepository) {
        DbMapper.gemRepository = gemRepository;
        modelRepositoryMap.put("gem", gemRepository);
    }

    @Autowired
    public void setSkillBookRepository(SkillBookRepository skillBookRepository) {
        DbMapper.skillBookRepository = skillBookRepository;
        modelRepositoryMap.put("skillbook", skillBookRepository);
    }

    @Autowired
    public void setEquipmentRepository(EquipmentRepository equipmentRepository) {
        DbMapper.equipmentRepository = equipmentRepository;
        modelRepositoryMap.put("equipment", equipmentRepository);
    }

    @Autowired
    public void setWorldNpcRepository(WorldNpcRepository worldNpcRepository) {
        DbMapper.worldNpcRepository = worldNpcRepository;
        modelRepositoryMap.put("worldnpc", worldNpcRepository);
    }

    @Autowired
    public void setCharacterModelRepository(CharacterModelRepository characterModelRepository) {
        DbMapper.characterModelRepository = characterModelRepository;
        modelRepositoryMap.put("charactermodel", characterModelRepository);
    }

    @Autowired
    public void setEventDataRepository(EventDataRepository eventDataRepository) {
        DbMapper.eventDataRepository = eventDataRepository;
        modelRepositoryMap.put("eventdata", eventDataRepository);
    }

    @Autowired
    public void setActionAcceptQuestRepository(ActionAcceptQuestRepository actionAcceptQuestRepository) {
        DbMapper.actionAcceptQuestRepository = actionAcceptQuestRepository;
        modelRepositoryMap.put("actionacceptquest", actionAcceptQuestRepository);
    }

    @Autowired
    public void setActionAttackRepository(ActionAttackRepository actionAttackRepository) {
        DbMapper.actionAttackRepository = actionAttackRepository;
        modelRepositoryMap.put("actionattack", actionAttackRepository);
    }

    @Autowired
    public void setActionChangeAttrRepository(ActionChangeAttrRepository actionChangeAttrRepository) {
        DbMapper.actionChangeAttrRepository = actionChangeAttrRepository;
        modelRepositoryMap.put("actionchangeattr", actionChangeAttrRepository);
    }

    @Autowired
    public void setActionChangeStepRepository(ActionChangeStepRepository actionChangeStepRepository) {
        DbMapper.actionChangeStepRepository = actionChangeStepRepository;
        modelRepositoryMap.put("actionchangestep", actionChangeStepRepository);
    }

    @Autowired
    public void setActionCloseEventRepository(ActionCloseEventRepository actionCloseEventRepository) {
        DbMapper.actionCloseEventRepository = actionCloseEventRepository;
        modelRepositoryMap.put("actioncloseevent", actionCloseEventRepository);
    }

    @Autowired
    public void setActionDialogueRepository(ActionDialogueRepository actionDialogueRepository) {
        DbMapper.actionDialogueRepository = actionDialogueRepository;
        modelRepositoryMap.put("actiondialogue", actionDialogueRepository);
    }

    @Autowired
    public void setActionLearnSkillRepository(ActionLearnSkillRepository actionLearnSkillRepository) {
        DbMapper.actionLearnSkillRepository = actionLearnSkillRepository;
        modelRepositoryMap.put("actionlearnskill", actionLearnSkillRepository);
    }

    @Autowired
    public void setActionTurnInQuestRepository(ActionTurnInQuestRepository actionTurnInQuestRepository) {
        DbMapper.actionTurnInQuestRepository = actionTurnInQuestRepository;
        modelRepositoryMap.put("actionturninQuest", actionTurnInQuestRepository);
    }

    @Autowired
    public void setActionMoveRepository(ActionMoveRepository actionMoveRepository) {
        DbMapper.actionMoveRepository = actionMoveRepository;
        modelRepositoryMap.put("actionmove", actionMoveRepository);
    }

    @Autowired
    public void setLootListRepository(LootListRepository lootListRepository) {
        DbMapper.lootListRepository = lootListRepository;
        modelRepositoryMap.put("lootlist", lootListRepository);
    }

    @Autowired
    public void setSkillRepository(SkillRepository skillRepository) {
        DbMapper.skillRepository = skillRepository;
        modelRepositoryMap.put("skill", skillRepository);
    }

    @Autowired
    public void setSkillCategoryTypeRepository(SkillCategoryTypeRepository skillCategoryTypeRepository) {
        DbMapper.skillCategoryTypeRepository = skillCategoryTypeRepository;
        modelRepositoryMap.put("skillCategorytype", skillCategoryTypeRepository);
    }

    @Autowired
    public void setSkillFunctionTypeRepository(SkillFunctionTypeRepository skillFunctionTypeRepository) {
        DbMapper.skillFunctionTypeRepository = skillFunctionTypeRepository;
        modelRepositoryMap.put("skillFunctiontype", skillFunctionTypeRepository);
    }

    @Autowired
    public void setSkillPositionRepository(SkillPositionRepository skillPositionRepository) {
        DbMapper.skillPositionRepository = skillPositionRepository;
        modelRepositoryMap.put("skillposition", skillPositionRepository);
    }

    @Autowired
    public void setDefaultSkillsRepository(DefaultSkillsRepository defaultSkillsRepository) {
        DbMapper.defaultSkillsRepository = defaultSkillsRepository;
        modelRepositoryMap.put("defaultskills", defaultSkillsRepository);
    }

    @Autowired
    public void setDefaultEquipmentsRepository(DefaultEquipmentsRepository defaultEquipmentsRepository) {
        DbMapper.defaultEquipmentsRepository = defaultEquipmentsRepository;
        modelRepositoryMap.put("defaultequipments", defaultEquipmentsRepository);
    }

    @Autowired
    public void setDefaultObjectsRepository(DefaultObjectsRepository defaultObjectsRepository) {
        DbMapper.defaultObjectsRepository = defaultObjectsRepository;
        modelRepositoryMap.put("defaultobjects", defaultObjectsRepository);
    }

    @Autowired
    public void setNormalObjectRepository(NormalObjectRepository normalObjectRepository) {
        DbMapper.normalObjectRepository = normalObjectRepository;
        modelRepositoryMap.put("normalobject", normalObjectRepository);
    }

    @Autowired
    public void setNpcLearnObjectListRepository(NpcLearnObjectListRepository npcLearnObjectListRepository) {
        DbMapper.npcLearnObjectListRepository = npcLearnObjectListRepository;
        modelRepositoryMap.put("npclearnobjectlist", npcLearnObjectListRepository);
    }

    @Autowired
    public void setEquipmentPositionRepository(EquipmentPositionRepository equipmentPositionRepository) {
        DbMapper.equipmentPositionRepository = equipmentPositionRepository;
        modelRepositoryMap.put("equipmentposition", equipmentPositionRepository);
    }

    @Autowired
    public void setDialogueRepository(DialogueRepository dialogueRepository) {
        DbMapper.dialogueRepository = dialogueRepository;
        modelRepositoryMap.put("dialogue", dialogueRepository);
    }

    @Autowired
    public void setDialogueSentenceRepository(DialogueSentenceRepository dialogueSentenceRepository) {
        DbMapper.dialogueSentenceRepository = dialogueSentenceRepository;
        modelRepositoryMap.put("dialoguesentence", dialogueSentenceRepository);
    }

    @Autowired
    public void setNpcDialogueRepository(NpcDialogueRepository npcDialogueRepository) {
        DbMapper.npcDialogueRepository = npcDialogueRepository;
        modelRepositoryMap.put("npcdialogue", npcDialogueRepository);
    }

    @Autowired
    public void setQuestRepository(QuestRepository questRepository) {
        DbMapper.questRepository = questRepository;
        modelRepositoryMap.put("quest", questRepository);
    }

    @Autowired
    public void setQuestDependencyRepository(QuestDependencyRepository questDependencyRepository) {
        DbMapper.questDependencyRepository = questDependencyRepository;
        modelRepositoryMap.put("questdependency", questDependencyRepository);
    }

    @Autowired
    public void setQuestDialogueDependencyRepository(QuestDialogueDependencyRepository questDialogueDependencyRepository) {
        DbMapper.questDialogueDependencyRepository = questDialogueDependencyRepository;
        modelRepositoryMap.put("questdialoguedependency", questDialogueDependencyRepository);
    }

    @Autowired
    public void setQuestRewardListRepository(QuestRewardListRepository questRewardListRepository) {
        DbMapper.questRewardListRepository = questRewardListRepository;
        modelRepositoryMap.put("questrewardlist", questRewardListRepository);
    }

    @Autowired
    public void setQuestObjectiveRepository(QuestObjectiveRepository questObjectiveRepository) {
        DbMapper.questObjectiveRepository = questObjectiveRepository;
        modelRepositoryMap.put("questobjective", questObjectiveRepository);
    }

    @Autowired
    public void setNpcShopRepository(NpcShopRepository npcShopRepository) {
        DbMapper.npcShopRepository = npcShopRepository;
        modelRepositoryMap.put("npcshop", npcShopRepository);
    }

    @Autowired
    public void setShopRepository(ShopRepository shopRepository) {
        DbMapper.shopRepository = shopRepository;
        modelRepositoryMap.put("shop", shopRepository);
    }

    @Autowired
    public void setShopGoodsRepository(ShopGoodsRepository shopGoodsRepository) {
        DbMapper.shopGoodsRepository = shopGoodsRepository;
        modelRepositoryMap.put("shopgoods", shopGoodsRepository);
    }

    @Autowired
    public void setObjectTypeRepository(ObjectTypeRepository objectTypeRepository) {
        DbMapper.objectTypeRepository = objectTypeRepository;
        modelRepositoryMap.put("objecttype", objectTypeRepository);
    }

    @Autowired
    public void setHangupTypeRepository(HangupTypeRepository hangupTypeRepository) {
        DbMapper.hangupTypeRepository = hangupTypeRepository;
        modelRepositoryMap.put("hanguptype", hangupTypeRepository);
    }

    @Autowired
    public void setWeaponTypeRepository(WeaponTypeRepository weaponTypeRepository) {
        DbMapper.weaponTypeRepository = weaponTypeRepository;
        modelRepositoryMap.put("weapontype", weaponTypeRepository);
    }

    @Autowired
    public void setTransListRepository(TransListRepository transListRepository) {
        DbMapper.transListRepository = transListRepository;
        modelRepositoryMap.put("translist", transListRepository);
    }

    @Autowired
    public void setConsignmentInfomationRepository(ConsignmentInfomationRepository consignmentInfomationRepository) {
        DbMapper.consignmentInfomationRepository = consignmentInfomationRepository;
        modelRepositoryMap.put("consignmentinfomation", consignmentInfomationRepository);
    }

    @Autowired
    public void setCompositeMaterialRepository(CompositeMaterialRepository compositeMaterialRepository) {
        DbMapper.compositeMaterialRepository = compositeMaterialRepository;
        modelRepositoryMap.put("compositematerial", compositeMaterialRepository);
    }

    @Autowired
    public void setStrengthenMaterialRepository(StrengthenMaterialRepository strengthenMaterialRepository) {
        DbMapper.strengthenMaterialRepository = strengthenMaterialRepository;
        modelRepositoryMap.put("strengthenmaterial", strengthenMaterialRepository);
    }

    @Autowired
    public void setQualityMaterialRepository(QualityMaterialRepository qualityMaterialRepository) {
        DbMapper.qualityMaterialRepository = qualityMaterialRepository;
        modelRepositoryMap.put("qualitymaterial", qualityMaterialRepository);
    }

    @Autowired
    public void setSlotMaterialRepository(SlotMaterialRepository slotMaterialRepository) {
        DbMapper.slotMaterialRepository = slotMaterialRepository;
        modelRepositoryMap.put("slotmaterial", slotMaterialRepository);
    }
}

