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
    int ORDER_TYPE_COST_RENEWAL = 2;// 会费续费

    int ORDER_STATUS_SUCCESS = 1; // 订单状态-成功
    int ORDER_STATUS_ERROR = 2; //  订单状态-失败
    int ORDER_STATUS_REFUSE_SUCCESS = 4; // 退款成功
    int ORDER_STATUS_REFUSE_ERROR = 5; // 退款失败

    int flow_enter = 2; // 收入
    int flow_exit = 1; // 支出


    String WEIXIN = "WEIXIN";
    String ALIPAY = "ALIPAY";

    int VALUE_ADD_SERVICE_TYPE_INTEREST = 1;    // 感兴趣
    int VALUE_ADD_SERVICE_TYPE_ACQUAINTANCE = 2; // 熟人

    int SQL_VALUE_ADD_SERVICE_TYPE_INTEREST = 3;    // 感兴趣
    int SQL_VALUE_ADD_SERVICE_TYPE_ACQUAINTANCE = 4; // 熟人
    int SQL_VALUE_ADD_SERVICE_TYPE_APPLY = 5; // 报名


    // ==============================机遇=================================
    int ARTICLE_SHOW_FRIEND = 1; // 朋友
    int ARTICLE_SHOW_INTERESTED = 2; // 对我感兴趣
    int ARTICLE_SHOW_INTEREST = 3; // 我感兴趣
    int ARTICLE_SHOW_DAILY = 4; // 日报
    int ARTICLE_SHOW_MAGAZINE = 5; // 杂志
    int ARTICLE_SHOW_SHARE= 6; // 分享
    int ARTICLE_SHOW_OFFICIAL = 7; // 官方

    int ARTICLE_NOT_PROCESS = 1; // 未处理
    int ARTICLE_AGREE = 2;  // 通过
    int ARTICLE_REFUSE = 3; // 未通过
    int ARTICLE_PUBLISH = 4; // 已发布
    int ARTICLE_DEL = 5; // 弃稿

    int ARTICLE_FROM_TYPE_PEPOLE = 1; // 个人
    int ARTICLE_FROM_TYPE_GROUP = 2; // 组织
    int ARTICLE_FROM_TYPE_OFFICIAL = 3; // 官方

    int ARTICLE_SHOW_NOMAL_TYPE = 1; // 正常
    int ARTICLE_SHOW_INTERESTED_TYPE = 2; // 感兴趣
    int ARTICLE_SHOW_NUINTERESTED_TYPE = 3; // 不感兴趣
    int ARTICLE_SHOW_MOVE_TYPE = 4; // 移除
    int ARTICLE_SHOW_SHIELDING_TYPE = 5; // 屏蔽

    // =====================================================================
    int ACTIVITY_BTN_STATUS_NOMAL = 4000; // 正常
    int ACTIVITY_BTN_STATUS_ENOUGH = 4001; // 报名人数已满
    int ACTIVITY_BTN_STATUS_APPLY_END = 4002; // 报名截止
    int ACTIVITY_BTN_STATUS_END = 4003; // 活动结束
    int ACTIVITY_BTN_STATUS_NOT_MEMBER = 4004; // 非组织会员不能报名
    int ACTIVITY_BTN_STATUS_MEMBER = 4005; // 组织会员不能报名
    int ACTIVITY_BTN_STATUS_NOT_INVITATION = 4006; // 非邀请不能报名
}
