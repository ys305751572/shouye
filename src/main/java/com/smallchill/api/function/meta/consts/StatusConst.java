package com.smallchill.api.function.meta.consts;

/**
 * 状态
 * Created by yesong on 2016/12/3 0003.
 */
public interface StatusConst {

    int WAITING = 1; // 未处理
    int AGREE = 2; // 同意
    int REFUSE = 3; //拒绝
    int PASS = 4; // 忽略
    int BLACK = 5; // 拉黑

    int NOT_PAY = 1; // 未支付
    int HAVE_PAY = 2; // 已支付

    int INITIATIVE = 1; // 主动
    int INVITATION = 2; // 邀请

    int ORDER_TYPE_COST = 1; // 年费

    int ORDER_STATUS_SUCCESS = 1; // 订单状态-成功
    int ORDER_STATUS_ERROR = 2; //  订单状态-失败

    String WEIXIN = "WEIXIN";
    String ALIPAY = "ALIPAY";
}