package edu.swjtu.finderapp.controller;

import edu.swjtu.finderapp.service.FileUpAndDownService;
import edu.swjtu.finderapp.common.IStatusMessage;
import edu.swjtu.finderapp.common.ResponseResult;
import edu.swjtu.finderapp.common.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/upload")
public class FileUploadController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadController.class);

    private final FileUpAndDownService fileUpAndDownService;

    public FileUploadController(FileUpAndDownService fileUpAndDownService) {
        this.fileUpAndDownService = fileUpAndDownService;
    }

    @GetMapping
    public String GetUploadController(){
        return "fileUpload";
    }

    @RequestMapping(value = "/setFileUpload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult setFileUpload(@RequestParam(value = "file", required = false) MultipartFile file) {
        ResponseResult result = new ResponseResult();
        try {
            Map<String, Object> resultMap = upload(file);
            if (!IStatusMessage.SystemStatus.SUCCESS.getMessage().equals(resultMap.get("result"))) {
                result.setCode(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
                result.setMessage((String) resultMap.get("msg"));
                return result;
            }
            result.setData(resultMap);
        } catch (ServiceException e) {
            e.printStackTrace();
            LOGGER.error(">>>>>>图片上传异常，e={}", e.getMessage());
            result.setCode(IStatusMessage.SystemStatus.ERROR.getCode());
            result.setMessage("上传图片出错");
        }
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

