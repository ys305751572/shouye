package com.smallchill.web.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.CacheKit;
import com.smallchill.core.toolbox.kit.JsonKit;
import com.smallchill.system.meta.intercept.RoleIntercept;
import com.smallchill.system.model.Demo;
import com.smallchill.system.model.Role;
import com.smallchill.web.meta.intercept.GroupIntercept;
import com.smallchill.web.model.Group;
import com.smallchill.web.model.GroupBank;
import com.smallchill.web.model.GroupExtend;
import com.smallchill.web.model.vo.GroupVo;
import com.smallchill.web.service.GroupBankService;
import com.smallchill.web.service.GroupExtendService;
import com.smallchill.web.service.GroupService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 组织管理
 * Created by 史龙 on 2016/10/28.
 */
@Controller
@RequestMapping("/group")
public class GroupController extends BaseController {

    private static String LIST_SOURCE = "Group.list";
    private static String BASE_PATH = "/web/group/";
    private static String CODE = "group";
    private static String PERFIX = "tb_group";
    private static String EXTEND = "tb_group_extend";
    private static String BANK = "tb_group_bank";

    @Autowired
    GroupService groupService;
    @Autowired
    GroupBankService groupBankService;
    @Autowired
    GroupExtendService groupExtendService;

    @RequestMapping("/")
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "group.html";
    }

    @ResponseBody
    @RequestMapping(KEY_LIST)
    public Object list() {
        return paginate(LIST_SOURCE, new GroupIntercept());
    }

    @RequestMapping(KEY_ADD)
    public String add(ModelMap mm) throws JSONException {
        String codeImage = "[{name:\"图片\",index:\"attachUrl\", type:\"imgupload\",newline:true,length:8,returnType:\"id\"}]";
        String table = "tb_group_extend";
        JSONArray json = new JSONArray(codeImage);
        JSONObject resultJson = json.optJSONObject(0);
        mm.put("codeImage", resultJson);
        mm.put("table", table);
        mm.put("code", CODE);
        return BASE_PATH + "group_add.html";
    }

    @ResponseBody
    @RequestMapping(KEY_SAVE)
    public AjaxResult save(GroupVo groupVo) {
//        GroupBank groupBank =mapping(BANK, GroupBank.class);
//        GroupExtend groupExtend =mapping(EXTEND, GroupExtend.class);
        try{
            Group group = mapping(PERFIX, Group.class);
            groupVo.setType(group.getType());
            groupService.saveGroup(groupVo);
        }catch (RuntimeException e){
            e.printStackTrace();
            return error(SAVE_FAIL_MSG);

        }
            return success(SAVE_SUCCESS_MSG);
    }
}
