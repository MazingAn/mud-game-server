package com.mud.game.thirdpart.wechat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 微信认证时候的检查程序
 * TOKEN: 1234567890qwertyuiop
 * 接口地址： http://服务器地址:8000/weichatLoginCallback
 * <p>
 * signature
 * timestamp
 * nonce
 */
@Controller
public class WechatLoginCtrl {

    public static final String TOKEN = "1234567890qwertyuiop";

    @GetMapping("/weichatLoginCallback")
    public boolean checkSignalure(@RequestParam(name = "signature") String signature,
                                  @RequestParam(name = "timestamp") String timestamp,
                                  @RequestParam(name = "nonce") String nonce) {

        // 对token、timestamp、和nonce按字典排序.
        String[] paramArr = {signature, timestamp, nonce};
        Arrays.sort(paramArr);

        // 将排序后的结果拼接成一个字符串.
        String content = paramArr[0].concat(paramArr[1]).concat(paramArr[2]);

        String ciphertext = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            // 对拼接后的字符串进行sha1加密.
            byte[] digest = md.digest(content.toString().getBytes());
            ciphertext = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // 将sha1加密后的字符串与signature进行对比.
        return ciphertext != null && ciphertext.equals(signature.toUpperCase());
    }

    /**
     * 将字节数组转换为十六进制字符串.
     *
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串.
     *
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];

        String s = new String(tempArr);
        return s;
    }
}
