package com.mud.game.commands.character;

import com.mud.game.commands.BaseCommand;
import com.mud.game.object.typeclass.PlayerCharacter;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

/**
 * 邮件列表
 * <pre>
 *    {
 *        "cmd": "check_mail"
 *    }
 *  </pre>
 */
public class CheckMail extends BaseCommand {
    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public CheckMail(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        //当前用户接受邮件列表
//        List<MailObject> recipientList = MongoMapper.mailObjectRepository.findMailObjectListByRecipientId(caller.getId());
//        caller.msg(new MailObjectMessage(caller, recipientList));
//        //当前用户发送的列表
//        List<MailObject> initiatorList = MongoMapper.mailObjectRepository.findMailObjectByInitiatorId(caller.getId());
//        caller.msg(new MailObjectMessage(caller, recipientList));
    }
}
