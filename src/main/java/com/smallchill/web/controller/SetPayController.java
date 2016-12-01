package com.smallchill.web.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.web.model.FriendExpand;
import com.smallchill.web.model.Poundage;
import com.smallchill.web.service.FriendExpandService;
import com.smallchill.web.service.PoundageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on
 * 2016/12/1.
 */
@RequestMapping(value = "/setPay")
@Controller
public class SetPayController extends BaseController {

    private static String BASE_PATH = "/web/setpay/";
    private static String CODE = "setPay";

    @Autowired
    PoundageService poundageService;
    @Autowired
    FriendExpandService friendExpandService;

    @RequestMapping(value = "/")
    public String index(ModelMap mm){
        List<Poundage> poundages = poundageService.findAll();
        FriendExpand interested = friendExpandService.findFirstBy("type = #{type}", Record.create().set("type",1));
        FriendExpand familiar = friendExpandService.findFirstBy("type = #{type}", Record.create().set("type",2));
        mm.put("poundage",poundages != null ? poundages.get(0) : new Poundage());
        mm.put("interested",interested != null ? interested : new FriendExpand());
        mm.put("familiar",familiar != null ? familiar : new FriendExpand());
        mm.put("code",CODE);
        return BASE_PATH+"setPay_list.html";
    }

    @RequestMapping(value = "/friendExpandSave")
    @ResponseBody
    public AjaxResult friendExpandSave(Integer type,Integer num,Double amount){
        FriendExpand friendExpand = friendExpandService.findFirstBy("type = #{type}", Record.create().set("type",type));
        try{
            friendExpand.setNum(num);
            friendExpand.setAmount(amount);
            friendExpand.setUpdateTime(System.currentTimeMillis());
            friendExpandService.update(friendExpand);
        }catch (RuntimeException e){
            e.printStackTrace();
            return error("设置失败");
        }
        return success("设置成功");
    }

    @RequestMapping(value = "/poundageSave")
    @ResponseBody
    public AjaxResult poundageSave(Double rate,Integer num,Double amount){
        try{
            List<Poundage> poundages = poundageService.findAll();
            if(!poundages.isEmpty()){
                Poundage poundage = poundages.get(0);
                poundage.setRate(rate);
                poundage.setNum(num);
                poundage.setAmount(amount);
                poundage.setUpdateTime(System.currentTimeMillis());
                poundageService.update(poundage);
            }else {
                Poundage poundage = new Poundage();
                poundage.setRate(rate);
                poundage.setNum(num);
                poundage.setAmount(amount);
                poundage.setUpdateTime(System.currentTimeMillis());
                poundageService.save(poundage);
            }
        }catch (RuntimeException e){
            e.printStackTrace();
            return error("设置失败");
        }
        return success("设置成功");
    }

}
