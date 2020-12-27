package edu.swjtu.finderapp.server;

import edu.swjtu.finderapp.server.dao.UserRepository;
import edu.swjtu.finderapp.server.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FinderServerApplicationTests {

    @Autowired
    UserRepository userRepository;

    @Test
    void contextLoads() {

        User user = new User("lxd","1234","12853254");
        userRepository.save(user);
    }

}
