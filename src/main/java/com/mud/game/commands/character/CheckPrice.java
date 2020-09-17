package com.mud.game.commands.character;

import com.mud.game.commands.BaseCommand;
import com.mud.game.messages.CheckPriceMessage;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.ObjectBindPrice;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

/**
 * 计算物品价格
 */
public class CheckPrice extends BaseCommand {
    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public CheckPrice(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        JSONObject args = getArgs();
        Session session = getSession();
        PlayerCharacter playerCharacter = (PlayerCharacter) getCaller();
        String dataKey = args.getString("args");
        if (StringUtils.isBlank(dataKey)) {
            return;
        }
        ObjectBindPrice objectBindPrice = DbMapper.objectBindPriceRepository.findObjectBindPriceByDataKey(dataKey);
        if (null != objectBindPrice) {
            playerCharacter.msg(new CheckPriceMessage(objectBindPrice.getDataKey(), objectBindPrice.getPrice()));
        }
    }
}
