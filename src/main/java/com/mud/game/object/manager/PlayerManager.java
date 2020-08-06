package com.mud.game.object.manager;

import com.mud.game.messages.AlertMessage;
import com.mud.game.messages.CharAllMessage;
import com.mud.game.messages.LoginSuccessMessage;
import com.mud.game.net.session.CallerType;
import com.mud.game.net.session.GameSessionService;
import com.mud.game.object.account.Player;
import com.mud.game.structs.SimpleCharacter;
import com.mud.game.utils.passwordutils.ShaPassword;
import com.mud.game.utils.jsonutils.JsonResponse;
import com.mud.game.utils.resultutils.UserOptionCode;
import com.mud.game.worldrun.db.mappings.MongoMapper;
import org.yeauty.pojo.Session;

import java.util.HashSet;
import java.util.Set;

/** 玩家账户管理，负责维护玩家账户在数据库的状态*/
public class PlayerManager {

    /** 创建一个游戏玩家账户
     * @param username String 账户名
     * @param password String 密码
     * @param session Session 玩家客户端连接的session
     * @return player Player 创建成功：返回一个账户实例； 失败：返回null
     * */
    public static Player create(String username, String password, Session session)  {
        // 检查账号是否重复
        if(MongoMapper.playerRepository.existsByUsername(username)){
            session.sendText(JsonResponse.JsonStringResponse(new AlertMessage(UserOptionCode.USERNAME_EXIST_ERROR)));
            return null;
        }else{
            //创建新的账户
            Player player = new Player();
            player.setUsername(username);
            player.setUsername(username);
            player.setPassword(password);
            player.setPlayerCharacters(new HashSet<>());
            MongoMapper.playerRepository.save(player);
            return player;
        }
    }

    /**登陆玩家账户
     * 对一个游戏账户执行登陆操作
     * @param username String 账户名
     * @param password String 密码
     * @param session Session 连接session
     * @return Player 登陆成功：返回角色信息 失败：返回null
     * */
    public static Player login(String username, String password, Session session)  {
        // 检查用户是否存在，不存在则发送错误信息
        if(!MongoMapper.playerRepository.existsByUsername(username)){
            session.sendText(JsonResponse.JsonStringResponse(new AlertMessage(UserOptionCode.USER_NOT_EXIST_ERROR)));
            return null;
        }else{
            // 查询用户并检查密码
            Player player = MongoMapper.playerRepository.findByUsername(username);
            // 检查通过，更新用户身份从匿名者到player级别
            if(player.getPassword().equals(ShaPassword.encrypts(password))){
                String playerId = player.getId();
                // 根据session的id拿到老的callerID
                String oldId = GameSessionService.getCallerIdBySessionId(session.id());
                // 转交session给新的caller
                GameSessionService.updateCallerId(oldId, playerId, CallerType.ACCOUNT);
                session.sendText(JsonResponse.JsonStringResponse(new LoginSuccessMessage(player)));
                showCharacters(player, session);
                return player;
            }
            // 检查不通过，直接显示错误信息
            session.sendText(JsonResponse.JsonStringResponse(new AlertMessage(UserOptionCode.INVALID_PASSWORD_ERROR)));
            return null;
        }
    }

    /**显示玩家的角色
     * @param player Player 玩家账户
     * @param session Session 客户端连接session
     * */
    public static void showCharacters(Player player, Session session)  {
        Set<SimpleCharacter> char_all = player.getPlayerCharacters();
        session.sendText(JsonResponse.JsonStringResponse(new CharAllMessage(char_all)));
    }

    /** 删除一个游戏玩家账户，本质上不对账户进行删除操作，仅仅是锁定账户
     *  删除操作对与服务端来讲实际上是进行了更新操作
     * @param username String 要删除（锁定）的用户名
     * */
    public static String delete(String username) {
        if(MongoMapper.playerRepository.existsByUsername(username)){
            Player player = MongoMapper.playerRepository.findByUsername(username);
            player.setLocked(true);
            MongoMapper.playerRepository.save(player);
            return UserOptionCode.USER_DELETE_SUCCESS;
        }else{
            return UserOptionCode.USER_NOT_EXIST_ERROR;
        }
    }

    /** 修改密码操作
     * @param username String 要修改的账户
     * @param newPassword String 修改后的密码
     * */
    public static String ChangePassword(String username, String newPassword){
        if(MongoMapper.playerRepository.existsByUsername(username)){
            Player player = MongoMapper.playerRepository.findByUsername(username);
            player.setPassword(ShaPassword.encrypts(newPassword));
            MongoMapper.playerRepository.save(player);
            return UserOptionCode.USER_DELETE_SUCCESS;
        }else{
            return UserOptionCode.USER_NOT_EXIST_ERROR;
        }
    }

    /** 封禁一个账号
     * 设置一个字段标志玩家已经被封禁，并设置封禁截止时间（当前时间戳加上封禁的时间banTime）
     * @param  username String 要封禁的账号
     * @param duringTime float 封禁持续的时间（单位：秒）
     * */
    public static String ban(String username, float duringTime){
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
