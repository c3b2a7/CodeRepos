package me.lolico.learning.springboot.common.util;

import org.apache.shiro.crypto.hash.Md5Hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Liar
 */
public class Md5Utils {
    private static final char[] HEX_CHARS = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String encrypt(String msg) throws NoSuchAlgorithmException {
        return encrypt(msg.getBytes());
    }

    public static String encrypt(String msg, String salt) throws NoSuchAlgorithmException {
        return encrypt((msg + salt).getBytes());
    }

    public static String encrypt(byte[] src) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] bytes = messageDigest.digest(src);
        char[] chars = new char[32];
        for (int i = 0; i < chars.length; i = i + 2) {
            byte b = bytes[i / 2];
            chars[i] = HEX_CHARS[(b >>> 0x4) & 0xf];
            chars[i + 1] = HEX_CHARS[b & 0xf];
        }
        return new String(chars);
    }
}
