package me.lolico.learning.springboot.common.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SysConfigTest {

    @Autowired
    SysConfig sysConfig;

    @Test
    void getKeyStr() {
        System.out.println(sysConfig.getValue("KEY_STR"));
    }
}
