package com.mud.game.utils.datafile.exportor;

import com.mud.game.worlddata.db.mappings.DbMapper;
import org.springframework.data.repository.CrudRepository;


/**
 * 数据仓库的映射，因为自动注入的问题，无法通过反射操作对应的数据仓库
 * 所以在这里手动判断
 * 下面的name在导出的时候就设置与对应的模型类名称相同
 */

public class ExportorFatory {

    public static Exportor createExportor(String outFilePath, String tableName, String type) {

        switch (tableName){
            case "Equipment":
                return new Exportor(DbMapper.equipmentRepository, outFilePath, tableName, type);
            case "Family":
                return new Exportor(DbMapper.familyRepository, outFilePath, tableName, type);
            case "GameSetting":
                return new Exportor(DbMapper.gameSettingRepository, outFilePath, tableName, type);
            case "CharacterModel":
                return new Exportor(DbMapper.characterModelRepository, outFilePath, tableName, type);
            case "Gem":
                return new Exportor(DbMapper.gemRepository, outFilePath, tableName, type);
            case "HangupObject":
                return new Exportor(DbMapper.hangupObjectRepository, outFilePath, tableName, type);
            case "PlayerTitle":
                return new Exportor(DbMapper.playerTitleRepository, outFilePath, tableName, type);
            case "School":
                return new Exportor(DbMapper.schoolRepository, outFilePath, tableName, type);
            case "SkillBook":
                return new Exportor(DbMapper.skillBookRepository, outFilePath, tableName, type);
            case "SkillBookBind":
                return new Exportor(DbMapper.skillBookBindRepository, outFilePath, tableName, type);
            case "WorldArea":
                return new Exportor(DbMapper.worldAreaRepository, outFilePath, tableName, type);
            case "WorldExit":
                return new Exportor(DbMapper.worldExitRepository, outFilePath, tableName, type);
            case "WorldNpc":
                return new Exportor(DbMapper.worldNpcRepository, outFilePath, tableName, type);
            case "WorldObject":
                return new Exportor(DbMapper.worldObjectRepository, outFilePath, tableName, type);
            case "WorldRoom":
                return new Exportor(DbMapper.worldRoomRepository, outFilePath, tableName, type);
            case "EventData":
                return new Exportor(DbMapper.eventDataRepository, outFilePath, tableName, type);
            case "ActionAttack":
                return new Exportor(DbMapper.actionAttackRepository, outFilePath, tableName, type);
            case "ActionDialogue":
                return new Exportor(DbMapper.actionDialogueRepository, outFilePath, tableName, type);
            case "ActionMove":
                return new Exportor(DbMapper.actionMoveRepository, outFilePath, tableName, type);
            case "ActionAcceptQuest":
                return new Exportor(DbMapper.actionAcceptQuestRepository, outFilePath, tableName, type);
            case "ActionChangeAttr":
                return new Exportor(DbMapper.actionChangeAttrRepository, outFilePath, tableName, type);
            case "ActionChangeStep":
                return new Exportor(DbMapper.actionChangeStepRepository, outFilePath, tableName, type);
            case "ActionCloseEvent":
                return new Exportor(DbMapper.actionCloseEventRepository, outFilePath, tableName, type);
            case "ActionLearnSkill":
                return new Exportor(DbMapper.actionLearnSkillRepository, outFilePath, tableName, type);
            case "ActionTurnInQuest":
                return new Exportor(DbMapper.actionTurnInQuestRepository, outFilePath, tableName, type);
            case "LootList":
                return new Exportor(DbMapper.lootListRepository, outFilePath, tableName, type);
            case "Skill":
                return new Exportor(DbMapper.skillRepository, outFilePath, tableName, type);
            case "SkillCategoryType":
                return new Exportor(DbMapper.skillCategoryTypeRepository, outFilePath, tableName, type);
            case "SkillFunctionType":
                return new Exportor(DbMapper.skillFunctionTypeRepository, outFilePath, tableName, type);
            case "SkillPosition":
                return new Exportor(DbMapper.skillPositionRepository, outFilePath, tableName, type);
            case "EquipmentPosition":
                return new Exportor(DbMapper.equipmentPositionRepository, outFilePath, tableName, type);
            case "DefaultSkills":
                return new Exportor(DbMapper.defaultSkillsRepository, outFilePath, tableName, type);
            case "DefaultEquipments":
                return new Exportor(DbMapper.defaultEquipmentsRepository, outFilePath, tableName, type);
            case "DefaultObjects":
                return new Exportor(DbMapper.defaultObjectsRepository, outFilePath, tableName, type);
            case "NormalObject":
                return new Exportor(DbMapper.normalObjectRepository, outFilePath, tableName, type);
            case "NpcLearnObjectList":
                return new Exportor(DbMapper.npcLearnObjectListRepository, outFilePath, tableName, type);
            case "Dialogue":
                return new Exportor(DbMapper.dialogueRepository, outFilePath, tableName, type);
            case "DialogueSentence":
                return new Exportor(DbMapper.dialogueSentenceRepository, outFilePath, tableName, type);
            case "NpcDialogue":
                return new Exportor(DbMapper.npcDialogueRepository, outFilePath, tableName, type);
            case "Quest":
                return new Exportor(DbMapper.questRepository, outFilePath, tableName, type);
            case "QuestDependency":
                return new Exportor(DbMapper.questDependencyRepository, outFilePath, tableName, type);
            case "QuestDialogueDependency":
                return new Exportor(DbMapper.questDialogueDependencyRepository, outFilePath, tableName, type);
            case "QuestRewardList":
                return new Exportor(DbMapper.questRewardListRepository, outFilePath, tableName, type);
            case "QuestObjective":
                return new Exportor(DbMapper.questObjectiveRepository, outFilePath, tableName, type);
            case "Shop":
                return new Exportor(DbMapper.shopRepository, outFilePath, tableName, type);
            case "NpcShop":
                return new Exportor(DbMapper.npcShopRepository, outFilePath, tableName, type);
            case "ShopGoods":
                return new Exportor(DbMapper.shopGoodsRepository, outFilePath, tableName, type);
            default:
                return null;
        }

    }

}
