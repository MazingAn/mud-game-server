package com.mud.game.condition.general;

import com.mud.game.condition.BaseCondition;
import com.mud.game.messages.MsgMessage;
import com.mud.game.messages.ToastMessage;
import com.mud.game.object.builder.CommonObjectBuilder;
import com.mud.game.object.manager.GameCharacterManager;
import com.mud.game.object.typeclass.PlayerCharacter;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.Skill;

public class HasSkill extends BaseCondition {
    /**
     * 构造函数
     * <p>
     * 构造函数用于捕获参数
     *
     * @param key             检测类的健
     * @param playerCharacter 检测的主体对象（要被检测的玩家）
     * @param args            检测函数参数列表（字符串列表）
     */
    public HasSkill(String key, PlayerCharacter playerCharacter, String[] args) {
        super(key, playerCharacter, args);
    }

    @Override
    public boolean match() throws NoSuchFieldException, IllegalAccessException {
        PlayerCharacter playerCharacter = getPlayerCharacter();
        String[] args = getArgs();
        String skillKey = args[0];
        if (GameCharacterManager.hasSkill(playerCharacter, skillKey)){
            return true;
        }else{
            if(skillKey.equals("skill_zhishi_dushu")){
                playerCharacter.msg(new MsgMessage("你还是个{g文盲{n，先去学点{g读书写字{n吧！"));
                playerCharacter.msg(new ToastMessage("你还是个{g文盲{n，先去学点{g读书写字{n吧！"));
            }
            Skill template = DbMapper.skillRepository.findSkillByDataKey(skillKey);
            if(template != null){
                playerCharacter.msg(new MsgMessage(String.format("需要先学会 {g%s{n", template.getName())));
                playerCharacter.msg(new ToastMessage(String.format("需要先学会 {g%s{n", template.getName())));
            }
            return false;
        }
    }
}
