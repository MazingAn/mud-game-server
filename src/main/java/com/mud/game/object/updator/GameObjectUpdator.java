package com.mud.game.object.updator;

import com.mud.game.object.typeclass.SkillBookObject;
import com.mud.game.object.typeclass.SkillObject;
import com.mud.game.worlddata.db.mappings.DbMapper;
import com.mud.game.worlddata.db.models.Skill;
import com.mud.game.worlddata.db.models.SkillBook;
import com.mud.game.worldrun.db.mappings.MongoMapper;

public class GameObjectUpdator {

    public static void updateGameObject(String type) {
        switch (type) {
            case "Skill":
                Iterable<Skill> skills = DbMapper.skillRepository.findAll();
                for (Skill skill : skills) {
                    Iterable<SkillObject> skillObjects = MongoMapper.skillObjectRepository.findSkillObjectListByDataKey(skill.getDataKey());
                    for (SkillObject skillObject : skillObjects) {
                        skillObject.setIcon(skill.getIcon());
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
