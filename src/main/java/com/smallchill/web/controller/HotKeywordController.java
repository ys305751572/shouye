package com.smallchill.web.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.web.model.HotKeyword;
import com.smallchill.web.service.HotKeywordService;
import com.smallchill.web.service.SearchKeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by Administrator
 * on 2016/11/30.
 */
@RequestMapping(value = "/hotKeyword")
@Controller
public class HotKeywordController extends BaseController {
    private static String LIST_SOURCE = "HotKeyword.list";
    private static String BASE_PATH = "/web/hotkeyword/";
    private static String CODE = "hotKeyword";

    @Autowired
    HotKeywordService hotKeywordService;
    @Autowired
    SearchKeywordService searchKeywordService;

    @RequestMapping(value = "/")
    public String index(ModelMap mm){
        mm.put("code", CODE);
        return BASE_PATH+"hotKeyword_list.html";
    }

    @ResponseBody
    @RequestMapping(KEY_LIST)
    public Object list() {
        Object object = paginate(LIST_SOURCE);
        return object;
    }

    @RequestMapping(KEY_UPDATE)
    public String update(ModelMap mm,Integer id){
        HotKeyword hotKeyword = hotKeywordService.findById(id);
        mm.put("hotKeyword", hotKeyword);
        mm.put("code", CODE);
        return BASE_PATH+"hotKeyword_update.html";
    }

    @RequestMapping(KEY_SAVE)
    @ResponseBody
    public AjaxResult save(Integer id,String name){
        HotKeyword hotKeyword = hotKeywordService.findById(id);
        try{
            hotKeyword.setName(name);
            hotKeyword.setType(1);
            hotKeywordService.update(hotKeyword);
        }catch (RuntimeException e){
            e.printStackTrace();
            return error("设置失败");
        }
        return success("设置成功");
    }

    @RequestMapping(value = "/release")
    public String type(ModelMap mm){
        mm.put("code", CODE);
        return BASE_PATH+"hotKeyword_type.html";
    }

    /**
     * 使用哪一种关键字表
     * 1.自己设置的关键字
     * 2.前十搜索的关键字
     */
    @RequestMapping(value = "/setRelease")
    @ResponseBody
    public AjaxResult keywordType(Integer type){
        try{

        }catch (RuntimeException e){
            e.printStackTrace();
            return error("设置失败");
        }
        return success("设置成功");
    }



}
