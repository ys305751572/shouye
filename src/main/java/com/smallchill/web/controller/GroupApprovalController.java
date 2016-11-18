package com.smallchill.web.controller;

import com.smallchill.common.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * Created by Administrator on 2016/11/17.
 */
@Controller
@RequestMapping("/groupApproval")
public class GroupApprovalController extends BaseController {
    private static String LIST_SOURCE = "GroupApproval.list";
    private static String BASE_PATH = "/web/groupapproval/";
    private static String CODE = "groupApproval";
    private static String PERFIX = "tb_group_approval";

    @RequestMapping(value = "/")
    public String groupIndex(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "groupApproval_list.html";
    }

}
