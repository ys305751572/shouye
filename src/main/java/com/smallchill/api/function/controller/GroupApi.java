package com.smallchill.api.function.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.toolbox.grid.JqGrid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 组织API
 * Created by yesong on 2016/10/26 0026.
 */
@Controller
@RequestMapping(value = "/api/group")
public class GroupApi extends BaseController{

    private static String LIST_SOURCE = "Group.list";

    /**
     * 组织列表
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
     * @return
     */
    @RequestMapping(value = "/join")
    @ResponseBody
    public String join() {
        return success();
    }
}
