package com.mud.game.commands.character;

import com.mud.game.commands.BaseCommand;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

/**
 * 移除邮件
 * <pre>
 *    {
 *        "cmd": "send_mail",
 *        "args":"dbref":  //邮件id
 *    }
 *  </pre>
 */
public class DeleteMail extends BaseCommand {
    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public DeleteMail(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        JSONObject args = getArgs();
        // 邮件主键
        String mailId = args.getString("args");
        MongoMapper.mailObjectRepository.deleteById(mailId);
        //TODO 返回邮件信息
    }
}
