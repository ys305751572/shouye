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
}
