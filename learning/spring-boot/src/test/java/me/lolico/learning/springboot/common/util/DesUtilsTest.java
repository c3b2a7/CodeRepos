package me.lolico.learning.springboot.common.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
class DesUtilsTest {

    @Test
    void getEncryptString() {
        System.out.println(DesUtils.getEncryptString("root"));
    }

    @Test
    void getDecryptString() {
        System.out.println(DesUtils.getDecryptString("EGR7dW+lIQs="));
    }
}
