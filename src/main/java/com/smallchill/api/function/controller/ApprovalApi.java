package com.smallchill.api.function.controller;

import com.smallchill.api.common.exception.UserHasApprovalException;
import com.smallchill.api.common.exception.UserHasFriendException;
import com.smallchill.api.common.exception.UserInBlankException;
import com.smallchill.api.common.exception.UsernotFriendException;
import com.smallchill.api.common.model.ErrorType;
import com.smallchill.common.base.BaseController;
import com.smallchill.web.model.UserApproval;
import com.smallchill.web.service.UserApprovalService;
import com.smallchill.web.service.impl.BothUserHasApprovalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 审核系统
 * Created by yesong on 2016/10/31 0031.
 */
@RequestMapping(value = "/api/approval")
@Controller
public class ApprovalApi extends BaseController {

    @Autowired
    private UserApprovalService userApprovalService;

    @RequestMapping(value = "/introduce")
    @ResponseBody
    public String introduce(UserApproval ua) {
        try {
            if (ua.getGroupId() != null) {
                userApprovalService.toGroup(ua);
            } else if (ua.getIntroduceUserId() != null) {
                userApprovalService.toUserTwoWay(ua);
            } else {
                userApprovalService.toUserOneWay(ua);
            }
        } catch (UserInBlankException e) {
            return fail(ErrorType.ERROR_CODE_APP_USERINBLANK);
        } catch (UserHasApprovalException e) {
            return fail(ErrorType.ERROR_CODE_APP_USERHASAPPROVAL);
        } catch (UsernotFriendException e) {
            return fail(ErrorType.ERROR_CODE_APP_USERNOTFRIEND);
        } catch (BothUserHasApprovalException e) {
            return fail(ErrorType.ERROR_CODE_APP_USERHASAPPROVAL);
        } catch (UserHasFriendException e) {
            return fail(ErrorType.ERROR_CODE_APP_USERHASJOIN);
        }
        return success();
    }

    @RequestMapping(value = "/audit")
    @ResponseBody
    public String audit(UserApproval userApproval) {
        try {
            if (userApproval.getStatus() == 1) {
                userApprovalService.userApprovalAgree(userApproval);
            } else if (userApproval.getStatus() == 2) {
                userApprovalService.userApprovalRefuse(userApproval);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
        return success();
    }
}
