package com.smallchill.web.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.web.meta.intercept.GroupApprovalIntercept;
import com.smallchill.web.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2016/11/22.
 */
@RequestMapping(value = "/userGroup")
@Controller
public class UserGroupController extends BaseController {

    private static String LIST_SOURCE = "UserGroup.list";
    private static String BASE_PATH = "/web/usergroup/";
    private static String CODE = "userGroup";
    private static String PERFIX = "tb_user_group";

    @Autowired
    UserGroupService userGroupService;

    @RequestMapping(value = "/")
    public String groupIndex(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "userGroup_list.html";
    }

    @ResponseBody
    @RequestMapping(KEY_LIST)
    public Object loadList() {
        Object object = paginate(LIST_SOURCE,new GroupApprovalIntercept());
        return object;
    }


}
