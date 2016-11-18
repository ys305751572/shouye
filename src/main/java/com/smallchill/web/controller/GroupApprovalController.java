package com.smallchill.web.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.web.model.Group;
import com.smallchill.web.model.GroupApproval;
import com.smallchill.web.model.GroupExtend;
import com.smallchill.web.service.GroupApprovalService;
import com.smallchill.web.service.GroupExtendService;
import com.smallchill.web.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @Autowired
    GroupApprovalService groupApprovalService;
    @Autowired
    GroupService groupService;
    @Autowired
    GroupExtendService groupExtendService;

    @RequestMapping(value = "/")
    public String groupIndex(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "groupApproval_list.html";
    }

    @ResponseBody
    @RequestMapping(KEY_LIST)
    public Object loadList() {
        Object object = paginate(LIST_SOURCE);
        return object;
    }

    @ResponseBody
    @RequestMapping(value = "/updateStatus")
    public AjaxResult updateStatus(Integer id, Integer status) {
        if(id==null || status==null){
            return error(UPDATE_FAIL_MSG);
        }
        try{

            GroupApproval groupApproval = groupApprovalService.findById(id);
            groupApproval.setStatus(status);
            groupApprovalService.update(groupApproval);

        }catch (RuntimeException e){
            e.printStackTrace();
            return error(UPDATE_FAIL_MSG);
        }

        return success(UPDATE_SUCCESS_MSG);

    }

    @RequestMapping(value = "/permissions")
    public String permissions(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "groupApproval_permissions.html";
    }

    @ResponseBody
    @RequestMapping(value = "/permission_setting")
    public AjaxResult permissionSetting(Integer status) {
        Group group = (Group) ShiroKit.getSession().getAttribute("groupAdmin");
        try{
            group.getId();

        }catch (RuntimeException e){
            e.printStackTrace();
            return error(UPDATE_FAIL_MSG);
        }

        return success(UPDATE_SUCCESS_MSG);

    }



}
