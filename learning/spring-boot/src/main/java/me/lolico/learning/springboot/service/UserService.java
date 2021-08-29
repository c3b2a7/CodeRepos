package me.lolico.learning.springboot.service;

import me.lolico.learning.springboot.common.Constants;
import me.lolico.learning.springboot.pojo.entity.User;
import me.lolico.learning.springboot.repository.UserRepository;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lolico
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public User addUser(User user) {
        user = prepareUser(user);
        return userRepository.save(user);
    }

    protected User prepareUser(User user) {
        String hash = new SimpleHash(Constants.Security.ALGORITHM_NAME, user.getPassword(), user.getUsername(), Constants.Security.HASH_ITERATIONS).toHex();
        user.setPassword(hash);
        return user;
    }

    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    public User updateInfo(User user) {
        if (user.getId() != 0) {
            return userRepository.dynamicUpdate(user);
        }
        return null;
    }

    public List<User> dynamicFind(User user) {
        return userRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (user.getId() > 0) {
                predicates.add(criteriaBuilder.equal(root.<Integer>get("id"), user.getId()));
            }
            if (!StringUtils.isEmpty(user.getUsername())) {
                predicates.add(criteriaBuilder.equal(root.<String>get("username"), user.getUsername()));
            }
            if (!StringUtils.isEmpty(user.getPassword())) {
                predicates.add(criteriaBuilder.equal(root.<String>get("password"), user.getPassword()));
            }
            if (!StringUtils.isEmpty(user.getEmail())) {
                predicates.add(criteriaBuilder.equal(root.<String>get("email"), user.getEmail()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }

    public User findUserForLogin(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }


    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
