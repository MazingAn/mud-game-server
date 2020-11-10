package com.mud.game.object.updator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mud.game.object.typeclass.SkillBookObject;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.structs.SkillXiShu;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.Skill;
import com.mud.game.worlddata.db.models.SkillBook;
import com.mud.game.worldrun.db.mappings.MongoMapper;

import java.util.HashSet;
import java.util.Set;

public class GameObjectUpdator {

    public static void updateGameObject(String type) {
        switch (type) {
            case "Skill":
                Iterable<Skill> skills = DbMapper.skillRepository.findAll();
                Set<SkillXiShu> skillXiShus = null;
                for (Skill skill : skills) {
                    Iterable<SkillObject> skillObjects = MongoMapper.skillObjectRepository.findSkillObjectListByDataKey(skill.getDataKey());
                    for (SkillObject skillObject : skillObjects) {
                        skillXiShus = new HashSet<>();
                        skillObject.setIcon(skill.getIcon());
                        JSONArray jsonObject = JSON.parseArray(skill.getXiShu());
                        for (Object json : jsonObject) {
                            JSONObject jo = JSONObject.parseObject(json.toString());
                            skillXiShus.add(new SkillXiShu(jo.getString("attrKey"), jo.getDouble("value"), jo.getDouble("coefficient")));
                        }
                        skillObject.setXiShu(skillXiShus);
                        MongoMapper.skillObjectRepository.save(skillObject);
                    }
                }
                break;
            case "SkillBook":
                Iterable<SkillBook> skillBooks = DbMapper.skillBookRepository.findAll();
                for (SkillBook skillBook : skillBooks) {
                    Iterable<SkillBookObject> skillBookObjects = MongoMapper.skillBookObjectRepository.findSkillBookObjectListByDataKey(skillBook.getDataKey());
                    for (SkillBookObject skillBookObject : skillBookObjects) {
                        skillBookObject.setIcon(skillBook.getIcon());
                        MongoMapper.skillBookObjectRepository.save(skillBookObject);
                    }
                }
                break;
        }
    }
}
