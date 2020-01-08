package com.mud.game.utils.passwordutils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ShaPassword {
    public static String encrypts(String source) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(source.getBytes());
            return new BigInteger(1, md.digest()).toString(32);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
