package com.mud.game.worlddata.db.mappings;

import com.mud.game.worlddata.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    public static DrugAndFoodRepository drugAndFoodRepository;
    public static SkillRepository skillRepository;


    @Autowired
    public void setGameSettingRepository(GameSettingRepository gameSettingRepository) {
        DbMapper.gameSettingRepository = gameSettingRepository;
    }

    @Autowired
    public void setFamilyRepository(FamilyRepository familyRepository) {
        DbMapper.familyRepository = familyRepository;
    }

    @Autowired
    public void setHangupObjectRepository(HangupObjectRepository hangupObjectRepository) {
        DbMapper.hangupObjectRepository = hangupObjectRepository;
    }

    @Autowired
    public void setSchoolRepository(SchoolRepository schoolRepository) {
        DbMapper.schoolRepository = schoolRepository;
    }

    @Autowired
    public void setPlayerTitleRepository(PlayerTitleRepository playerTitleRepository) {
        DbMapper.playerTitleRepository = playerTitleRepository;
    }

    @Autowired
    public void setSkillBookBindRepository(SkillBookBindRepository skillBookBindRepository) {
        DbMapper.skillBookBindRepository = skillBookBindRepository;
    }

    @Autowired
    public void setWorldAreaRepository(WorldAreaRepository worldAreaRepository) {
        DbMapper.worldAreaRepository = worldAreaRepository;
    }

    @Autowired
    public void setWorldExitRepository(WorldExitRepository worldExitRepository) {
        DbMapper.worldExitRepository = worldExitRepository;
    }

    @Autowired
    public void setWorldObjectRepository(WorldObjectRepository worldObjectRepository) {
        DbMapper.worldObjectRepository = worldObjectRepository;
    }

    @Autowired
    public void setWorldRoomRepository(WorldRoomRepository worldRoomRepository) {
        DbMapper.worldRoomRepository = worldRoomRepository;
    }

    @Autowired
    public  void setGemRepository(GemRepository gemRepository) {
        DbMapper.gemRepository = gemRepository;
    }

    @Autowired
    public  void setSkillBookRepository(SkillBookRepository skillBookRepository) {
        DbMapper.skillBookRepository = skillBookRepository;
    }

    @Autowired
    public  void setEquipmentRepository(EquipmentRepository equipmentRepository) {
        DbMapper.equipmentRepository = equipmentRepository;
    }

    @Autowired
    public  void setWorldNpcRepository(WorldNpcRepository worldNpcRepository) {
        DbMapper.worldNpcRepository = worldNpcRepository;
    }

    @Autowired
    public void setCharacterModelRepository(CharacterModelRepository characterModelRepository) {
        DbMapper.characterModelRepository = characterModelRepository;
    }

    @Autowired
    public void setEventDataRepository(EventDataRepository eventDataRepository) {
        DbMapper.eventDataRepository = eventDataRepository;
    }

    @Autowired
    public void setActionAcceptQuestRepository(ActionAcceptQuestRepository actionAcceptQuestRepository) {
        DbMapper.actionAcceptQuestRepository = actionAcceptQuestRepository;
    }

    @Autowired
    public void setActionAttackRepository(ActionAttackRepository actionAttackRepository) {
        DbMapper.actionAttackRepository = actionAttackRepository;
    }

    @Autowired
    public void setActionChangeAttrRepository(ActionChangeAttrRepository actionChangeAttrRepository) {
        DbMapper.actionChangeAttrRepository = actionChangeAttrRepository;
    }

    @Autowired
    public void setActionChangeStepRepository(ActionChangeStepRepository actionChangeStepRepository) {
        DbMapper.actionChangeStepRepository = actionChangeStepRepository;
    }

    @Autowired
    public void setActionCloseEventRepository(ActionCloseEventRepository actionCloseEventRepository) {
        DbMapper.actionCloseEventRepository = actionCloseEventRepository;
    }

    @Autowired
    public void setActionDialogueRepository(ActionDialogueRepository actionDialogueRepository) {
        DbMapper.actionDialogueRepository = actionDialogueRepository;
    }

    @Autowired
    public void setActionLearnSkillRepository(ActionLearnSkillRepository actionLearnSkillRepository) {
        DbMapper.actionLearnSkillRepository = actionLearnSkillRepository;
    }

    @Autowired
    public void setActionTurnInQuestRepository(ActionTurnInQuestRepository actionTurnInQuestRepository) {
        DbMapper.actionTurnInQuestRepository = actionTurnInQuestRepository;
    }

    @Autowired
    public void setActionMoveRepository(ActionMoveRepository actionMoveRepository) {
        DbMapper.actionMoveRepository = actionMoveRepository;
    }

    @Autowired
    public void setLootListRepository(LootListRepository lootListRepository) {
        DbMapper.lootListRepository = lootListRepository;
    }

    @Autowired
    public void setDrugAndFoodRepository(DrugAndFoodRepository drugAndFoodRepository) {
        DbMapper.drugAndFoodRepository = drugAndFoodRepository;
    }

    @Autowired
    public void setSkillRepository(SkillRepository skillRepository) {
        DbMapper.skillRepository = skillRepository;
    }
}

