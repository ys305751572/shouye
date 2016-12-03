package com.smallchill.web.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.common.vo.User;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.system.service.UserService;
import com.smallchill.web.model.Group;
import com.smallchill.web.model.GroupBank;
import com.smallchill.web.model.GroupExtend;
import com.smallchill.web.model.ProvinceCity;
import com.smallchill.web.service.GroupBankService;
import com.smallchill.web.service.GroupExtendService;
import com.smallchill.web.service.ProvinceCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Administrator on 2016/12/3.
 */
@RequestMapping(value = "/account")
@Controller
public class AccountSecurityController extends BaseController {

    private static String BASE_PATH = "/web/account/";
    private static String CODE = "account";

    @Autowired
    UserService userService;
    @Autowired
    GroupExtendService groupExtendService;

    @Autowired
    GroupBankService groupBankService;

    @Autowired
    ProvinceCityService provinceCityService;

    @RequestMapping(value = "/")
    public String index(ModelMap mm){
        Group group = (Group) ShiroKit.getSession().getAttribute("groupAdmin");
        GroupExtend groupExtend = groupExtendService.findFirstBy("group_id = #{groupId}",Record.create().set("groupId",group.getId()));
        GroupBank groupBank = groupBankService.findFirstBy("group_id = #{groupId}",Record.create().set("groupId",group.getId()));
        mm.put("groupBank", groupBank);
        mm.put("mobile", groupExtend.getArtificialPersonMobile());
        mm.put("code", CODE);
        return BASE_PATH + "account_list.html";
    }

    /**
     * 重置密码
     * @param mm
     * @return
     */
    @RequestMapping(value = "/resetPwd")
    public String resetPwd(ModelMap mm){
        mm.put("code", CODE);
        return BASE_PATH + "account_resetPwd.html";
    }


    @RequestMapping(value = "/setPwd")
    @ResponseBody
    public AjaxResult setPwd(String mobile,String pwd,String verificationCode){
        Group group = (Group) ShiroKit.getSession().getAttribute("groupAdmin");
        try{
            GroupExtend groupExtend = groupExtendService.findFirstBy("group_id = #{groupId}",Record.create().set("groupId",group.getId()));
            if(!Objects.equals(groupExtend.getArtificialPersonMobile(),mobile)){
                return error("手机号输入错误");
            }
            User user = userService.findFirstBy("account = #{account}", Record.create().set("account",mobile));
            if(user==null){
                return error("手机号不存在");
            }
            String salt = ShiroKit.getRandomSalt(5);
            String pwdMd5 = ShiroKit.md5(pwd, salt);
            user.setPassword(pwdMd5);
            user.setSalt(salt);
//            userService.update(user);

        }catch (RuntimeException e){
            e.printStackTrace();
            return error(UPDATE_FAIL_MSG);
        }
        return success(UPDATE_SUCCESS_MSG);

    }


    /**
     * 重置手机
     * @param mm
     * @return
     */
    @RequestMapping(value = "/resetMobile")
    public String resetMobile(ModelMap mm){
        mm.put("code", CODE);
        return BASE_PATH + "account_resetMobile.html";
    }


    @RequestMapping(value = "/setMobile")
    @ResponseBody
    public AjaxResult setMobile(String mobile,String code){
        Group group = (Group) ShiroKit.getSession().getAttribute("groupAdmin");
        String demoCode = "321";
        try{
            if(!Objects.equals(demoCode, code)){
                return error("验证码输入错误");
            }

            GroupExtend groupExtend = groupExtendService.findFirstBy("group_id = #{groupId}",Record.create().set("groupId",group.getId()));
            String account = groupExtend.getArtificialPersonMobile();
            User user = userService.findFirstBy("account = #{account}", Record.create().set("account",account));
            user.setAccount(mobile);
            groupExtend.setArtificialPersonMobile(mobile);

//            groupExtendService.update(groupExtend);
//            userService.update(user);

        }catch (RuntimeException e){
            e.printStackTrace();
            return error(UPDATE_FAIL_MSG);
        }
        return success(UPDATE_SUCCESS_MSG);

    }



    /**
     * 设置银行
     * @param mm
     * @return
     */
    @RequestMapping(value = "/resetBank")
    public String resetBank(ModelMap mm){
        List<Map<String, Object>> province = provinceCityService.province();
        List<Map<String, Object>> city = provinceCityService.city();
        mm.put("province", province);
        mm.put("city", city);
        mm.put("code", CODE);
        return BASE_PATH + "account_resetBank.html";
    }

    @RequestMapping(value = "/setBank")
    @ResponseBody
    public AjaxResult setBank(String bankUserName,String bankAccout,String bankName,Integer province,Integer city,String branchName){
        Group group = (Group) ShiroKit.getSession().getAttribute("groupAdmin");
        try{
            GroupBank groupBank = groupBankService.findFirstBy("group_id = #{groupId}",Record.create().set("groupId",group.getId()));
            groupBank.setBankUserName(bankUserName);
            groupBank.setBankAccout(bankAccout);
            groupBank.setBankName(bankName);
            groupBank.setProvince(province);
            groupBank.setCity(city);
            groupBank.setBranchName(branchName);

//            groupBankService.update(groupBank);
        }catch (RuntimeException e){
            e.printStackTrace();
            return error(UPDATE_FAIL_MSG);
        }
        return success(UPDATE_SUCCESS_MSG);

    }







    @RequestMapping(value = "/checkMobile")
    @ResponseBody
    public AjaxResult checkMobile(String mobile){
        Group group = (Group) ShiroKit.getSession().getAttribute("groupAdmin");
        try{
            GroupExtend groupExtend = groupExtendService.findFirstBy("group_id = #{groupId}",Record.create().set("groupId",group.getId()));
            if(!Objects.equals(groupExtend.getArtificialPersonMobile(),mobile)){
                return error("手机号输入错误");
            }
        }catch (RuntimeException e){
            e.printStackTrace();
            return error("错误");
        }
        return success("");

    }

    @RequestMapping(value = "/checkCode")
    @ResponseBody
    public AjaxResult checkCode(String code){
        Group group = (Group) ShiroKit.getSession().getAttribute("groupAdmin");
        String demoCode = "123";
        try{
            if(!demoCode.equals(code)){
                return error("验证码输入错误");
            }
        }catch (RuntimeException e){
            e.printStackTrace();
            return error("错误");
        }
        return success("");

    }



}
