package me.lolico.learning.springboot.common.util;

import java.util.Base64;

/**
 * @author Liar
 */
public class Base64Utils {
    public static String encode(String msg) {
        return msg == null ? null : encode(msg.getBytes());
    }

    public static String encode(byte[] src) {
        return src == null ? null : new String(Base64.getEncoder().encode(src));
    }

    public static String decode(String msg) {
        return msg == null ? null : decode(msg.getBytes());
    }

    public static String decode(byte[] src) {
        return src == null ? null : new String(Base64.getDecoder().decode(src));
    }

}
