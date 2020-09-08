package com.mud.game.commands.character;

import com.mud.game.commands.BaseCommand;
import com.mud.game.messages.DeteleMailMessage;
import com.mud.game.messages.MailObjectMessage;
import com.mud.game.messages.ToastMessage;
import com.mud.game.object.builder.CommonObjectBuilder;
import com.mud.game.object.manager.PlayerCharacterManager;
import com.mud.game.object.supertypeclass.CommonObject;
import com.mud.game.object.typeclass.MailObject;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.structs.AttachmentInfo;
import com.mud.game.utils.collections.ListUtils;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.yeauty.pojo.Session;

import java.io.IOException;
import java.util.List;

import static org.apache.commons.lang.StringUtils.isNumeric;

/**
 * 提取邮件中的附件
 *
 * <pre>
 *    {
 *        "cmd": "extract_mail_attachment",
 *        "args": "mailObjectId", //邮件id
 *    }
 *  </pre>
 */

public class ExtractMailAttachment extends BaseCommand {
    /**
     * 构造函数，获取传入的参数，并执行命令
     *
     * @param key     命令绑定的key
     * @param caller  命令的调用者
     * @param args    命令参数
     * @param session 命令信息的返回通道
     */
    public ExtractMailAttachment(String key, Object caller, JSONObject args, Session session) {
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

        if (null == mailObject) {
            caller.msg("提取附件失败");
            //邮件不存在
            return;
        }
        List<AttachmentInfo> attachmentList = mailObject.getAttachments();
        List<AttachmentInfo> newAttachmentList = null;
        try {
            newAttachmentList = ListUtils.deepCopy(attachmentList);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (attachmentList == null || attachmentList.size() == 0) {
            caller.msg(new ToastMessage("附件不存在！"));
            return;
        }
        //判断模板物品还是已生成物品
        for (int i = 0; i < attachmentList.size(); i++) {
            if (isNumeric(attachmentList.get(i).getDbref())) {

                if (PlayerCharacterManager.receiveObjectToBagpack(caller, attachmentList.get(i).getDataKey(), attachmentList.get(i).getNumber())) {
                    newAttachmentList.remove(i);
                }
            } else {
                CommonObject commonObject = CommonObjectBuilder.findObjectById(attachmentList.get(i).getDbref());
                if (PlayerCharacterManager.receiveObjectToBagpack(caller, commonObject, attachmentList.get(i).getNumber())) {
                    //物品唯一修改物品归属
                    if (commonObject.isUnique()) {
                        commonObject.setOwner(caller.getId());
                        CommonObjectBuilder.save(commonObject);
                    }
                    newAttachmentList.remove(i);
                }
            }
        }
        //附件完全提取，删除邮件
        if (newAttachmentList.size() == 0) {
            MongoMapper.mailObjectRepository.delete(mailObject);
            caller.msg(new ToastMessage("提取完成！"));
            caller.msg(new DeteleMailMessage(mailObject));
        } else {
            mailObject.setAttachments(newAttachmentList);
            MongoMapper.mailObjectRepository.save(mailObject);
            caller.msg(new ToastMessage("提取部分成功！"));
            //刷新邮件列表
            List<MailObject> recipientList = MongoMapper.mailObjectRepository.findMailObjectListByRecipientId(caller.getId());
            if (recipientList != null && recipientList.size() > 0) {
                caller.msg(new MailObjectMessage(caller, recipientList));
            }
        }

        //刷新背包
        PlayerCharacterManager.showBagpack(caller);
    }
}
