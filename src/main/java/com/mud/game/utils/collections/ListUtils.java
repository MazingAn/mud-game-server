package com.mud.game.utils.collections;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListUtils {

    public static <T> T randomChoice(List<T> array) {
        Random rand = new Random();
        return array.get(rand.nextInt(array.size()));
    }


    /**
     * 深度复制list对象,先序列化对象，再反序列化对象
     *
     * @param src 需要复制的对象列表
     * @return 返回新的对象列表
     * @throws IOException 读取Object流信息失败
     * @throws ClassNotFoundException 泛型类不存在
     */
    public static <T> List<T> deepCopy(List<T> src)
            throws IOException, ClassNotFoundException
    {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);
        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        return (List<T>)in.readObject();
    }
}
