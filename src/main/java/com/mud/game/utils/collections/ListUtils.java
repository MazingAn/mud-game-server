package com.mud.game.utils.collections;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListUtils {

    public static <T> T randomChoice(List<T> array){
        Random rand = new Random();
        return array.get(rand.nextInt(array.size()));
    }

}
