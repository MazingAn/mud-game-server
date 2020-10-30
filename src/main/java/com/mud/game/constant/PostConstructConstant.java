package com.mud.game.constant;

import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.DataDictionary;
import com.mud.game.worlddata.db.repository.DataDictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class PostConstructConstant {
    @Autowired
    private DataDictionaryRepository dataDictionaryRepository;
    /**
     * 丢弃物品提示
     */
    public static String DISCARD_CONTENTS;
    /**
     * 犯罪值触发指定房间NPC攻击
     */
    public static int CRIME_VALUE_ATTACK;
    /**
     * 犯罪值达到阈值，不能与npc互动
     */
    public static int CRIME_VALUE_CMDS;
    /**
     * 犯罪值为0是，使用道具减去犯罪值提示
     */
    public static String CUT_BACKCRIME_ZERO_OBJECT;
    /**
     * 犯罪值为0是，找npc减去犯罪值提示
     */
    public static String CUT_BACKCRIME_ZERO_NPC;
    /**
     * 减去犯罪值提示
     */
    public static String CUT_BACKCRIME_ATTACK;
    /**
     * 此NPC有减少犯罪值的功能
     */
    public static String CUT_BACKCRIME_NPC_DATAKEY;
    /**
     * 仇恨值触发主动攻击
     */
    public static int ENEMY_LEVEL_VALUE;

    @PostConstruct
    public void init() {
        DISCARD_CONTENTS = dataDictionaryRepository.findDataDictionaryByDataKey("DISCARD_CONTENTS").getContent();
        CRIME_VALUE_ATTACK = Integer.parseInt(dataDictionaryRepository.findDataDictionaryByDataKey("CRIME_VALUE_ATTACK").getContent());
        CRIME_VALUE_CMDS = Integer.parseInt(dataDictionaryRepository.findDataDictionaryByDataKey("CRIME_VALUE_CMDS").getContent());
        ENEMY_LEVEL_VALUE = Integer.parseInt(dataDictionaryRepository.findDataDictionaryByDataKey("ENEMY_LEVEL_VALUE").getContent());
        CUT_BACKCRIME_ZERO_OBJECT = dataDictionaryRepository.findDataDictionaryByDataKey("CUT_BACKCRIME_ZERO_OBJECT").getContent();
        CUT_BACKCRIME_ZERO_NPC = dataDictionaryRepository.findDataDictionaryByDataKey("CUT_BACKCRIME_ZERO_NPC").getContent();
        CUT_BACKCRIME_ATTACK = dataDictionaryRepository.findDataDictionaryByDataKey("CUT_BACKCRIME_ATTACK").getContent();
        CUT_BACKCRIME_NPC_DATAKEY = dataDictionaryRepository.findDataDictionaryByDataKey("CUT_BACKCRIME_NPC_DATAKEY").getContent();
    }

    /**
     * 数据被修改的时候，重新加载内存的中数据
     */
    public static void overload() {
        DISCARD_CONTENTS = DbMapper.dataDictionaryRepository.findDataDictionaryByDataKey("DISCARD_CONTENTS").getContent();
        CRIME_VALUE_ATTACK = Integer.parseInt(DbMapper.dataDictionaryRepository.findDataDictionaryByDataKey("CRIME_VALUE_ATTACK").getContent());
        CRIME_VALUE_CMDS = Integer.parseInt(DbMapper.dataDictionaryRepository.findDataDictionaryByDataKey("CRIME_VALUE_CMDS").getContent());
        CUT_BACKCRIME_ZERO_OBJECT = DbMapper.dataDictionaryRepository.findDataDictionaryByDataKey("CUT_BACKCRIME_ZERO_OBJECT").getContent();
        CUT_BACKCRIME_ZERO_NPC = DbMapper.dataDictionaryRepository.findDataDictionaryByDataKey("CUT_BACKCRIME_ZERO_NPC").getContent();
        CUT_BACKCRIME_ATTACK = DbMapper.dataDictionaryRepository.findDataDictionaryByDataKey("CUT_BACKCRIME_ATTACK").getContent();
        CUT_BACKCRIME_NPC_DATAKEY = DbMapper.dataDictionaryRepository.findDataDictionaryByDataKey("CUT_BACKCRIME_NPC_DATAKEY").getContent();
        ENEMY_LEVEL_VALUE = Integer.parseInt(DbMapper.dataDictionaryRepository.findDataDictionaryByDataKey("ENEMY_LEVEL_VALUE").getContent());
    }
}
