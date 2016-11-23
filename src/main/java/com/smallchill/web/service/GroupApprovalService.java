package com.smallchill.web.service;

import com.smallchill.api.common.exception.UserHasApprovalException;
import com.smallchill.api.common.exception.UserHasJoinGroupException;
import com.smallchill.api.common.exception.UserInOthersBlankException;
import com.smallchill.api.function.modal.vo.GroupApprovalVo;
import com.smallchill.core.base.service.IService;
import com.smallchill.core.toolbox.support.BladePage;
import com.smallchill.web.model.Group;
import com.smallchill.web.model.GroupApproval;

/**
 * Generated by Blade.
 * 2016-10-27 11:45:35
 */
public interface GroupApprovalService extends IService<GroupApproval> {

    void join(GroupApproval ga) throws UserHasApprovalException, UserHasJoinGroupException, UserInOthersBlankException;

    /**
     * 干部列表
     * @param groupId
     * @return
     */
    BladePage cadresList(Integer groupId);

    /**
     * 委任or移除 干部
     * @param userId    用户id
     * @param status    是否干部
     */
    void appointedSave(Integer userId,Integer status);

    /**
     * 修改审核状态
     * @param id
     * @param status
     */
    void updateStatus(Integer id, Integer status);

    /**
     * 权限设置
     */
    void permissionSetting(Group group,Integer permissionsType,Integer isJoin,Integer costType,Integer cost,Integer sexLimit,Integer industryLimit,Integer domainLimit,Integer provinceLimit,Integer cityLimit,Integer professionalLimit,Integer zyLimit);

    /**
     * 申请页信息
     * @param groupId 组织ID
     * @return vo
     */
    GroupApprovalVo gaInfo(Integer groupId);
}
