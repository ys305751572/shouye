package com.smallchill.web.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.grid.JqGrid;
import com.smallchill.core.toolbox.kit.JsonKit;
import com.smallchill.web.meta.intercept.GroupIntercept;
import com.smallchill.web.model.Group;
import com.smallchill.web.model.vo.GroupVo;
import com.smallchill.web.service.GroupBankService;
import com.smallchill.web.service.GroupExtendService;
import com.smallchill.web.service.GroupService;
import org.beetl.sql.core.kit.CaseInsensitiveHashMap;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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

    @RequestMapping(value = "/group_index")
    public String groupIndex(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "group_list.html";
    }

    @ResponseBody
    @RequestMapping(KEY_LIST)
    public Object list(HttpServletRequest request) {
        JqGrid object = (JqGrid) paginate(LIST_SOURCE, new GroupIntercept());
        List<CaseInsensitiveHashMap> groupList2 = object.getRows();

        //查询结果所有ID
        List<Integer> groupIds = new ArrayList<>();
        for (CaseInsensitiveHashMap map : groupList2) {
            Integer id = (Integer) map.get("ID");
            groupIds.add(id);
        }
        request.getSession().setAttribute("groupIds",groupIds);

        //查询结果的所有会员数

        //查询结果的所有干事数


        return object;
    }


    @RequestMapping(KEY_ADD)
    public String add(ModelMap mm) throws JSONException {
        mm.put("code", CODE);
        return BASE_PATH + "group_add.html";
    }

    //消息发送页面
    @RequestMapping("/message" + "/{id}")
    public String groupMessages(ModelMap mm,@PathVariable String id) {
        Group group = groupService.findById(id);
        mm.put("group", group);
        mm.put("code", CODE);
        return BASE_PATH + "group_message.html";
    }

    //消息发送
    @ResponseBody
    @RequestMapping("/send_message")
    public AjaxResult sendMessage(HttpServletRequest request,String groupId,Integer send,String sendTime,String title,String content) {

        boolean index = groupService.sendMessage(request,groupId,send,sendTime,title,content);
        if (index) {
            return success(SEND_SUCCESS_MSG);
        } else {
            return error(SEND_FAIL_MSG);
        }
    }

    //消息发送
    @ResponseBody
    @RequestMapping("/audit_status")
    public AjaxResult auditStatus(Integer id,Integer status) {
        try{
            groupService.audit(id,status);
        }catch (RuntimeException e){
            e.printStackTrace();
            return error("失败");

        }
        return success("成功");

    }


    //跳转该组织的会员页面(暂无)
    @RequestMapping("/members")
    public String groupMembers(ModelMap mm) {
        mm.put("code", CODE);
        return null;
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
