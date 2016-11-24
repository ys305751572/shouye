package com.smallchill.web.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.toolbox.kit.JsonKit;
import com.smallchill.web.model.Classification;
import com.smallchill.web.model.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 结识
 * Created by shilong on 2016/11/24.
 */
@RequestMapping(value = "/userApproval")
@Controller
public class UserApprovalController extends BaseController {

    private static String LIST_SOURCE = "UserApproval.list";
    private static String BASE_PATH = "/web/userapproval/";
    private static String CODE = "userApproval";
    private static String PERFIX = "tb_user_group";


    @RequestMapping(value = "/")
    public String groupIndex(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "userApproval_list.html";
    }
}
