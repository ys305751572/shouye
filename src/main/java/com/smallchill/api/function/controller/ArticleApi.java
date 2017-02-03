package com.smallchill.api.function.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.toolbox.Record;
import com.smallchill.web.model.Article;
import com.smallchill.web.model.MagazineInfo;
import com.smallchill.web.service.ArticleService;
import com.smallchill.web.service.DailyService;
import com.smallchill.web.service.MagazineInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.util.List;

/**
 * 文章API
 * Created by yesong on 2017/1/12 0012.
 */
@RequestMapping(value = "/api/article")
@Controller
public class ArticleApi extends BaseController {

    @Autowired
    private MagazineInfoService magazineInfoService;
    @Autowired
    private DailyService dailyService;
    @Autowired
    private ArticleService articleService;

    /**
     * 查询发布对象
     *
     * @return result
     */
    @PostMapping(value = "publishObject")
    @ResponseBody
    public String findPublishObject() {
        List<MagazineInfo> magazineInfos = magazineInfoService.findAll2();
        List<Record> dailys = dailyService.findAllDailys();
        return success(Record.create().set("magazineInfos", magazineInfos).set("dailys", dailys), "publishObject");
    }

    /**
     * 创建文章
     *
     * @param article 文章内容
     * @param obj     选择对象
     * @return result
     */
    @RequestMapping(value = "/create")
    @ResponseBody
    public String create(Article article, String obj) {
        try {
            articleService.create(article, null, obj, false);
        } catch (Exception e) {
            return fail();
        }
        return success();
    }
}
