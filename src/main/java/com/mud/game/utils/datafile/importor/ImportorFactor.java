package com.mud.game.utils.datafile.importor;

import com.mud.game.utils.datafile.exportor.Exportor;
import com.mud.game.worlddata.db.mappings.DbMapper;

import java.io.InputStream;

public class ImportorFactor {
    /*
     * 数据仓库的映射，因为自动注入的问题，无法通过反射操作对应的数据仓库
     * 所以在这里手动判断
     * 下面的name在导出的时候就设置与对应的模型类名称相同
     */

    public static Importor createImportor(InputStream stream, String fileName) {

        String[] fileNameSplit = fileName.split("\\.");
        String name = fileNameSplit[0];
        switch (name) {
            case "Equipment":
                return new Importor(stream, fileName, DbMapper.equipmentRepository);
            case "Family":
                return new Importor(stream, fileName, DbMapper.familyRepository);
            case "GameSetting":
                return new Importor(stream, fileName, DbMapper.gameSettingRepository);
            case "CharacterModel":
                return new Importor(stream, fileName, DbMapper.characterModelRepository);
            case "Gem":
                return new Importor(stream, fileName, DbMapper.gemRepository);
            case "HangupObject":
                return new Importor(stream, fileName, DbMapper.hangupObjectRepository);
            case "PlayerTitle":
                return new Importor(stream, fileName, DbMapper.playerTitleRepository);
            case "School":
                return new Importor(stream, fileName, DbMapper.schoolRepository);
            case "SkillBook":
                return new Importor(stream, fileName, DbMapper.skillBookRepository);
            case "SkillBookBind":
                return new Importor(stream, fileName, DbMapper.skillBookBindRepository);
            case "WorldArea":
                return new Importor(stream, fileName, DbMapper.worldAreaRepository);
            case "WorldExit":
                return new Importor(stream, fileName, DbMapper.worldExitRepository);
            case "WorldNpc":
                return new Importor(stream, fileName, DbMapper.worldNpcRepository);
            case "WorldObject":
                return new Importor(stream, fileName, DbMapper.worldObjectRepository);
            case "WorldRoom":
                return new Importor(stream, fileName, DbMapper.worldRoomRepository);
            case "EventData":
                return new Importor(stream, fileName, DbMapper.eventDataRepository);
            case "ActionAttack":
                return new Importor(stream, fileName, DbMapper.actionAttackRepository);
            case "ActionDialogue":
                return new Importor(stream, fileName, DbMapper.actionDialogueRepository);
            case "ActionMove":
                return new Importor(stream, fileName, DbMapper.actionMoveRepository);
            case "ActionAcceptQuest":
                return new Importor(stream, fileName, DbMapper.actionAcceptQuestRepository);
            case "ActionChangeAttr":
                return new Importor(stream, fileName, DbMapper.actionChangeAttrRepository);
            case "ActionChangeStep":
                return new Importor(stream, fileName, DbMapper.actionChangeStepRepository);
            case "ActionCloseEvent":
                return new Importor(stream, fileName, DbMapper.actionCloseEventRepository);
            case "ActionLearnSkill":
                return new Importor(stream, fileName, DbMapper.actionLearnSkillRepository);
            case "ActionTurnInQuest":
                return new Importor(stream, fileName, DbMapper.actionTurnInQuestRepository);
            case "LootList":
                return new Importor(stream, fileName, DbMapper.lootListRepository);
            case "Skill":
                return new Importor(stream, fileName, DbMapper.skillRepository);
            case "SkillCategoryType":
                return new Importor(stream, fileName, DbMapper.skillCategoryTypeRepository);
            case "SkillFunctionType":
                return new Importor(stream, fileName, DbMapper.skillFunctionTypeRepository);
            case "SkillPosition":
                return new Importor(stream, fileName, DbMapper.skillPositionRepository);
            case "EquipmentPosition":
                return new Importor(stream, fileName, DbMapper.equipmentPositionRepository);
            case "DefaultSkills":
                return new Importor(stream, fileName, DbMapper.defaultSkillsRepository);
            case "DefaultEquipments":
                return new Importor(stream, fileName, DbMapper.defaultEquipmentsRepository);
            case "DefaultObjects":
                return new Importor(stream, fileName, DbMapper.defaultObjectsRepository);
            case "NormalObject":
                return new Importor(stream, fileName, DbMapper.normalObjectRepository);
            case "NpcLearnObjectList":
                return new Importor(stream, fileName, DbMapper.npcLearnObjectListRepository);
            case "Dialogue":
                return new Importor(stream, fileName, DbMapper.dialogueRepository);
            case "DialogueSentence":
                return new Importor(stream, fileName, DbMapper.dialogueSentenceRepository);
            case "NpcDialogue":
                return new Importor(stream, fileName, DbMapper.npcDialogueRepository);
            case "Quest":
                return new Importor(stream, fileName, DbMapper.questRepository);
            case "QuestDependency":
                return new Importor(stream, fileName, DbMapper.questDependencyRepository);
            case "QuestDialogueDependency":
                return new Importor(stream, fileName, DbMapper.questDialogueDependencyRepository);
            case "QuestRewardList":
                return new Importor(stream, fileName, DbMapper.questRewardListRepository);
            case "QuestObjective":
                return new Importor(stream, fileName, DbMapper.questObjectiveRepository);
            case "Shop":
                return new Importor(stream, fileName, DbMapper.shopRepository);
            case "NpcShop":
                return new Importor(stream, fileName, DbMapper.npcShopRepository);
            case "ShopGoods":
                return new Importor(stream, fileName, DbMapper.shopGoodsRepository);
            case "ObjectType":
                return new Importor(stream, fileName, DbMapper.objectTypeRepository);
            case "HangupType":
                return new Importor(stream, fileName, DbMapper.hangupTypeRepository);
            case "WeaponType":
                return new Importor(stream, fileName, DbMapper.weaponTypeRepository);
            case "TransList":
                return new Importor(stream, fileName, DbMapper.transListRepository);
            default:
                return null;
        }
    }
}
