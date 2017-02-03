package com.smallchill.api.function.controller;

import com.smallchill.api.function.meta.other.ArticleConvert;
import com.smallchill.api.function.meta.validate.UserIdValidate;
import com.smallchill.api.function.modal.vo.ArticleVo;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.toolbox.Record;
import com.smallchill.web.model.Article;
import com.smallchill.web.model.MagazineInfo;
import com.smallchill.web.service.ArticleService;
import com.smallchill.web.service.DailyService;
import com.smallchill.web.service.MagazineInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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



    /**
     * 分享文章至手页好友
     *
     * @param articleId 文章ID
     * @param toUserIds 分享用户ID
     * @return result
     */
    @PostMapping(value = "/share")
    @ResponseBody
    @Before(UserIdValidate.class)
    public String share(Integer articleId, String toUserIds) {
        try {
            articleService.contributeToMyFriend(articleId, toUserIds);
        } catch (Exception e) {
            return fail();
        }
        return success();
    }

    /**
     * 查询用户发布列表
     *
     * @param userId 用户ID
     * @return result
     */
    @PostMapping(value = "/list/{userId}")
    @ResponseBody
    public String listByUserId(Integer userId) {
        List<ArticleVo> list;
        try {
            list = ArticleConvert.toArticleVos(articleService.findByUserId(userId));
        } catch (Exception e) {
            return fail();
        }
        return success(list);
    }

    /**
     * 转发
     *
     * @param toUserId  转发用户ID
     * @param articleId 文章ID
     * @return result
     */
    @PostMapping(value = "/forward")
    @ResponseBody
    public String forward(String toUserId, Integer articleId) {
        try {
            articleService.contributeToMyFriend(articleId, toUserId);
        } catch (Exception e) {
            return fail();
        }
        return success();
    }

    /**
     * 投稿
     *
     * @param obj 选择日报ID|选择杂志ID，多个ID之间用","号分割
     * @return
     */
    @PostMapping(value = "/contribute")
    @ResponseBody
    public String contribute(Integer articleId, String obj) {
        String[] objs = obj.split("\\|");
        try {
            if (StringUtils.isNotBlank(objs[1])) {
                // 投稿到杂志
                articleService.contributeToMagazine(articleId, objs[1]);
            }
            if (StringUtils.isNotBlank(objs[0])) {
                // 投稿到日报
                articleService.contributeToDaily(articleId, objs[0]);
            }
        } catch (Exception e) {
            return fail();
        }
        return success();
    }
}
