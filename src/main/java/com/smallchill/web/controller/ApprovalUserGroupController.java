package com.smallchill.web.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.web.meta.intercept.GroupAdminIntercept;
import com.smallchill.web.service.AugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 结识
 * Created by shilong on 2016/11/24.
 */
@RequestMapping(value = "/approvalUserGroup")
@Controller
public class ApprovalUserGroupController extends BaseController {

    private static String LIST_SOURCE = "ApprovalUserGroup.list";
    private static String BASE_PATH = "/web/approvalusergroup/";
    private static String CODE = "approvalUserGroup";
    private static String PERFIX = "tb_approval_user_group";

    @Autowired
    AugService augService;

    @RequestMapping(value = "/")
    public String groupIndex(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "approvalUserGroup_list.html";
    }


    @ResponseBody
    @RequestMapping(KEY_LIST)
    public Object loadList() {
        Object object = paginate(LIST_SOURCE,new GroupAdminIntercept());
        return object;
    }

    @RequestMapping("/loadToUserId" + "/{userId}")
    @ResponseBody
    public Object loadOne(@PathVariable Integer userId){
        List list = augService.loadOne(userId);
        return list;
    }


    /**
     *修改审核状态
     */
    @ResponseBody
    @RequestMapping(value = "/updateStatus")
    public AjaxResult updateStatus(Integer id, Integer status) {
        if(id==null || status==null){
            return error(UPDATE_FAIL_MSG);
        }
        try{
            augService.updateStatus(id,status);
        }catch (RuntimeException e){
            e.printStackTrace();
            return error(UPDATE_FAIL_MSG);
        }
        return success(UPDATE_SUCCESS_MSG);

    }


}
