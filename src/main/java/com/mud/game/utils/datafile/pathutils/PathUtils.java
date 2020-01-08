package com.mud.game.utils.datafile.pathutils;

import org.reflections.Reflections;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class PathUtils {
    public static List<String> getAllEntitiesName(String packageName) {
        Reflections reflections = new Reflections(packageName);
        Set<Class<? extends Object>> allClasses = reflections.getTypesAnnotatedWith(Entity.class);
        List<String> names = new ArrayList<String >();
        Iterator<Class<? extends Object>> iterator = allClasses.iterator();
        while(iterator.hasNext()){
            names.add(iterator.next().getSimpleName());

        }
        return names;
    }
}
