package com.mud.game.object.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.object.typeclass.GemObject;
import com.mud.game.object.typeclass.NormalObjectObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.structs.EmbeddedCommand;
import com.mud.game.structs.GemObjectAppearance;
import com.mud.game.structs.NormalObjectAppearance;
import com.mud.game.utils.jsonutils.Attr2Map;
import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.utils.jsonutils.JsonStrConvetor;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.Equipment;
import com.mud.game.worlddata.db.models.Gem;
import org.yeauty.pojo.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GemObjectManager {
    public static GemObject create(String templateKey) {
        GemObject gemObject = new GemObject();
        Gem template = DbMapper.gemRepository.findGemByDataKey(templateKey);
        gemObject.setDataKey(template.getDataKey());
        gemObject.setName(template.getName());
        gemObject.setDescription(template.getDescription());
        gemObject.setUnitName(template.getUnitName());
        gemObject.setCategory(template.getCategory());
        gemObject.setUnique(template.isUniqueInBag());
        gemObject.setMaxStack(template.getMaxStack());
        gemObject.setCanDiscard(template.isCanDiscard());
        gemObject.setCanRemove(template.isCanRemove());
        gemObject.setIcon(template.getIcon());
        gemObject.setQuality(template.getQuality());
        gemObject.setOwner(null);
        gemObject.setTotalNumber(0);
        gemObject.setAttrs(Attr2Map.equipmentAttrTrans(template.getAttrs()));
        return gemObject;
    }

    public static void onPlayerLook(GemObject gemObject, PlayerCharacter playerCharacter, Session session) {
        /*
         * @ 当玩家查看装备的时候返回装备信息和可执行的命令（操作）
         * */
        Map<String, Object> lookMessage = new HashMap<>();
        GemObjectAppearance appearance = new GemObjectAppearance(gemObject);
        // 设置玩家可以对此物体执行的命令
        appearance.setCmds(getAvailableCommands(gemObject, playerCharacter));
        lookMessage.put("look_obj", appearance);
        session.sendText(JsonResponse.JsonStringResponse(lookMessage));
    }

    public static List<EmbeddedCommand> getAvailableCommands(GemObject gemObject, PlayerCharacter playerCharacter) {
        /*获得装备可操作命令*/
        List<EmbeddedCommand> cmds = new ArrayList<>();
        if (gemObject.isCanDiscard()) {
            cmds.add(new EmbeddedCommand("丢弃", "discard", gemObject.getId()));
        }
        return cmds;
    }
}
