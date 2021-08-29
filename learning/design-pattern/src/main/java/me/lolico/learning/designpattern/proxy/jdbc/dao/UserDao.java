package me.lolico.learning.designpattern.proxy.jdbc.dao;

import me.lolico.learning.designpattern.proxy.jdbc.model.User;

/**
 * @author lolico
 */
public interface UserDao {
    void save(User user);
}
