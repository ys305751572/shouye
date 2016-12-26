package com.smallchill.web.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.grid.JqGrid;
import com.smallchill.core.toolbox.support.BladePage;
import com.smallchill.system.service.DictService;
import com.smallchill.web.meta.intercept.GroupAdminIntercept;
import com.smallchill.web.model.*;
import com.smallchill.web.service.*;
import org.beetl.sql.core.kit.CaseInsensitiveHashMap;
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
    @Autowired
    ProvinceCityService provinceCityService;
    @Autowired
    DictService dictService;

    @RequestMapping(value = "/")
    public String groupIndex(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "groupApproval_list.html";
    }

    @ResponseBody
    @RequestMapping(KEY_LIST)
    public Object loadList() {
        JqGrid object = (JqGrid) paginate(LIST_SOURCE,new GroupAdminIntercept());
        matchType(object);
        return object;
    }

    private void matchType(JqGrid bladePage) {
        Collection<CaseInsensitiveHashMap> list = bladePage.getRows();
        for (CaseInsensitiveHashMap map : list) {
            Object targetType = map.get("targetType");
            Object matchType = map.get("matchType");
            int userId = Integer.parseInt(map.get("userId").toString());
            int groupId = Integer.parseInt(map.get("groupId").toString());
            if (targetType != null && Integer.parseInt(targetType.toString()) == 1) {
                // 查询分组名字
                String sql = "select c.`classification` from tb_user_classification uc " +
                        "left join tb_classification c on uc.`classification_id` = c.`id`" +
                        " where c.`group_id` = #{groupId} and uc.`user_id` = #{userId} and uc.`classification_id` = #{matchType} order by uc.`id` desc";
                Record record = Db.init().selectOne(sql, Record.create().set("groupId", groupId).set("userId", userId).set("matchType", Integer.parseInt(matchType.toString())));
                map.put("matchTypeString",record == null ? "" : record.getStr("classification"));
            } else if (targetType != null && Integer.parseInt(targetType.toString()) == 2) {
                // 查询标记名字
                String sql = "SELECT t.`tag` FROM tb_user_tag ut LEFT JOIN tb_tag t ON ut.`tag_id` = t.`id` " +
                        "WHERE ut.`user_id` = #{userId} AND t.`group_id` = #{groupId} and ut.`tag_id` = #{matchType} ORDER BY ut.`id` DESC";
                Record record = Db.init().selectOne(sql, Record.create().set("groupId", groupId).set("userId", userId).set("matchType", Integer.parseInt(matchType.toString())));
                map.put("matchTypeString", record == null ?  "" : record.getStr("tag"));
            } else {
                map.put("matchTypeString","");
            }
        }
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
        mm.put("group",groupService.findById(group.getId()));
        mm.put("groupExtend",groupExtend);
        mm.put("code", CODE);

        //行业
        List domains = dictService.findFather("906");
        mm.put("domains", domains);

        //省
        List<Map<String, Object>> province = provinceCityService.province();
        mm.put("province", province);

        //职业
        List professional = dictService.findFather("910");
        mm.put("professional", professional);

        return BASE_PATH + "groupApproval_permissions.html";
    }

    /**
     * 权限设置
     */
    @ResponseBody
    @RequestMapping(value = "/permission_setting")
    public AjaxResult permissionSetting(Integer permissionsType,Integer isJoin,Integer isIntroduce,Integer costType,Double cost,Integer sexLimit,Integer industryLimit,Integer domainLimit,Integer provinceLimit,Integer cityLimit,Integer professionalLimit,Integer zyLimit) {
        Group group = (Group) ShiroKit.getSession().getAttribute("groupAdmin");
        if(group==null){
            return error("错误:未找到组织");
        }
        try{
            groupApprovalService.permissionSetting(group,permissionsType,isJoin,isIntroduce,costType,cost,sexLimit, industryLimit,domainLimit,provinceLimit,cityLimit,professionalLimit,zyLimit);
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
                "  tug.id AS id,\n" +
                "  tug.user_id AS userId,\n" +
                "  tui.mobile AS mobile\n" +
                "FROM\n" +
                "  tb_user_group tug \n" +
                "  LEFT JOIN tb_user_info tui \n" +
                "    ON tug.user_id = tui.user_id \n" +
                "WHERE 1=1 \n" +
                "AND tug.vip_type = 1 \n" +
                "AND tug.user_id NOT IN\n" +
                "  (SELECT \n" +
                "    user_id \n" +
                "  FROM\n" +
                "    tb_user_group \n" +
                "  WHERE vip_type = 2)" +
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
    public AjaxResult appointedSave(Integer id,Integer status) {
        try{
            groupApprovalService.appointedSave(id,status);
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
