package com.smallchill.api.function.controller;

import com.smallchill.api.function.meta.other.ArticleConvert;
import com.smallchill.api.function.meta.validate.UserIdValidate;
import com.smallchill.api.function.modal.vo.ArticleVo;
import com.smallchill.api.function.service.ShieldingService;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.toolbox.Record;
import com.smallchill.web.model.Article;
import com.smallchill.web.model.MagazineInfo;
import com.smallchill.web.service.ArticleService;
import com.smallchill.web.service.ArticleShowService;
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
    @Autowired
    private ArticleShowService articleShowService;
    @Autowired
    ShieldingService shieldingService;

    /**
     * 查询发布对象
     *
     * @return result
     */
    @PostMapping(value = "publishObject")
    @ResponseBody
    public String findPublishObject(Integer userId) {
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
     * 查询文章详情
     *
     * @param id 文章ID
     * @return result
     */
    @PostMapping(value = "/detail")
    @ResponseBody
    public String detail(Integer id) {


        return null;
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
            articleService.contributeToMyFriend(articleId, toUserIds, true);
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
            articleService.contributeToMyFriend(articleId, toUserId, true);
        } catch (Exception e) {
            return fail();
        }
        return success();
    }

    /**
     * 投稿
     *
     * @param obj 选择日报ID|选择杂志ID，多个ID之间用","号分割
     * @return result
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

    /**
     * 文章感兴趣
     *
     * @param id 数据主键ID
     * @return result
     */
    @PostMapping(value = "/interest/{id}")
    @ResponseBody
    public String interest(Integer id, Integer articleId) {
        try {
            articleShowService.interest(id,articleId);
        } catch (Exception e) {
            return fail();
        }
        return success();
    }

    /**
     * 文章不感兴趣
     *
     * @param id 数据主键ID
     * @param position 按钮位子 1:机遇列表 2:感兴趣列表
     * @return result
     */
    @PostMapping(value = "/uninterest/{id}")
    @ResponseBody
    public String uninterest(Integer id,Integer articleId, Integer position) {
        try {
            articleShowService.uninterest(id,articleId, position);
        } catch (Exception e) {
            return fail();
        }
        return success();
    }

    /**
     * 屏蔽发布者
     *
     * @param id 数据主键ID
     * @return result
     */
    @PostMapping(value = "/shielding/{id}")
    @ResponseBody
    public String shielding(Integer id, Integer userId) {
        try {
            shieldingService.shieldingByArticleShowId(id, userId);
        } catch (Exception e) {
            return fail();
        }
        return success();
    }

    /**
     * 删除文章
     *
     * @param id     文章ID
     * @param userId 当前用户ID
     * @return result
     */
    public String delete(Integer id, Integer userId) {


        return null;
    }
}
