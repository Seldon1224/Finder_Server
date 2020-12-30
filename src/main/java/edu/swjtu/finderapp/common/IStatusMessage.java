package edu.swjtu.finderapp.common;

public interface IStatusMessage {


    String getCode();
    String getMessage();

    enum SystemStatus implements IStatusMessage {

        SUCCESS("1000", "操作成功"), //请求成功
        ERROR("1001", "网络异常，请稍后重试~"),
        FILE_UPLOAD_NULL("1002","上传图片为空文件"),
        PARAM_ERROR("1003","图片参数错误"),      //请求失败
        USER_EXIST("1004","用户已存在"),
        USER_SAVED("1005","用户已保存"),
        USER_SAVED_ERROR("1006","用户保存出错"),
        LOGIN_SUCCESS("1007","登陆成功"),
        LOGIN_FAIL("1008","密码错误，登录失败"),
        LOGIN_NO_USER("1009","用户不存在,登录失败");


        private String code;
        private String message;

        SystemStatus(String code, String message) {
            this.code = code;
            this.message = message;
        }
        public String getCode() {
            return this.code;
        }
        public String getMessage() {
            return this.message;
        }
    }
}
