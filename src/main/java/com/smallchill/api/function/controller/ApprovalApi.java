package com.smallchill.api.function.controller;

import com.smallchill.api.common.exception.*;
import com.smallchill.api.common.model.ErrorType;
import com.smallchill.api.function.meta.validate.*;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.toolbox.Record;
import com.smallchill.web.model.UserApproval;
import com.smallchill.web.service.GroupApprovalService;
import com.smallchill.web.service.UserApprovalService;
import com.smallchill.web.service.UserGroupService;
import com.smallchill.web.service.impl.BothUserHasApprovalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 审核api
 * Created by yesong on 2016/10/31 0031.
 */
@RequestMapping(value = "/api/approval")
@Controller
public class ApprovalApi extends BaseController {

    @Autowired
    private UserApprovalService userApprovalService;
    @Autowired
    private UserGroupService userGroupService;
    @Autowired
    private GroupApprovalService groupApprovalService;

    /**
     * 发送请求信息
     *
     * @param ua 请求信息
     * @return result
     */
    @RequestMapping(value = "/introduce")
    @ResponseBody
    @Before(UserApprovalValidate.class)
    public String introduce(UserApproval ua, String toUserIds) {
        try {
            if (ua.getGroupId() != null) {
                userApprovalService.toGroup(ua);
            } else if (ua.getIntroduceUserId() != null) {
                userApprovalService.toUserTwoWay(ua);
            } else {
                userApprovalService.toUserOneWay(ua, toUserIds);
            }
        } catch (UserInOthersBlankException e) {
            return fail(ErrorType.ERROR_CODE_APP_USERINBLANK);
        } catch (UserHasApprovalException e) {
            return fail(ErrorType.ERROR_CODE_APP_USERHASAPPROVAL);
        } catch (UsernotFriendException e) {
            return fail(ErrorType.ERROR_CODE_APP_USERNOTFRIEND);
        } catch (BothUserHasApprovalException e) {
            return fail(ErrorType.ERROR_CODE_APP_USERHASAPPROVAL);
        } catch (UserHasFriendException e) {
            return fail(ErrorType.ERROR_CODE_APP_USERHASJOIN);
        } catch (UserInMyBlankException e) {
            return fail(ErrorType.ERROR_CODE_APP_USERINMYBLANK);
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
        return success();
    }


    /**
     * 审核请求处理
     *
     * @param userApproval 请求信息
     * @return result
     */
    @RequestMapping(value = "/audit")
    @ResponseBody
    @Before(UserAduitValidate.class)
    public String audit(UserApproval userApproval) {
        try {
            if (userApproval.getStatus() == 1) {
                // 同意
                userApprovalService.userApprovalAgree(userApproval);
            } else if (userApproval.getStatus() == 2) {
                // 拒绝
                userApprovalService.userApprovalRefuse(userApproval);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
        return success();
    }

    /**
     * 对我感兴趣用户结识API
     *
     * @param ua 请求信息
     * @return result
     */
    @PostMapping(value = "/auditinterest")
    @ResponseBody
    @Before(FromUserIdAndtoUserIdValidate.class)
    public String auditOfInterest(UserApproval ua) {
        try {
            userApprovalService.auditOfInterest(ua);
        } catch (BothUserHasApprovalException e) {
            return fail(ErrorType.ERROR_CODE_APP_USERHASAPPROVAL);
        } catch (UserInOthersBlankException e) {
            return fail(ErrorType.ERROR_CODE_APP_USERINBLANK);
        } catch (UserHasApprovalException e) {
            return fail(ErrorType.ERROR_CODE_APP_USERHASAPPROVAL);
        } catch (UsernotFriendException e) {
            return fail(ErrorType.ERROR_CODE_APP_USERNOTFRIEND);
        } catch (UserInMyBlankException e) {
            return fail(ErrorType.ERROR_CODE_APP_USERINMYBLANK);
        } catch (UserHasFriendException e) {
            return fail(ErrorType.ERROR_CODE_APP_USERHASJOIN);
        }
        return success();
    }

    /**
     * (推荐)审核
     *
     * @return result
     */
    @RequestMapping(value = "/audit/introduce")
    @ResponseBody
    public String auditByIntroduce(UserApproval ua) {

        if (ua.getStatus() == 1) {
            // 同意
            try {
                userApprovalService.auditAgreeByIntroduce(ua);
            } catch (ApprovalFailException e) {
                return fail(ErrorType.ERROR_CODE_APP_APPROVAL_FAIL);
            }
        } else {
            // 拒绝
            userApprovalService.auditRefuseByIntroduce(ua);
        }
        return success();
    }

    /**
     * 用户是否在此组织
     *
     * @param userId  当前用户ID
     * @param groupId 组织ID
     * @return result
     */
    @PostMapping(value = "/isingroup")
    @ResponseBody
    @Before(GroupUserValidate.class)
    public String isInGroup(Integer userId, Integer groupId) {
        int count = userGroupService.count("user_id = #{userId} and group_id = #{groupId}",
                Record.create().set("userId", userId).set("groupId", groupId));
        if (count > 0) {
            return success("0", "isingroup");
        } else {
            return success("1", "isingroup");
        }
    }

    /**
     * 组织申请信息
     *
     * @param groupId 组织ID
     * @param userId  用户ID
     * @return result
     */
    @PostMapping(value = "/group/introduce/info")
    @ResponseBody
    @Before(GroupUserValidate.class)
    public String groupIntroduceInfo(Integer groupId, Integer userId) {
        return success(groupApprovalService.gaInfo(groupId));
    }

    /**
     * 组织邀请审核
     *
     * @param groupId 组织ID
     * @param userId  当前用户ID
     * @return result
     */
    @PostMapping(value = "/auditGroup")
    @ResponseBody
    @Before(GroupUserValidate.class)
    public String auditGroup(Integer groupId, Integer userId, Integer status) {
        try {
            if (status == 1) {
                groupApprovalService.userAuditGroupAgree(groupId, userId);
            }
            else {
                groupApprovalService.userAuditGroupRefuse(groupId, userId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success();
    }
}
