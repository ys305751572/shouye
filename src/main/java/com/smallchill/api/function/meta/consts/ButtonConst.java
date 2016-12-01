package com.smallchill.api.function.meta.consts;

/**
 * 按钮值
 * Created by yesong on 2016/12/1 0001.
 */
public interface ButtonConst {

    int INTEREST = 3002;
    int JOIN = 3001;
    int WAITING = 3003; // 等待审核
    int REFUSE = 3004;  // 审核不通过
    int INTERESTED = 3005; // 已感兴趣

    Integer ALLOW_CLICK = 1;  // 按钮可点击
    Integer UNALLOW_CLICK = 2; // 按钮不可点击


    int applyFriend_type = 1001;              // 申请好友
    int applyAcquaintances_type = 1002;       // 申请熟人
    int insterest_type = 1003;                // 感兴趣
    int intersection_type = 1004;             // 查看交集
    int queryUserAcquaintances_type = 1005;   // 查看对方熟人
    int recommendFriend_type = 1006;          // 推荐朋友
    int apply_waiting = 1007;                 // 等待对方审核
    int apply_refuse = 1008;                  // 审核不通过
    int apply_group_waiting = 1009;           // 等待组织引荐审核
}
