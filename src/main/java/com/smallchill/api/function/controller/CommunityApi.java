package com.smallchill.api.function.controller;

import com.smallchill.api.function.meta.intercept.UserApiIntercept;
import com.smallchill.api.function.meta.validate.GlobalUsersValidate;
import com.smallchill.api.function.meta.validate.GroupUserValidate;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.toolbox.grid.JqGrid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 圈脉API
 * Created by yesong on 2016/11/3 0003.
 */
@RequestMapping(value = "/api/community")
@Controller
public class CommunityApi extends BaseController{

    private static String LIST_SOURCE = "UserInfo.listPage";

    /**
     * 组织列表
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

    @RequestMapping(value = "/group/user/list")
    @ResponseBody
    @Before(GroupUserValidate.class)
    public String groupUserList() {
        JqGrid jqGrid;
        try {
            jqGrid = apiPaginate(LIST_SOURCE, new UserApiIntercept());
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
        return success(jqGrid);
    }
}
