package com.smallchill.api.common.model;

/**
 * 错误参数
 * Created by yesong on 2016/2/22.
 */
public enum ErrorType {

    ERROR_CODE_SERVER_EXCEPTION("服务器繁忙，请稍后再试", 1001),
    ERROR_CODE_PARAM_EXCEPTION("参数不正确", 1002),

    ERROR_CODE_USERNOTFOUND("该用户不存在", 2001),
    ERROR_CODE_USERHASLOCK("该用户被封号", 2005),
    ERROR_CODE_USERHASFREEZE("该用户被冻结", 2006),
    ERROR_CODE_VALIDATECODE_FAIL("验证码错误", 2002),
    ERROR_CODE_OLDPWD_FAIL("旧密码错误", 2003),
    ERROR_CODE_OLDANDNEWPWD_SAME("旧密码和新密码不能一样", 2004);

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
