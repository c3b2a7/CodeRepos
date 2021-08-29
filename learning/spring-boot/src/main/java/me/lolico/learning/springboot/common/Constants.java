package me.lolico.learning.springboot.common;

import org.apache.shiro.crypto.hash.Sha256Hash;

/**
 * @author lolico
 */
public class Constants {

    public static class Security {
        public static final String ALGORITHM_NAME = Sha256Hash.ALGORITHM_NAME;
        public static final Integer HASH_ITERATIONS = 3;
    }

    public static class Web {
        public static final String USER_SESSION = "USER_SESSION";
    }

}
