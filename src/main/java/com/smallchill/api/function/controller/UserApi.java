package com.smallchill.api.function.controller;

import com.smallchill.api.common.kit.ExcludeParams;
import com.smallchill.api.function.meta.intercept.UserApiIntercept;
import com.smallchill.api.function.meta.validate.GlobalUsersValidate;
import com.smallchill.api.function.meta.validate.GroupUserValidate;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.grid.JqGrid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户API
 * Created by yesong on 2016/11/4 0004.
 */
@RequestMapping(value = "/api/user")
@Controller
public class UserApi extends BaseController{

    private static String LIST_SOURCE = "UserInfo.listPage";

    /**
     * 全局用户列表
     * @return result
     */
    @RequestMapping(value = "/global/user/list")
    @ResponseBody
    @Before(GlobalUsersValidate.class)
    public String globalUserlist() {
        JqGrid jqGrid;
        try {
            jqGrid = apiPaginate(LIST_SOURCE, new UserApiIntercept());
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
        return success(jqGrid);
    }

    /**
     * 组织用户列表
     * @return result
     */
    @RequestMapping(value = "/group/list")
    @ResponseBody
    @Before(GroupUserValidate.class)
    public String groupUserList() {
        JqGrid jqGrid;
        try {
            jqGrid = apiPaginate(LIST_SOURCE
                    ,new UserApiIntercept().addRecord(Record.create().set("userId", this.getRequest().getParameter("userId"))),
                    ExcludeParams.create().set("userId"));
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
        return success(jqGrid);
    }
}
