package com.mud.game.utils.collections;

import java.util.ArrayList;
import java.util.Random;

public class ArrayListUtils {

    public static <T> T randomChoice(ArrayList<T> array){
        Random rand = new Random();
        return array.get(rand.nextInt(array.size()));
    }

}
