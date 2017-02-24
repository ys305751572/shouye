package com.smallchill.web.controller;

import com.smallchill.web.model.Article;
import com.smallchill.web.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.JsonKit;


/**
 * Generated by Blade.
 * 2017-01-11 14:36:12
 */
@Controller
@RequestMapping("/article")
public class ArticleController extends BaseController {
    private static String CODE = "article";
    private static String PERFIX = "tb_article";
    private static String LIST_SOURCE = "Acticle.list";
    private static String BASE_PATH = "/web/article/";

    @Autowired
    ArticleService service;

    @RequestMapping(KEY_MAIN)
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "acticle.html";
    }

    @RequestMapping(KEY_ADD)
    public String add(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "article_add.html";
    }

    @RequestMapping(KEY_EDIT + "/{id}")
    public String edit(@PathVariable String id, ModelMap mm) {
        Article acticle = service.findById(id);
        mm.put("model", JsonKit.toJson(acticle));
        mm.put("id", id);
        mm.put("code", CODE);
        return BASE_PATH + "acticle_view.html";
    }

    @RequestMapping(KEY_VIEW + "/{id}")
    public String view(@PathVariable String id, ModelMap mm) {
        Article acticle = service.findById(id);
        mm.put("model", JsonKit.toJson(acticle));
        mm.put("id", id);
        mm.put("code", CODE);
        return BASE_PATH + "acticle_view.html";
    }

    @ResponseBody
    @RequestMapping(KEY_LIST)
    public Object list() {
        Object grid = paginate(LIST_SOURCE);
        return grid;
    }

    @ResponseBody
    @RequestMapping(KEY_SAVE)
    public AjaxResult save() {
        Article acticle = mapping(PERFIX, Article.class);
        boolean temp = service.save(acticle);
        if (temp) {
            return success(SAVE_SUCCESS_MSG);
        } else {
            return error(SAVE_FAIL_MSG);
        }
    }

    @ResponseBody
    @RequestMapping(KEY_UPDATE)
    public AjaxResult update() {
        Article acticle = mapping(PERFIX, Article.class);
        boolean temp = service.update(acticle);
        if (temp) {
            return success(UPDATE_SUCCESS_MSG);
        } else {
            return error(UPDATE_FAIL_MSG);
        }
    }

    @ResponseBody
    @RequestMapping(KEY_REMOVE)
    public AjaxResult remove(@RequestParam String ids) {
        int cnt = service.deleteByIds(ids);
        if (cnt > 0) {
            return success(DEL_SUCCESS_MSG);
        } else {
            return error(DEL_FAIL_MSG);
        }
    }
}