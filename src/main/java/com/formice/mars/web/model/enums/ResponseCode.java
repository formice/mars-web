package com.formice.mars.web.model.enums;

public enum ResponseCode {
    SUCCESS(200,"成功"),
    ERROR(1,"错误"),
    //NEED_REGISTER(10,"需要注册,请授权登录!"),
    //NEED_LOGIN(12,"需要登录,请登录!"),


    //TOMANYLOGIN(11,"账号被挤出."),
    //ILLEGAL_ARGUMENT(2,"ILLEGAL_ARGUMENT"),
    CODE_USER_NOT_LOGIN(10000000, "您还未登录，请登录！"),
    CODE_ACCOUNT_NOT_EXIST(10000001, "账户名不存在"),
    CODE_ACCOUNT_PWD_NOT_MATCH(10000002, "密码输入错误"),
    CODE_USER_IDENTITY_ERROR(10000003, "用户身份错误"),
    CODE_VERFI_CODE_OUT_OF_DATE(10000004, "验证码过期"),
    CODE_VERFI_CODE_INPUT_WRONG(10000005, "验证码输入错误"),
    CODE_NAME_EXIST(10000006, "用户名已经存在"),
    CODE_OLD_PWD_NOT_MATCH(10000007, "老密码不正确"),
    SMS_RETURN_CODE_FAILED(10000008, "短信发送失败"),
    CODE_MOBILE_EXIST(10000009, "手机号已被注册"),
    CODE_MOBILE_BINDED(10000010, "已绑定手机号"),
    CODE_EMAIL_WRONG(10000011, "邮箱格式错误"),
    CODE_PWD_HAS_SETTING(10000012, "密码已经设置");

    private final int code;
    private final String desc;

    ResponseCode(int code, String desc){
        this.code=code;
        this.desc=desc;
    }
    public int getCode(){
        return code;
    }
    public String getDesc(){
        return desc;
    }
}