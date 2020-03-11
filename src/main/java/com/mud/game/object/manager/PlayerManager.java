package com.mud.game.object.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mud.game.messages.AlertMessage;
import com.mud.game.messages.CharAllMessage;
import com.mud.game.messages.LoginSuccessMessage;
import com.mud.game.net.session.CallerType;
import com.mud.game.net.session.GameSessionService;
import com.mud.game.object.account.Player;
import com.mud.game.structs.SimplePlayerCharacter;
import com.mud.game.utils.passwordutils.ShaPassword;
import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.utils.resultutils.UserOptionCode;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.yeauty.pojo.Session;

import java.util.HashSet;
import java.util.Set;

public class PlayerManager {
    /*玩家管理，负责维护玩家账户在数据库的状态*/

    public static Player create(String username, String password, Session session)  {
        /*
        * @ 创建一个游戏玩家账户
        * */
        if(MongoMapper.playerRepository.existsByUsername(username)){
            session.sendText(JsonResponse.JsonStringResponse(new AlertMessage(UserOptionCode.USERNAME_EXIST_ERROR)));
            return null;
        }else{
            Player player = new Player();
            player.setUsername(username);
            player.setUsername(username);
            player.setPassword(password);
            player.setPlayerCharacters(new HashSet<>());
            MongoMapper.playerRepository.save(player);
            return player;
        }
    }

    public static Player login(String username, String password, Session session)  {
        /*
         * 对一个游戏账户执行登陆操作
         * 本质上就是更新玩家的更新callerID为角色ID，然后修改角色的类型为账户类型
         * */
        if(!MongoMapper.playerRepository.existsByUsername(username)){
            session.sendText(JsonResponse.JsonStringResponse(new AlertMessage(UserOptionCode.USER_NOT_EXIST_ERROR)));
            return null;
        }else{
            Player player = MongoMapper.playerRepository.findByUsername(username);
            if(player.getPassword().equals(ShaPassword.encrypts(password))){
                String playerId = player.getId();
                // 根据session的id拿到老的callerID
                String oldId = GameSessionService.getCallerIdBySessionId(session.id());
                // 转交session给新的caller
                GameSessionService.updateCallerId(oldId, playerId, CallerType.ACCOUNT);
                session.sendText(JsonResponse.JsonStringResponse(new LoginSuccessMessage(player)));
                return player;
            }
            session.sendText(JsonResponse.JsonStringResponse(new AlertMessage(UserOptionCode.INVALID_PASSWORD_ERROR)));
            return null;
        }
    }

    public static void showCharacters(Player player, Session session)  {
        Set<SimplePlayerCharacter> charAll = player.getPlayerCharacters();
        session.sendText(JsonResponse.JsonStringResponse(new CharAllMessage(charAll)));
    }

    public static String delete(String username) {
        /*
         * @ 删除一个游戏玩家账户，本质上不对账户进行删除操作，仅仅是锁定账户
         * @ 删除操作对与服务端来讲实际上是进行了更新操作
         * */
        if(MongoMapper.playerRepository.existsByUsername(username)){
            Player player = MongoMapper.playerRepository.findByUsername(username);
            player.setLocked(true);
            MongoMapper.playerRepository.save(player);
            return UserOptionCode.USER_DELETE_SUCCESS;
        }else{
            return UserOptionCode.USER_NOT_EXIST_ERROR;
        }
    }

    public static String ChangePassword(String username, String newPassword){
        /*
         * @ 修改密码操作
         * */
        if(MongoMapper.playerRepository.existsByUsername(username)){
            Player player = MongoMapper.playerRepository.findByUsername(username);
            player.setPassword(ShaPassword.encrypts(newPassword));
            MongoMapper.playerRepository.save(player);
            return UserOptionCode.USER_DELETE_SUCCESS;
        }else{
            return UserOptionCode.USER_NOT_EXIST_ERROR;
        }
    }

    public static String ban(String username, float duringTime){
        /*
         * @ 封禁一个账号
         * @ 设置一个字段标志玩家已经被封禁，并设置封禁截止时间（当前时间戳加上封禁的时间banTime）
         * @
         * */
        if(MongoMapper.playerRepository.existsByUsername(username)){
            Player player = MongoMapper.playerRepository.findByUsername(username);
            player.setBan(true);
            player.setBanEndTime(duringTime + System.currentTimeMillis());
            MongoMapper.playerRepository.save(player);
            return UserOptionCode.USER_BAN_SUCCESS;
        }else{
            return UserOptionCode.USER_NOT_EXIST_ERROR;
        }
    }
}
