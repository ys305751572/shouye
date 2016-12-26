package com.smallchill.web.service;

import com.smallchill.api.common.exception.GroupCloseJoinException;
import com.smallchill.api.common.exception.UserHasApprovalException;
import com.smallchill.api.common.exception.UserHasJoinGroupException;
import com.smallchill.api.common.exception.UserInOthersBlankException;
import com.smallchill.api.function.modal.vo.GroupApprovalVo;
import com.smallchill.core.base.service.IService;
import com.smallchill.core.toolbox.support.BladePage;
import com.smallchill.web.model.Group;
import com.smallchill.web.model.GroupApproval;

import java.util.List;

/**
 * Generated by Blade.
 * 2016-10-27 11:45:35
 */
public interface GroupApprovalService extends IService<GroupApproval> {

    /**
     * 用户是否满足加入组织
     * @param userId 当前用户id
     * @param groupId 组织ID
     */
    boolean isMeetConditions(Integer userId, Integer groupId) throws GroupCloseJoinException;


    /**
     * 加入组织
     * @param ga
     * @throws UserHasApprovalException
     * @throws UserHasJoinGroupException
     * @throws UserInOthersBlankException
     */
    int join(GroupApproval ga) throws UserHasApprovalException, UserHasJoinGroupException, UserInOthersBlankException;

    /**
     * 邀请用户加入组织
     * @param userId 用户id
     * @param content 验证信息
     */
    void userInvitation(Integer userId,String content);
    /**
     * 干部列表
     * @param groupId
     * @return
     */
    BladePage cadresList(Integer groupId);

    /**
     * 委任or移除 干部
     * @param id    组织用户关联id
     * @param status    是否干部
     */
    void appointedSave(Integer id,Integer status);

    /**
     * 修改审核状态
     * @param id
     * @param status
     */
    void updateStatus(Integer id, Integer status);

    /**
     * 权限设置
     */
    void permissionSetting(Group group,Integer permissionsType,Integer isJoin,Integer isIntroduce,Integer costType,Double cost,Integer sexLimit,Integer industryLimit,Integer domainLimit,Integer provinceLimit,Integer cityLimit,Integer professionalLimit,Integer zyLimit);

    /**
     * 申请页信息
     * @param groupId 组织ID
     * @return vo
     */
    GroupApprovalVo gaInfo(Integer groupId);

    /**
     * 组织邀请审核 - 同意
     * @param groupId 组织ID
     * @param userId 用户ID
     */
    void userAuditGroupAgree(Integer groupId, Integer userId);

    /**
     * 组织邀请审核 -- 拒绝
     * @param groupId 组织ID
     * @param userId 用户ID
     */
    void userAuditGroupRefuse(Integer groupId, Integer userId);

    /**
     * 修改入群申请信息的支付状态
     * @param gaId
     */
    void setPaiedStatusSuccess(int gaId);

    /**
     * 判断拒绝申请信息是否已经超过72小时
     * @param throughTime 处理时间
     * @return boolean
     */
    boolean isOverRefuseMaxTime(Long throughTime, Integer gaId);

    /**
     *
     * 查询申请加入用户
     * @param mobile 用户手机
     * @return list
     */
    List findMembersApproval(String mobile);
}
