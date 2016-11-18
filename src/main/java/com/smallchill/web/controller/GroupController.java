package com.smallchill.web.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.common.vo.User;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.grid.JqGrid;
import com.smallchill.core.toolbox.kit.JsonKit;
import com.smallchill.web.meta.intercept.GroupIntercept;
import com.smallchill.web.model.Group;
import com.smallchill.web.model.GroupExtend;
import com.smallchill.web.model.GroupLoad;
import com.smallchill.web.model.vo.GroupVo;
import com.smallchill.web.service.GroupBankService;
import com.smallchill.web.service.GroupExtendService;
import com.smallchill.web.service.GroupLoadService;
import com.smallchill.web.service.GroupService;
import org.beetl.sql.core.kit.CaseInsensitiveHashMap;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
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
    @Autowired
    GroupLoadService groupLoadService;

//    @RequestMapping("/")
//    public String index(ModelMap mm) {
//        mm.put("code", CODE);
//        return BASE_PATH + "group.html";
//    }

    @RequestMapping(value = "/")
    public String groupIndex(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "group_list.html";
    }

    @ResponseBody
    @RequestMapping(KEY_LIST)
    public Object list(HttpServletRequest request) {
        JqGrid object = (JqGrid) paginate(LIST_SOURCE, new GroupIntercept());

        List<CaseInsensitiveHashMap> groupList = object.getRows();

        //查询结果所有ID
        List<Integer> ids = new ArrayList<>();
        for (CaseInsensitiveHashMap map : groupList) {
            Integer id = (Integer) map.get("ID");
            ids.add(id);
        }
        request.getSession().setAttribute("groupIds",ids);
        request.getSession().setAttribute("groupNum",groupList.size());

        //查询结果的所有会员数

        //查询结果的所有干事数

        return object;
    }

    @RequestMapping(KEY_ADD)
    public String add(ModelMap mm) throws JSONException {
        mm.put("code", CODE);
        return BASE_PATH + "group_add.html";
    }

    //消息发送页面(单发)
    @RequestMapping("/message" + "/{id}")
    public String groupMessages(ModelMap mm,@PathVariable String id) {
        Group group = groupService.findById(id);
        mm.put("group", group);
        mm.put("groupNum", 0);
        mm.put("code", CODE);
        return BASE_PATH + "group_message.html";
    }

    //消息发送页面(群发)
    @RequestMapping("/message")
    public String _groupMessages(ModelMap mm,HttpServletRequest request) {
        Group group = new Group();
        mm.put("groupNum", request.getSession().getAttribute("groupNum"));
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

    //审核状态
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
    @RequestMapping("/members"  + "/{id}")
    public String groupMembers(ModelMap mm,@PathVariable String id) {
        mm.put("code", CODE);
        return null;
    }


    //跳转改变组织状态页面
    @RequestMapping("/banned")
    public String banned(ModelMap mm,Integer id) {
        mm.put("id", id);
        return BASE_PATH +"group_banned.html";
    }


    //改变组织状态(封/解)
    @ResponseBody
    @RequestMapping(value = "/setBanned")
    public AjaxResult setBanned(Integer id, Integer bannedTime ,String content){
        try{
            groupService.banned(id,bannedTime,content);
        }catch (RuntimeException e){
            return error(SAVE_FAIL_MSG);
        }
        return success(SAVE_SUCCESS_MSG);
    }

    //跳转默认加载组织
    @RequestMapping(value = "/load")
    public String load(ModelMap mm) {
        mm.put("code", CODE);
        List<Integer> day = new ArrayList<>();
        for(int i=1;i<=30;i++){
            day.add(i);
        }
        mm.put("day", day);
        List<GroupExtend> groupExtends = groupExtendService.findAll();
        mm.put("groupExtend", groupExtends);
        return BASE_PATH +"group_load.html";
    }

    @ResponseBody
    @RequestMapping(value = "/loadList")
    public Object loadList() {
        Object object = paginate("Group.load");
        return object;
    }

    @ResponseBody
    @RequestMapping(value = "/loadSave")
    public AjaxResult loadSave(Integer groupId,Integer id){
        try{
            Integer index = groupLoadService.loadSave(groupId,id);
            if(index==2){
                return error("该组织已存在");
            }else{
                return success("设置成功");
            }
        }catch (RuntimeException e){
            e.printStackTrace();
            return error("设置错误");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/loadDel")
    public AjaxResult loadDel(Integer id){
        try{
            if(id==null){
                return error(DEL_SUCCESS_MSG);
            }
            groupLoadService.deleteBy("id = #{id}", Record.create().set("id", id));
        }catch (RuntimeException e){
            e.printStackTrace();
            return error(DEL_SUCCESS_MSG);
        }
        return success(DEL_FAIL_MSG);
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
            Integer adminId = (Integer) ShiroKit.getUser().getId();
            groupVo.setCreateAdminId(adminId);
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
                String _targat = "";
                for(String t : targats){
                    _t.append(t).append("|");
                }
                if(StringUtils.isNotBlank(_t.toString())){
                    _targat = _t.toString().substring(0,_t.length()-1);
                }
                groupVo.setTarget(_targat);

            }
            //新增一个组织管理员
            User user = new User();
            String pwd = groupVo.getPassword();
            String salt = ShiroKit.getRandomSalt(5);
            String pwdMd5 = ShiroKit.md5(pwd, salt);
            user.setPassword(pwdMd5);
            user.setSalt(salt);
            user.setAccount(groupVo.getArtificialPersonMobile());
            //设置角色为组织管理员
            user.setDeptid(1);
            //设置管理员为已审核
            user.setStatus(1);
            user.setCreatetime(new Date());
            Blade.create(User.class).save(user);
            groupService.saveGroup(groupVo);
        }catch (RuntimeException e){
            e.printStackTrace();
            return error(SAVE_FAIL_MSG);
        }

        return success(SAVE_SUCCESS_MSG);
    }
}
