package com.smallchill.web.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.web.meta.intercept.GroupAdminIntercept;
import com.smallchill.web.model.*;
import com.smallchill.web.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

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
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    UserGroupService userGroupService;

    @RequestMapping(value = "/")
    public String groupIndex(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "groupApproval_list.html";
    }

    @ResponseBody
    @RequestMapping(KEY_LIST)
    public Object loadList() {
        Object object = paginate(LIST_SOURCE,new GroupAdminIntercept());
        return object;
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
            groupApprovalService.updateStatus(id,status);
        }catch (RuntimeException e){
            e.printStackTrace();
            return error(UPDATE_FAIL_MSG);
        }

        return success(UPDATE_SUCCESS_MSG);

    }

    /**
     * 权限设置
     */
    @RequestMapping(value = "/permissions")
    public String permissions(ModelMap mm) {
        Group group = (Group) ShiroKit.getSession().getAttribute("groupAdmin");
        GroupExtend groupExtend = null;
        if(group!=null && group.getId()!=null){
            groupExtend = groupExtendService.findFirstBy("group_id = #{groupId}",Record.create().set("groupId", group.getId()));
        }
        mm.put("group",group);
        mm.put("groupExtend",groupExtend);
        mm.put("code", CODE);
        return BASE_PATH + "groupApproval_permissions.html";
    }

    /**
     * 权限设置
     */
    @ResponseBody
    @RequestMapping(value = "/permission_setting")
    public AjaxResult permissionSetting(Integer permissionsType,Integer isJoin,Integer costType,Integer cost,Integer sexLimit,Integer industryLimit,Integer domainLimit,Integer provinceLimit,Integer cityLimit,Integer professionalLimit,Integer zyLimit) {
        Group group = (Group) ShiroKit.getSession().getAttribute("groupAdmin");
        if(group==null){
            return error("错误:未找到组织");
        }
        try{
            groupApprovalService.permissionSetting(group,permissionsType,isJoin,costType,cost,sexLimit, industryLimit,domainLimit,provinceLimit,cityLimit,professionalLimit,zyLimit);
        }catch (RuntimeException e){
            e.printStackTrace();
            return error(UPDATE_FAIL_MSG);
        }
        return success(UPDATE_SUCCESS_MSG);

    }

    /**
     * 委任干部页面
     */
    @RequestMapping(value = "/appointed")
    public String appointed(ModelMap mm) {
        Group group = (Group) ShiroKit.getSession().getAttribute("groupAdmin");
        String sql = "SELECT \n" +
                "  tui.user_id AS userId,\n" +
                "  tui.mobile AS mobile\n" +
                "FROM\n" +
                "  tb_user_group tug \n" +
                "  LEFT JOIN tb_user_info tui \n" +
                "    ON tug.user_id = tui.user_id \n" +
                "WHERE 1=1 \n" +
                "AND tui.vip_type = 1 \n" +
                "AND tug.group_id = #{groupId} \n";
        List list = Db.init().selectList(sql,Record.create().set("groupId", group.getId()));
        mm.put("userInfos", list);
        mm.put("num", list.isEmpty() ? 0 : list.size());
        mm.put("code", CODE);
        return BASE_PATH + "groupApproval_appointed.html";
    }

    /**
     * 委任or移除 干部
     */
    @ResponseBody
    @RequestMapping(value = "/appointedSave")
    public AjaxResult appointedSave(Integer userId,Integer status) {
        try{
            groupApprovalService.appointedSave(userId,status);
        }catch (RuntimeException e){
            e.printStackTrace();
            return error("设置错误");
        }
        return success("设置成功");
    }

    /**
     * 干部列表
     */
    @ResponseBody
    @RequestMapping(value = "/cadresList")
    public Object cadresList() {
        Group group = (Group) ShiroKit.getSession().getAttribute("groupAdmin");
        if(group == null){
            return null;
        }
        return groupApprovalService.cadresList(group.getId());
    }

}
