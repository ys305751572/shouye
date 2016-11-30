package com.smallchill.web.controller;

import com.smallchill.common.base.BaseController;
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
        return BASE_PATH+"HotKeyword_list.html";
    }

    @ResponseBody
    @RequestMapping(KEY_LIST)
    public Object list() {
        Object object = paginate(LIST_SOURCE);
        return object;
    }

    @RequestMapping(KEY_UPDATE)
    public String update(ModelMap mm){
        return BASE_PATH+"";
    }

}
