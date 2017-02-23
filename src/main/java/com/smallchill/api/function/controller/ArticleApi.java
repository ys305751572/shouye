package com.smallchill.api.function.controller;

import com.smallchill.api.common.kit.ExcludeParams;
import com.smallchill.api.common.model.Result;
import com.smallchill.api.function.meta.intercept.ApplyUsersIntercept;
import com.smallchill.api.function.meta.intercept.ArticleIntercept;
import com.smallchill.api.function.meta.intercept.ArticlePageIntercept;
import com.smallchill.api.function.meta.other.ArticleConvert;
import com.smallchill.api.function.meta.validate.UserIdValidate;
import com.smallchill.api.function.modal.vo.ArticleVo;
import com.smallchill.api.function.service.ShieldingService;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.grid.JqGrid;
import com.smallchill.core.toolbox.kit.CollectionKit;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PostMapping(value = "/list")
    @ResponseBody
    @Before(UserIdValidate.class)
    public String listByUserId(Integer userId) {
        JqGrid page;
        List<MagazineInfo> magazineInfos = magazineInfoService.simpleListByUserId(userId);
        List<Record> dailys = dailyService.simpleListByUserId(userId);

        StringBuilder maganzineIds = new StringBuilder();
        if (CollectionKit.isNotEmpty(magazineInfos)) {
            for (MagazineInfo magazineInfo : magazineInfos) {
                maganzineIds.append(magazineInfo.getId()).append(",");
            }
        }

        StringBuffer dailyIds = new StringBuffer();
        if (CollectionKit.isNotEmpty(dailys)) {
            for (Record record : dailys) {
                dailyIds.append(record.getInt("id")).append(",");
            }
        }

        try {
            page = apiPaginate("Acticle.listByUserId", new ArticlePageIntercept().addRecord(Record.create()
            .set("userId", getParameter("userId"))
                            .set("maganzineIds", StringUtils.isNotBlank(maganzineIds) ? maganzineIds.substring(0, maganzineIds.length() - 1) : "")
                            .set("dailyIds", StringUtils.isNotBlank(dailyIds) ? dailyIds.substring(0, dailyIds.length() - 1) : "")),
                    ExcludeParams.create().set("userId")
                   );
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
        return success(page);
    }

    /**
     * 查询发布对象
     *
     * @return result
     */
    @PostMapping(value = "publishObject")
    @ResponseBody
    public String findPublishObject(Integer userId) {
        List<MagazineInfo> magazineInfos = magazineInfoService.simpleListByUserId(userId);
        List<Record> dailys = dailyService.simpleListByUserId(userId);

        List<Integer> list1 = articleService.friend(userId);
        List<Integer> list2 = articleService.listIntereste(userId);
        List<Integer> list3 = articleService.listInterested(userId);

        Record record = Record.create().set("magazineInfos", magazineInfos).set("dailys", dailys);
        record.set("friendCount", CollectionKit.isNotEmpty(list1) ? list1.size() : 0)
                .set("interestCount", CollectionKit.isNotEmpty(list2) ? list2.size() : 0)
                .set("interestedCount", CollectionKit.isNotEmpty(list3) ? list3.size() : 0);
        return success(record, "publishObject");
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
    public String create(Article article, Integer userId, String obj, MultipartRequest request) {
        try {
            articleService.create(article, userId, request, obj);
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
        return success();
    }

    /**
     * 查询文章详情
     *
     * @param articleId 文章ID
     * @return result
     */
    @PostMapping(value = "/detail")
    @ResponseBody
    public String detail(Integer articleId, Integer userId, Integer authorId, Integer authorType) {
        ArticleVo articleVo;
        try {
            articleVo = articleService.detail(articleId, userId, authorId, authorType);
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
        return success(articleVo);
    }

    /**
     * 文章感兴趣用户列表
     *
     * @param articleId 文章ID
     * @param userId    用户ID
     * @return result
     */
    @PostMapping(value = "/detail/userlist")
    @ResponseBody
    public String detailInterestUserList(Integer articleId, Integer userId) {
        Article article = articleService.findById(articleId);
        if (article.getFromId() == userId && article.getFromType() == 1) {
            JqGrid page = apiPaginate("Acticle.interestUserListByArticleId", new ApplyUsersIntercept().addRecord(
                    Record.create().set("activityId", articleId).set("userId", userId)
            ), ExcludeParams.create().set("activityId").set("userId"));
            return success(page);
        }
        return success();
    }

    /**
     * 删除文章
     *
     * @param articleId 文章ID
     * @param userId    当前用户ID
     * @return result
     */
    @PostMapping(value = "/delete")
    @ResponseBody
    @Before(UserIdValidate.class)
    public String delete(Integer articleId, Integer userId) {
        try {
            articleService.deleteById(articleId, userId);
        } catch (Exception e) {
            e.printStackTrace();
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
    public String share(Integer articleId, Integer userId, String toUserIds) {
        try {
            articleService.share(articleId, userId, toUserIds);
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
    @PostMapping(value = "/list/publish")
    @ResponseBody
    public String publishListByUserId(Integer userId) {
        List<ArticleVo> list;
        try {
            list = articleService.findByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
        return success(list);
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
    @PostMapping(value = "/interest")
    @ResponseBody
    public String interest(Integer id, Integer articleId, Integer userId) {
        try {
            articleShowService.interest(id, articleId, userId);
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
        return success();
    }

    /**
     * 文章不感兴趣
     *
     * @param id       数据主键ID
     * @param position 按钮位子 1:机遇列表 2:感兴趣列表
     * @return result
     */
    @PostMapping(value = "/uninterest")
    @ResponseBody
    public String uninterest(Integer id, Integer articleId, Integer position, Integer userId) {
        try {
            articleShowService.uninterest(id, articleId, position, userId);
        } catch (Exception e) {
            return fail();
        }
        return success();
    }

    /**
     * 我感兴趣文章列表
     *
     * @param userId 当前用户ID
     * @return result
     */
    @PostMapping(value = "/list/interest")
    @ResponseBody
    @Before(UserIdValidate.class)
    public String listInterest(Integer userId) {
        JqGrid page = apiPaginate("Acticle.interestListByUserId", new ArticleIntercept()
                        .addRecord(Record.create().set("userId", this.getParameter("userId"))
                                .set("isInterest", 2)),
                ExcludeParams.create().set("userId"));
        return success(page);
    }

    @PostMapping(value = "/list/uninterest")
    @ResponseBody
    @Before(UserIdValidate.class)
    public String listUnInterest(Integer userId) {
        JqGrid page = apiPaginate("Acticle.interestListByUserId", new ArticleIntercept()
                        .addRecord(Record.create().set("userId", this.getParameter("userId"))
                                .set("isInterest", 3)),
                ExcludeParams.create().set("userId"));
        return success(page);
    }
}
