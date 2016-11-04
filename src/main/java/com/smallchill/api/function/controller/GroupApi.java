package com.smallchill.api.function.controller;

import com.smallchill.api.common.exception.UserHasApprovalException;
import com.smallchill.api.common.exception.UserHasJoinGroupException;
import com.smallchill.api.common.exception.UserInBlankException;
import com.smallchill.api.common.kit.ExcludeParams;
import com.smallchill.api.common.model.ErrorType;
import com.smallchill.api.common.model.Result;
import com.smallchill.api.function.meta.intercept.GroupApiIntercept;
import com.smallchill.api.function.meta.other.Convert;
import com.smallchill.api.function.meta.validate.GroupJoinValidator;
import com.smallchill.api.function.meta.validate.GroupPageValidator;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.grid.JqGrid;
import com.smallchill.web.model.Group;
import com.smallchill.web.model.GroupApproval;
import com.smallchill.web.service.GroupApprovalService;
import com.smallchill.web.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 组织API
 * Created by yesong on 2016/10/26 0026.
 */
@Controller
@RequestMapping(value = "/api/group")
public class GroupApi extends BaseController {

    private static String LIST_SOURCE = "Group.listPage";

    @Autowired
    private GroupApprovalService groupApprovalService;

    @Autowired
    private GroupService groupService;

    /**
     * 组织列表
     *
     * @return result
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    @Before(GroupPageValidator.class)
    public String list() {
        JqGrid page;
        try {
            page = apiPaginate(LIST_SOURCE,
                    new GroupApiIntercept().addRecord(Record.create().set("userId", this.getRequest().getParameter("userId"))),
                    ExcludeParams.create().set("userId"));
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
        return success(page);
    }

    /**
     * 组织详情
     *
     * @param groupId 组织ID
     * @return result
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    @Before(GroupJoinValidator.class)
    public String detail(Integer groupId) {
        String sql = "select title1,content1,is_open1,title2,content2,is_open2,title3,content3,is_open3 from tb_group where id = #{groupId}";
        Record record = Db.init().selectOne(sql, Record.create().set("groupId", groupId));
        List<Record> list = Convert.recordToGroupDetail(record);
        return success(list);
    }

    /**
     * 加入组织
     *
     * @return result
     */
    @RequestMapping(value = "/join")
    @ResponseBody
    @Before(GroupJoinValidator.class)
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
