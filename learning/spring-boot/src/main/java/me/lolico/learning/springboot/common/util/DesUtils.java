package me.lolico.learning.springboot.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * @author lolico
 */
@Component
public class DesUtils {
    private static Key key;
    private static String KEY_STR;
    private static String ALGORITHM;

    @Value("#{ sysConfig.getValue('DESUTILS_KEY_STR') ?: 'MY_KET_STR' }")
    public void setKeyStr(String keyStr) {
        DesUtils.KEY_STR = keyStr;
    }

    @Value("#{ sysConfig.getValue('DESUTILS_ALGORITHM') ?: 'DES' }")
    public void setAlgorithm(String algorithm) {
        DesUtils.ALGORITHM = algorithm;
    }

    @PostConstruct
    public void init() {
        try {
            KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
            generator.init(new SecureRandom(KEY_STR.getBytes()));
            key = generator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String getEncryptString(String str) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] bytes = cipher.doFinal(str.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getDecryptString(String str) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(str));
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        System.out.println("".length());
        if (StringUtils.isEmpty(args)) {
            System.out.println("请输入加密的字符，用空格分隔。");
        } else {
            for (String arg : args) {
                System.out.println(arg + ": " + getEncryptString(arg));
            }
        }
    }

}
