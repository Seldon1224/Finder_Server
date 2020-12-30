package edu.swjtu.finderapp;

import edu.swjtu.finderapp.dao.UserRepository;
import edu.swjtu.finderapp.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FinderServerApplicationTests {

    @Autowired
    UserRepository userRepository;

    @Test
    void contextLoads() {

        User user = new User("lxd","1234","12853254",null);
        userRepository.save(user);
    }

}
