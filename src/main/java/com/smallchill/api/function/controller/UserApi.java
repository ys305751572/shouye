package com.smallchill.api.function.controller;

import com.smallchill.api.common.exception.UserExitsException;
import com.smallchill.api.common.kit.ExcludeParams;
import com.smallchill.api.function.meta.intercept.UserApiIntercept;
import com.smallchill.api.function.meta.other.Convert;
import com.smallchill.api.function.meta.validate.GlobalUsersValidate;
import com.smallchill.api.function.meta.validate.GroupPageValidator;
import com.smallchill.api.function.meta.validate.GroupUserValidate;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.grid.JqGrid;
import com.smallchill.web.model.UserInfo;
import com.smallchill.web.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserInfoService userInfoService;
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

    /**
     * 用户详情
     * @param userId 用户ID
     * @return result
     */
    @RequestMapping(value = "/info")
    @ResponseBody
    @Before(GroupPageValidator.class)
    public String info(Integer userId) {
        Record record;
        try {
            record = userInfoService.findUserInfoDetail(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
        return success(record,"userInfo");
    }

    /**
     * 修改用户信息
     * @param userInfo 用户信息
     * @return result
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    @Before(GroupPageValidator.class)
    public String update(UserInfo userInfo) {

        UserInfo userInfo1;
        Record record = null;
        try {
            userInfo1 = userInfoService.updateUserInfo(userInfo,null);
            record = Convert.userInfoToRecord(userInfo1);
        } catch (UserExitsException e) {
            e.printStackTrace();
        }
        return success(record,"userInfo");
    }
}
