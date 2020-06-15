package com.mud.game.object.manager;

import com.mud.game.handler.ConditionHandler;
import com.mud.game.handler.EquipmentPositionHandler;
import com.mud.game.messages.ToastMessage;
import com.mud.game.object.typeclass.EquipmentObject;
import com.mud.game.object.typeclass.GemObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.SkillBookObject;
import com.mud.game.structs.EmbeddedCommand;
import com.mud.game.structs.EquipmentObjectAppearance;
import com.mud.game.structs.SkillBookObjectAppearance;
import com.mud.game.utils.collections.ArrayListUtils;
import com.mud.game.utils.jsonutils.Attr2Map;
import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.Gem;
import com.mud.game.worlddata.db.models.Skill;
import com.mud.game.worlddata.db.models.SkillBook;
import com.mud.game.worlddata.db.models.SkillBookBind;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.IteratorUtils;
import org.yeauty.pojo.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkillBookObjectManager {
    public static SkillBookObject create(String templateKey)  {
        SkillBookObject skillBookObject = new SkillBookObject();
        SkillBook template = DbMapper.skillBookRepository.findSkillBookByDataKey(templateKey);
        skillBookObject.setDataKey(template.getDataKey());
        skillBookObject.setName(template.getName());
        skillBookObject.setDescription(template.getDescription());
        skillBookObject.setUnitName(template.getUnitName());
        skillBookObject.setCategory(template.getCategory());
        skillBookObject.setUnique(template.isUniqueInBag());
        skillBookObject.setMaxStack(template.getMaxStack());
        skillBookObject.setCanDiscard(template.isCanDiscard());
        skillBookObject.setCanRemove(template.isCanRemove());
        skillBookObject.setIcon(template.getIcon());
        skillBookObject.setQuality(template.getQuality());
        skillBookObject.setOwner(null);
        skillBookObject.setTotalNumber(0);
        skillBookObject.setMax_level(template.getMax_level());
        skillBookObject.setUse_potential(template.isUse_potential());
        skillBookObject.setUseCondition(template.getUseCondition());
        return skillBookObject;
    }

    public static String getCurrentSkill(SkillBookObject skillBookObject){
        if(skillBookObject.getCurrentSkill() != null){
            return skillBookObject.getCurrentSkill();
        }else{
            Iterable<SkillBookBind> binds = DbMapper.skillBookBindRepository.findSkillBookBindsBySkillBook(skillBookObject.getDataKey());
            if(binds.iterator().hasNext()){
                String randomSkill = ArrayListUtils.randomChoice(new ArrayList<>(IterableUtils.toList(binds))).getSkill();
                skillBookObject.setCurrentSkill(randomSkill);
                MongoMapper.skillBookObjectRepository.save(skillBookObject);
                return randomSkill;
            }else{
                return null;
            }
        }
    }

    public static void use(PlayerCharacter playerCharacter, SkillBookObject skillBookObject){
        if(ConditionHandler.matchCondition(skillBookObject.getUseCondition(), playerCharacter)){
            PlayerCharacterManager.learnSkillBySkillBook(playerCharacter, skillBookObject);
        }else{
            playerCharacter.msg(new ToastMessage("无法使用此技能书！"));
        }
    }

    public static List<EmbeddedCommand> getAvailableCommands(SkillBookObject skillBookObject, PlayerCharacter playerCharacter) {
        /*获得装备可操作命令*/
        List<EmbeddedCommand> cmds = new ArrayList<>();
        if(skillBookObject.isCanDiscard()){
            cmds.add(new EmbeddedCommand("丢弃", "discard", skillBookObject.getId()));
        }
        cmds.add(new EmbeddedCommand("研读", "use", skillBookObject.getId()));
        return cmds;
    }

    public static void onPlayerLook(SkillBookObject skillBookObject, PlayerCharacter playerCharacter, Session session)  {
        /*
         * @ 当玩家查看装备的时候返回装备信息和可执行的命令（操作）
         * */
        Map<String, Object> lookMessage = new HashMap<>();
        SkillBookObjectAppearance appearance = new SkillBookObjectAppearance(skillBookObject);
        // 设置玩家可以对此物体执行的命令
        appearance.setCmds(getAvailableCommands(skillBookObject, playerCharacter));
        lookMessage.put("look_obj", appearance);
        session.sendText(JsonResponse.JsonStringResponse(lookMessage));
    }
}
