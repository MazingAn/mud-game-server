package com.mud.game.object.manager;

import com.mud.game.object.supertypeclass.CommonObject;
import com.mud.game.object.typeclass.NormalObjectObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.object.typeclass.SkillBookObject;
import com.mud.game.structs.*;
import com.mud.game.utils.jsonutils.Attr2Map;
import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.utils.jsonutils.JsonStrConvetor;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.NormalObject;
import org.yeauty.pojo.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NormalObjectObjectManager {

    public static NormalObjectObject create(String templateKey) {
        try{
            NormalObjectObject normalObjectObject = new NormalObjectObject();
            NormalObject template = DbMapper.normalObjectRepository.findNormalObjectByDataKey(templateKey);
            normalObjectObject.setDataKey(template.getDataKey());
            normalObjectObject.setName(template.getName());
            normalObjectObject.setDescription(template.getDescription());
            normalObjectObject.setUnitName(template.getUnitName());
            normalObjectObject.setCategory(template.getCategory());
            normalObjectObject.setUnique(template.isUniqueInBag());
            normalObjectObject.setMaxStack(template.getMaxStack());
            normalObjectObject.setCanDiscard(template.isCanDiscard());
            normalObjectObject.setCanRemove(template.isCanRemove());
            normalObjectObject.setIcon(template.getIcon());
            normalObjectObject.setQuality(1);
            normalObjectObject.setLevel(1);
            normalObjectObject.setTotalNumber(0);
            return normalObjectObject;
        }catch (Exception e){
             System.out.println("在创建物品 " + templateKey +" 的时候发生异常！ 已经跳过对物品的创建！");
             return null;
        }
    }

    public static List<EmbeddedCommand> getAvailableCommands(NormalObjectObject normalObjectObject, PlayerCharacter playerCharacter) {
        /*获得装备可操作命令*/
        List<EmbeddedCommand> cmds = new ArrayList<>();
        if(normalObjectObject.isCanDiscard()){
            cmds.add(new EmbeddedCommand("丢弃", "discard", normalObjectObject.getId()));
        }
        if(normalObjectObject.getFunction() != null && !normalObjectObject.getFunction().trim().equals("")  ){
            cmds.add(new EmbeddedCommand("使用", "use", normalObjectObject.getId()));
        }
        return cmds;
    }

    public static void onPlayerLook(NormalObjectObject normalObjectObject, PlayerCharacter playerCharacter, Session session)  {
        /*
         * @ 当玩家查看装备的时候返回装备信息和可执行的命令（操作）
         * */
        Map<String, Object> lookMessage = new HashMap<>();
        NormalObjectAppearance appearance = new NormalObjectAppearance(normalObjectObject);
        // 设置玩家可以对此物体执行的命令
        appearance.setCmds(getAvailableCommands(normalObjectObject, playerCharacter));
        lookMessage.put("look_obj", appearance);
        session.sendText(JsonResponse.JsonStringResponse(lookMessage));
    }

}
