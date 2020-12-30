package edu.swjtu.finderapp.dao;

import edu.swjtu.finderapp.pojo.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>{

    @Query(value = "select * from table_user_info where user_name=?1"
            , nativeQuery = true)
    User findUserByUserName(String name);

    @Query(value = "select count(*) from table_user_info where user_name=?1",
            nativeQuery = true)
    int findSameUserNum(String name);
}
