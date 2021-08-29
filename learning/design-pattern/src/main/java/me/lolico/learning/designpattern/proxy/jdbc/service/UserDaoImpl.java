package me.lolico.learning.designpattern.proxy.jdbc.service;

import me.lolico.learning.designpattern.proxy.jdbc.dao.UserDao;
import me.lolico.learning.designpattern.proxy.jdbc.model.User;

/**
 * @author lolico
 */
public class UserDaoImpl implements UserDao {

    @Override
    public void save(User user) {
        System.out.println("保存用户:" + user);
    }
}
