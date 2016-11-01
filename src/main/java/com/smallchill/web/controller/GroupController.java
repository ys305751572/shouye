package com.smallchill.web.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.JsonKit;
import com.smallchill.web.meta.intercept.GroupIntercept;
import com.smallchill.web.model.vo.GroupVo;
import com.smallchill.web.service.GroupBankService;
import com.smallchill.web.service.GroupExtendService;
import com.smallchill.web.service.GroupService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.lang3.StringUtils;

/**
 * 组织管理
 * Created by 史龙 on 2016/10/28.
 */
@Controller
@RequestMapping("/group")
public class GroupController extends BaseController {

    private static String LIST_SOURCE = "Group.list";
    private static String AUDIT_SOURCE = "Group.audit";
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

    @RequestMapping(value = "/group_index")
    public String groupIndex(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "group_list.html";
    }

 /*   @RequestMapping(value = "/group_audit")
    public String groupAudit(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "group_audit.html";
    }

    @ResponseBody
    @RequestMapping(value = "/group_audit_list")
    public Object auditList() {
        return paginate(AUDIT_SOURCE, new GroupIntercept());
    }*/

    @ResponseBody
    @RequestMapping(KEY_LIST)
    public Object list() {
        return paginate(LIST_SOURCE, new GroupIntercept());
    }


    @RequestMapping(KEY_ADD)
    public String add(ModelMap mm) throws JSONException {
        mm.put("code", CODE);
        return BASE_PATH + "group_add.html";
    }

    @ResponseBody
    @RequestMapping(KEY_SAVE)
    public AjaxResult save(GroupVo groupVo,
                           String name,
                           String code,
                           String license,
                           String artificialPersonName,
                           String artificialPersonIdcard,
                           String artificialPersonMobile,
                           String bankUserName,
                           String bankAccout,
                           Integer bankId,
                           String bankName,
                           Integer banckProvince,
                           Integer bankCity,
                           String branchName,
                           String password,
                           Integer type,
                           Integer province,
                           Integer city,
                           String title1,
                           Integer isOpen1,
                           String title2,
                           Integer isOpen2,
                           String title3,
                           Integer isOpen3,
                           String content1,
                           String content2,
                           String content3,
                           String targat) {
//        GroupBank groupBank =mapping(BANK, GroupBank.class);
//        GroupExtend groupExtend =mapping(EXTEND, GroupExtend.class);
        try{
            groupVo.setName(name);
            groupVo.setCode(code);
            groupVo.setLicense(license);
            groupVo.setArtificialPersonName(artificialPersonName);
            groupVo.setArtificialPersonIdcard(artificialPersonIdcard);
            groupVo.setArtificialPersonMobile(artificialPersonMobile);
            groupVo.setBankUserName(bankUserName);
            groupVo.setBankAccout(bankAccout);
            groupVo.setBankId(bankId);
            groupVo.setBankName(bankName);
            groupVo.setBanckProvince(banckProvince);
            groupVo.setBankCity(bankCity);
            groupVo.setBranchName(branchName);
            groupVo.setPassword(password);
            groupVo.setType(type);
            groupVo.setProvince(province);
            groupVo.setCity(city);
            groupVo.setTitle1(title1);
            groupVo.setIsOpen1(isOpen1);
            groupVo.setTitle2(title2);
            groupVo.setIsOpen2(isOpen2);
            groupVo.setTitle3(title3);
            groupVo.setIsOpen3(isOpen3);

            groupVo.setContent1(content1);
            groupVo.setContent2(content2);
            groupVo.setContent3(content3);
            if(StringUtils.isNotBlank(targat)){
                String[] targats = JsonKit.parse(targat,String[].class);
                StringBuffer _t = new StringBuffer();
                for(String t : targats){
                    _t.append(t).append("|");
                }
                String _targat = _t.toString().substring(0,_t.length()-1);
                groupVo.setTarget(_targat);
            }
            groupService.saveGroup(groupVo);
        }catch (RuntimeException e){
            e.printStackTrace();
            return error(SAVE_FAIL_MSG);

        }
            return success(SAVE_SUCCESS_MSG);
    }
}
