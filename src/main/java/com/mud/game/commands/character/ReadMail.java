package com.mud.game.commands.character;

import com.mud.game.commands.BaseCommand;
import com.mud.game.messages.ReadMailMessage;
import com.mud.game.object.typeclass.MailObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

/**
 * 读取邮件
 * 读取未读邮件命令时发送请求，将邮件设置为已读
 *
 * <pre>
 *    {
 *        "cmd": "read_mail",
 *        "args": "mailId", //邮件id
 *    }
 *    返回：
 *    {
 *        "cmd":"replace_mail_status",
 *        "args":{
 *            "mailId":"deref" //邮件Id
 *        }
 *    }
 *  </pre>
 */

public class ReadMail extends BaseCommand {
    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public ReadMail(String key, Object caller, JSONObject args, Session session) {
        super(key, caller, args, session);
    }

    @Override
    public void execute() throws JSONException {
        PlayerCharacter caller = (PlayerCharacter) getCaller();
        JSONObject args = getArgs();
        // 邮件id
        String mailId = args.getString("args");
        //判断
        MailObject mailObject = MongoMapper.mailObjectRepository.findMailObjecByIdAndRecipientId(mailId, caller.getId());
        if (null == mailObject || mailObject.getReaded()) {
            //邮件不存在或邮件已读
            return;
        }
        mailObject.setReaded(true);
        MongoMapper.mailObjectRepository.save(mailObject);
        //更新前端数据
        caller.msg(new ReadMailMessage(mailObject));
    }
}
