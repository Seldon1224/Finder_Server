package edu.swjtu.finderapp.controller;

import edu.swjtu.finderapp.common.IStatusMessage;
import edu.swjtu.finderapp.common.ResponseResult;
import edu.swjtu.finderapp.common.ServiceException;
import edu.swjtu.finderapp.dao.UserRepository;
import edu.swjtu.finderapp.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import edu.swjtu.finderapp.service.FileUpAndDownService;

import java.util.HashMap;
import java.util.Map;


@Controller
public class MainController {

    private final UserRepository userRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    private final FileUpAndDownService fileUpAndDownService;
    @Autowired
    public MainController(UserRepository userRepository, FileUpAndDownService fileUpAndDownService) {
        this.userRepository = userRepository;
        this.fileUpAndDownService = fileUpAndDownService;
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
    @ResponseBody
    public ResponseResult SignUpSubmit(@RequestParam(value = "file", required = false) MultipartFile file,
                        @ModelAttribute User user) {
        ResponseResult result = new ResponseResult();
        try {
            String name = user.getUser_name();
            String pic_path = null;
            if(file != null){
                Map<String, Object> resultMap = upload(file);
                if (IStatusMessage.SystemStatus.SUCCESS.getMessage().equals(resultMap.get("result"))) {
                    pic_path = resultMap.get("path").toString();
                }
            }
            int sameNum = userRepository.findSameUserNum(name);
            if(sameNum != 0) { //该用户已存在
                result.setCode(IStatusMessage.SystemStatus.USER_EXIST.getCode());
                result.setMessage(IStatusMessage.SystemStatus.USER_EXIST.getMessage());
            }
            else{
                user.setUser_pic_path(pic_path);
                userRepository.save(user);
                result.setCode(IStatusMessage.SystemStatus.USER_SAVED.getCode());
                result.setMessage(IStatusMessage.SystemStatus.USER_SAVED.getMessage());
            }
        }catch(ServiceException e){
            e.printStackTrace();
            LOGGER.error(">>>>>>用户注册失败，e={}", e.getMessage());
            result.setCode(IStatusMessage.SystemStatus.USER_SAVED_ERROR.getCode());
            result.setMessage(IStatusMessage.SystemStatus.USER_SAVED_ERROR.getMessage());
        }
        return result;
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseResult LoginSubmit(@ModelAttribute User user) {
        //userRepository.save(user);
        ResponseResult result = new ResponseResult();
        User loginUser = userRepository.findUserByUserName(user.getUser_name());
        if(loginUser != null){
            if(user.getUser_password().equals(loginUser.getUser_password())){
                result.setCode(IStatusMessage.SystemStatus.LOGIN_SUCCESS.getCode());
                result.setMessage(IStatusMessage.SystemStatus.LOGIN_SUCCESS.getMessage());
            }
            else{
                result.setCode(IStatusMessage.SystemStatus.LOGIN_FAIL.getCode());
                result.setMessage(IStatusMessage.SystemStatus.LOGIN_FAIL.getMessage());
            }
            return result;
        }
        result.setCode(IStatusMessage.SystemStatus.LOGIN_NO_USER.getCode());
        result.setMessage(IStatusMessage.SystemStatus.LOGIN_NO_USER.getMessage());
        return result;
    }

    private Map<String, Object> upload(MultipartFile file) throws ServiceException {
        Map<String, Object> returnMap = new HashMap<>();
        try {
            if (!file.isEmpty()) {
                Map<String, Object> picMap = fileUpAndDownService.uploadPicture(file);
                if (IStatusMessage.SystemStatus.SUCCESS.getMessage().equals(picMap.get("result"))) {
                    return picMap;
                } else {
                    returnMap.put("result", IStatusMessage.SystemStatus.ERROR.getMessage());
                    returnMap.put("msg", picMap.get("result"));
                }
            } else {
                LOGGER.info(">>>>>>上传图片为空文件");
                returnMap.put("result", IStatusMessage.SystemStatus.ERROR.getMessage());
                returnMap.put("msg", IStatusMessage.SystemStatus.FILE_UPLOAD_NULL.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("上传图片出错");
        }
        return returnMap;
    }
}
