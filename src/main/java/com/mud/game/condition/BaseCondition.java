package com.mud.game.condition;

import com.mud.game.object.typeclass.PlayerCharacter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public abstract class BaseCondition {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private PlayerCharacter playerCharacter;
    private String key;
    private String[] args;

    public BaseCondition(String key, PlayerCharacter playerCharacter, String[] args) {
        this.playerCharacter = playerCharacter;
        this.args = args;
        this.key = key;
    }

    public abstract boolean match() throws NoSuchFieldException, IllegalAccessException;


    public PlayerCharacter getPlayerCharacter() {
        return playerCharacter;
    }

    public void setPlayerCharacter(PlayerCharacter playerCharacter) {
        this.playerCharacter = playerCharacter;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }
}
