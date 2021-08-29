package me.lolico.learning.springboot.util;

import me.lolico.learning.springboot.common.util.EntityCastUtils;
import me.lolico.learning.springboot.pojo.entity.User;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class EntityCastUtilsTest {

    @Test
    void castEntity() throws Exception {
        List<Object[]> list = Arrays.asList(new Object[]{1, "name1", "pwd1", "email1"}, new Object[]{2, "name2", "pwd2", "email2"}, new Object[]{3, "name3", "pwd3", "email3"});
        List<User> users= EntityCastUtils.castEntity(list,User.class);
        System.out.println(users);
    }
}
