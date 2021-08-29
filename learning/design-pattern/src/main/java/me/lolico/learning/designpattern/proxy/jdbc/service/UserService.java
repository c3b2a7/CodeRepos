package me.lolico.learning.designpattern.proxy.jdbc.service;

import me.lolico.learning.designpattern.proxy.jdbc.dao.UserDao;
import me.lolico.learning.designpattern.proxy.jdbc.model.User;

/**
 * @author lolico
 */
public class UserService implements UserDao {

    private final UserDao userDao = new UserDaoImpl();

    @Override
    public void save(User user) {
        userDao.save(user);
    }
}
