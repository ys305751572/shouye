package com.smallchill.web.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.JsonKit;
import com.smallchill.web.meta.intercept.GroupIntercept;
import com.smallchill.web.model.Group;
import com.smallchill.web.model.GroupExtend;
import com.smallchill.web.model.vo.GroupVo;
import com.smallchill.web.service.GroupBankService;
import com.smallchill.web.service.GroupExtendService;
import com.smallchill.web.service.GroupService;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 组织管理
 * Created by 史龙 on 2016/10/28.
 */
@Controller
@RequestMapping("/groupAudit")
public class GroupAuditController extends BaseController {

    private static String LIST_SOURCE = "Group.audit";
    private static String BASE_PATH = "/web/group/";
    private static String CODE = "groupAudit";
    private static String PERFIX = "tb_group";

    @Autowired
    GroupService groupService;
    @Autowired
    GroupBankService groupBankService;
    @Autowired
    GroupExtendService groupExtendService;


    @RequestMapping(value = "/group_audit")
    public String groupAudit(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "group_audit.html";
    }

    @ResponseBody
    @RequestMapping(KEY_LIST)
    public Object list() {
        return paginate(LIST_SOURCE);
    }

    //单元格内 修改备注
    @ResponseBody
    @RequestMapping("/updateNote")
    public AjaxResult updateNote(Integer id,String content) {
        try{
            groupService.updateNote(id,content);
        }catch (RuntimeException e){
            e.printStackTrace();
            return error(UPDATE_FAIL_MSG);
        }
        return success(UPDATE_FAIL_MSG);

    }



}
