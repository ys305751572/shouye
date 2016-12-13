package com.smallchill.api.common.model;

/**
 * 错误参数
 * Created by yesong on 2016/2/22.
 */
public enum ErrorType {

    ERROR_CODE_SERVER_EXCEPTION("服务器繁忙，请稍后再试", 1001),
    ERROR_CODE_PARAM_EXCEPTION("参数不正确", 1002),
    ERROR_CODE_AVATER_EXCEPTION("请选择要上传的文件",1003),
    ERROR_CODE_TYPE_EXCEPTION("请选择要上传的文件",1003),

    ERROR_CODE_USERNOTFOUND("该用户不存在", 2001),
    ERROR_CODE_VALIDATECODE_FAIL("验证码错误", 2002),
    ERROR_CODE_USERHASLOCK("该用户被封号", 2003),
    ERROR_CODE_USERHASFREEZE("该用户被冻结", 2004),
    ERROR_CODE_USERHASEXTIS("该用户已存在", 2005),

    ERROR_CODE_USERHASJOIN("您已加入过该组织,请不要重复加入", 3001),
    ERROR_CODE_USERHASAPPROVAL("您已发送过申请,请耐心等待", 3002),
    ERROR_CODE_USERINBLANK("您已被该群加入黑名单,无法加入", 3003),
    ERROR_CODE_OVER_INTREST_MAX_NUM("您已达到感兴趣人数上限", 3004),
    ERROR_CODE_OVER_ACQUAINTANCES_MAX_NUM("您已达到熟人人数上限", 3005),

    ERROR_CODE_APP_USERHASJOIN("您已经跟该用户为好友关系", 4001),
    ERROR_CODE_APP_USERHASAPPROVAL("尚有未处理的请求，无法重复提交", 4002),
    ERROR_CODE_APP_USERNOTFRIEND("您跟该用户尚未结识，无法申请加入熟人", 4004),
    ERROR_CODE_APP_USERINBLANK("您已被该用户加入黑名单,无法加入", 4003),
    ERROR_CODE_APP_USERINMYBLANK("该用户在您的黑名单中，请先从黑名单中移除", 4004),
    ERROR_CODE_APP_MYSELF("您不能向自己发送好友请求", 4005),
    ERROR_CODE_APP_MYSELF2("您不能审核自己", 4006),
    ERROR_CODE_APP_APPROVAL_FAIL("请求消息已失效", 4007),

    ERROR_CODE_APP_NOT_MANAGER_FAIL("您不是该组织干事", 5001),

    ERROR_CODE_APP_CANNOT_JOIN_GROUP_FAIL("您不符合加入组织要求", 6001),
    ERROR_CODE_APP_USERINFO_EXTEND_FAIL("添加人数超出上限", 7001),
    ERROR_CODE_APP_USERINFO_EXTEND_PRICE_FAIL("传入金额有误", 7001),
    ERROR_CODE_APP_PAYERROR_REFUND_FAIL("退款失败", 9001);

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
