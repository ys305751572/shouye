package com.smallchill.web.service;

import com.smallchill.api.common.exception.UserHasApprovalException;
import com.smallchill.api.common.exception.UserHasFriendException;
import com.smallchill.api.common.exception.UserInBlankException;
import com.smallchill.api.common.exception.UsernotFriendException;
import com.smallchill.core.base.service.IService;
import com.smallchill.web.model.UserApproval;
import com.smallchill.web.service.impl.BothUserHasApprovalException;

/**
 * 用户审核
 * Generated by yesong.
 * 2016-10-28 14:31:09
 */
public interface UserApprovalService extends IService<UserApproval>{

    /**
     * 发送审核申请-单向；
     * 只发送给接收用户
     * 1.组织审核通过后
     * 2.通过共同熟人结识
     * 3.扫二维码结识
     * 4.附近的人结识
     * 5.熟人的熟人结识
     */
    void toUserOneWay(UserApproval ua) throws UserInBlankException, UserHasApprovalException, UsernotFriendException, BothUserHasApprovalException, UserHasFriendException;

    /**
     * 发送审核申请-双向；
     * 同时发送给接收用户和发送用户
     * 1.引荐
     */
    void toUserTwoWay(UserApproval ua);

    /**
     * 发送审核申请给组织
     * 1.通过组织成员结识
     * 2.通过活动临时群
     * 3.通过查看交集
     */
    void toGroup(UserApproval ua) throws UserInBlankException, UserHasApprovalException, UsernotFriendException, BothUserHasApprovalException, UserHasFriendException;

    /**
     * 组织审核同意
     * @param ua
     */
    void groupApprovalAgree(UserApproval ua);

    /**
     * 组织审核拒绝
     * @param ua
     */
    void groupApprovalRefuse(UserApproval ua);

    /**
     * 组织审核拉黑
     * @param ua
     */
    void groupApprovalBlank(UserApproval ua);

    /**
     * 组织审核移除黑名单
     * @param ua
     */
    void groupApprovalUnBlank(UserApproval ua);

    /**
     * 用户审核同意
     * @param ua
     */
    void userApprovalAgree(UserApproval ua);

    /**
     * 用户审核拒绝
     * @param ua
     */
    void userApprovalRefuse(UserApproval ua);

    /**
     * 用户审核拉黑
     * @param ua
     */
    void userApprovalBlank(UserApproval ua);

    /**
     * 用户审核移除黑名单
     * @param ua
     */
    void userApprovalUnBlank(UserApproval ua);

    void resetStatus(UserApproval ua);
}
