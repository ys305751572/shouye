package com.smallchill.api.common.model;

/**
 * 错误参数
 * Created by yesong on 2016/2/22.
 */
public enum ErrorType {

    ERROR_CODE_0001("参数不正确", 1002),
    ERROR_CODE_0002("服务器繁忙，请稍后再试", 1001),

    ERROR_CODE_0003("该用户不存在", 2001),
    ERROR_CODE_0004("验证码错误", 2002),
    ERROR_CODE_0005("旧密码错误", 2003),
    ERROR_CODE_0006("旧密码和新密码不能一样", 2004);

    private String name;
    private int code;

    ErrorType() {
    }

    ErrorType(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
