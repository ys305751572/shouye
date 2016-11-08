package com.smallchill.web.service;

import com.smallchill.api.common.exception.UserHasApprovalException;
import com.smallchill.api.common.exception.UserHasJoinGroupException;
import com.smallchill.api.common.exception.UserInOthersBlankException;
import com.smallchill.core.base.service.IService;
import com.smallchill.web.model.GroupApproval;

/**
 * Generated by Blade.
 * 2016-10-27 11:45:35
 */
public interface GroupApprovalService extends IService<GroupApproval> {

    void join(GroupApproval ga) throws UserHasApprovalException, UserHasJoinGroupException, UserInOthersBlankException;
}
