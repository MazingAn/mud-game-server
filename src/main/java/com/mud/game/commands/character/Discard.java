package com.mud.game.commands.character;

import com.mud.game.commands.BaseCommand;
import com.mud.game.messages.ToastMessage;
import com.mud.game.object.builder.CommonObjectBuilder;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.typeclass.BagpackObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.structs.CommonObjectInfo;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.DataDictionary;
import com.mud.game.worlddata.db.models.supermodel.BaseCommonObject;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import java.util.Map;

import static com.mud.game.constant.PostConstructConstant.DISCARD_CONTENTS;

/**
 * 丢弃道具：目前根据背包格子丢弃
 * <p>
 * cmd:discard
 * args{
 * itemId:""
 * }
 */
public class Discard extends BaseCommand {
    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public Discard(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        JSONObject args = getArgs();
        Session session = getSession();
        PlayerCharacter playerCharacter = (PlayerCharacter) getCaller();
        String itemId = args.getString("itmeId");
        //移除背包格子
        BagpackObject bagpackObject = MongoMapper.bagpackObjectRepository.findBagpackObjectById(playerCharacter.getBagpack());
        Map<String, CommonObjectInfo> map = bagpackObject.getItems();
        if (map.containsKey(itemId)) {
            CommonObjectInfo commonObjectInfo = map.get(itemId);
            BaseCommonObject baseCommonObject = CommonObjectBuilder.findObjectTemplateByDataKey(commonObjectInfo.getDataKey());
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("你丢掉了");
            stringBuffer.append("{g ");
            stringBuffer.append(commonObjectInfo.getNumber());
            stringBuffer.append(baseCommonObject.getUnitName());
            stringBuffer.append("{n ");
            stringBuffer.append("{y ");
            stringBuffer.append(commonObjectInfo.getName());
            stringBuffer.append("{n ");
            stringBuffer.append("。");

            playerCharacter.msg(new ToastMessage(stringBuffer.toString()));
            playerCharacter.msg(DISCARD_CONTENTS);

            //删除唯一物品
            if (baseCommonObject != null && baseCommonObject.getMaxStack() == 1) {
                CommonObjectBuilder.deleteObjectById(commonObjectInfo.getDbref());
            }
            map.remove(itemId);
        }
        MongoMapper.bagpackObjectRepository.save(bagpackObject);
        PlayerCharacterManager.showBagpack(playerCharacter);
    }
}
