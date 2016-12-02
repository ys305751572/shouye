package com.smallchill.api.function.meta.consts;

/**
 * 消息相关静态配置信息
 * Created by yesong on 2016/10/27 0027.
 */
public interface MessageConts {

    // 消息类型
    Integer MESSAGE_TYPE_NORMAL = 0; // 普通消息
    Integer MESSAGE_TYPE_ACTION = 1; // 事件消息

    // 消息角色
    Integer MESSAGE_ROLE_SYS = 0; // 系统
    Integer MESSAGE_ROLE_USER = 1; // 个人
    Integer MESSAGE_ROLE_GROUP = 2; // 组织


    //==========================消息表头==============================
    String MSG_USER_AUDIT = "[加入申请]";
    String MSG_USER_AUDIT_CANAL = "[取消会员]";
    String MSG_GROUP = "[组织消息]";
    String MSG_SYS = "[系统消息]";
    String MSG_WITHDRAWAL_SUCCESS = "[提现成功]";
    String MSG_WITHDRAWAL_FAIL = "[提现失败]";
    String MSG_GROUP_AUDIT = "[成功入驻]";

    //=============================消息内容=============================
    String MSG_USER_AUDIT_CONTENT_FREE_REFUSE = "很遗憾，您的申请未能获得通过";
    String MSG_USER_AUDIT_CONTENT_NOT_FREE_REFUSE = "很遗憾，您的申请未能获得通过,会员费已如数返还(订单号[s])";
    String MSG_USER_AUDIT_CONTENT_AGREE = "恭喜您！您的申请获得通过，欢迎您加入我们";
    String MSG_USER_AUDIT_CONTENT_CANEL = "很遗憾，您已被取消会员资格";

}
