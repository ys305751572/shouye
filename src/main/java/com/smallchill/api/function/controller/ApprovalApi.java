package com.smallchill.api.function.controller;

import com.smallchill.api.common.exception.*;
import com.smallchill.api.common.model.ErrorType;
import com.smallchill.api.function.meta.validate.UserAduitValidate;
import com.smallchill.api.function.meta.validate.UserApprovalValidate;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Before;
import com.smallchill.web.model.UserApproval;
import com.smallchill.web.service.UserApprovalService;
import com.smallchill.web.service.impl.BothUserHasApprovalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 审核api
 * Created by yesong on 2016/10/31 0031.
 */
@RequestMapping(value = "/api/approval")
@Controller
public class ApprovalApi extends BaseController {

    @Autowired
    private UserApprovalService userApprovalService;

    /**
     * 发送请求信息
     *
     * @param ua 请求信息
     * @return result
     */
    @RequestMapping(value = "/introduce")
    @ResponseBody
    @Before(UserApprovalValidate.class)
    public String introduce(UserApproval ua) {
        try {
            if (ua.getGroupId() != null) {
                userApprovalService.toGroup(ua);
            } else if (ua.getIntroduceUserId() != null) {
                userApprovalService.toUserTwoWay(ua);
            } else {
                userApprovalService.toUserOneWay(ua);
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
     * (引荐)审核
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
}
