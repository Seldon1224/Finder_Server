package edu.swjtu.finderapp.common.config;

import edu.swjtu.finderapp.pojo.User;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;


@ControllerAdvice
public class GlobalConfig {
    //全局数据
    @ModelAttribute(value="info")
    public User userinfo() {
        return new User("lxd","123",null, null);
    }
}
