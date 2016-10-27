package com.smallchill.api.function.controller;

import com.smallchill.api.common.exception.UserHasApprovalException;
import com.smallchill.api.common.exception.UserHasJoinGroupException;
import com.smallchill.api.common.exception.UserInBlankException;
import com.smallchill.api.common.model.ErrorType;
import com.smallchill.api.function.meta.validate.GroupValidator;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.toolbox.grid.JqGrid;
import com.smallchill.web.model.GroupApproval;
import com.smallchill.web.service.GroupApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 组织API
 * Created by yesong on 2016/10/26 0026.
 */
@Controller
@RequestMapping(value = "/api/group")
public class GroupApi extends BaseController {

    private static String LIST_SOURCE = "Group.list";

    @Autowired
    private GroupApprovalService groupApprovalService;

    /**
     * 组织列表
     *
     * @return result
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public String list() {
        JqGrid page;
        try {
            page = apiPaginate(LIST_SOURCE);
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
        return success(page);
    }

    /**
     * 加入组织
     *
     * @return result
     */
    @RequestMapping(value = "/join")
    @ResponseBody
    @Before(GroupValidator.class)
    public String join(GroupApproval ga) {
        try {
            groupApprovalService.join(ga);
        } catch (UserHasApprovalException e) {
            return fail(ErrorType.ERROR_CODE_USERHASAPPROVAL);
        } catch (UserHasJoinGroupException e) {
            return fail(ErrorType.ERROR_CODE_USERHASJOIN);
        } catch (UserInBlankException e) {
            return fail(ErrorType.ERROR_CODE_USERINBLANK);
        }
        return success();
    }
}
