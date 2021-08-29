package me.lolico.learning.springboot.repository;


import me.lolico.learning.springboot.pojo.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Optional;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    UserRepository repository;


    @Test
    void findByUsernameAndPassword() {
        Optional.ofNullable(repository.findByUsernameAndPassword("admin", "admin"))
                .ifPresent(System.out::println);
    }

    @Test
    void findByUsernameLike() {
        Page<User> userPage = repository.findByUsernameLike("test", PageRequest.of(1, 3, Sort.by(Sort.Direction.DESC, "id")));
        Optional.ofNullable(userPage).ifPresent(page -> {
            System.out.println(page.getTotalElements());
            System.out.println(page.getTotalPages());
            System.out.println(page.getContent());
            System.out.println(page.getNumber());
            System.out.println(page.getNumberOfElements());
            System.out.println(page.getPageable());
            System.out.println(page.getSize());
            System.out.println(page.getSort());
            System.out.println("========\n" + page + "\n========");
        });
    }

    @Test
    void findByUsernameLikeAndEmail() {
        Optional.ofNullable(repository.findByUsernameLikeAndEmail(null, "test3@ttt"))
                .ifPresent(System.out::println);
    }

    @Test
    void dynamicUpdate() {
        User user = new User();
        user.setId(2);
        user.setUsername("test2");

        System.out.println(repository.dynamicUpdate(user));
    }
}
