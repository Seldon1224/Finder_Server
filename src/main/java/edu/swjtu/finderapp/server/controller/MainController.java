package edu.swjtu.finderapp.server.controller;

import edu.swjtu.finderapp.server.dao.UserRepository;
import edu.swjtu.finderapp.server.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class MainController {
    private final UserRepository userRepository;

    @Autowired
    public MainController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/signup")
    public String GetSignupView(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @GetMapping("/login")
    public String GetLoginView(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/signup")
    public @ResponseBody
    String SignUpSubmit(@ModelAttribute User user) {
        String name = user.getUser_name();
        String password = user.getUser_password();
        String phone = user.getUser_phone();

        int sameNum = userRepository.findSameUserNum(name);
        if(sameNum != 0) {
            return "exist";
        }
        else if(name.length() <= 10 && password.length() <= 17 && phone.length() == 11) {
            userRepository.save(user);
            return "saved";
        }
        else{
            return "error";
        }

    }
    @PostMapping("/login")
    public @ResponseBody
    String LoginSubmit(@ModelAttribute User user) {
        //userRepository.save(user);
        User loginUser = userRepository.findUserByUserName(user.getUser_name());
        if(loginUser != null){
            if(user.getUser_password().equals(loginUser.getUser_password()))
                return "success";
            else
                return "wrong";
        }
        return "nouser";
    }
}
