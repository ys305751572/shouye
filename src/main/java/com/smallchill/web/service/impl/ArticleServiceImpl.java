package com.smallchill.web.service.impl;

import com.smallchill.api.function.meta.consts.StatusConst;
import com.smallchill.api.function.service.impl.ShouPageServiceImpl;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.kit.DateTimeKit;
import com.smallchill.web.model.Article;
import com.smallchill.web.model.ArticleShow;
import com.smallchill.web.model.Daily;
import com.smallchill.web.model.Maganize;
import com.smallchill.web.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.smallchill.core.base.service.BaseService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartRequest;

import java.util.ArrayList;
import java.util.List;


/**
 * 文章service
 * Generated by yesong.
 * 2017-01-11 14:36:12
 */
@Service
public class ArticleServiceImpl extends BaseService<Article> implements ArticleService, StatusConst {

    @Autowired
    private ArticleShowService articleShowService;
    @Autowired
    private MaganizeService maganizeService;
    @Autowired
    private DailyService dailyService;

    @Transactional
    @Override
    public void create(Article article, MultipartRequest multipartRequest, String obj, boolean isUserId) {
        // 创建文章
        Integer lastActicleId = createActicle(article, multipartRequest);
        String[] objs = obj.split("\\|");
        if (StringUtils.isNotBlank(objs[0])) {
            // 分享到关系用户
            if (isUserId) {
                contributeToMyFriend(lastActicleId, objs[0]);
            } else {
                String userIds = findUserIds(article.getFromId(), obj);
                contributeToMyFriend(lastActicleId, userIds);
            }
        }
        if (StringUtils.isNotBlank(objs[2])) {
            // 投稿到杂志
            contributeToMagazine(lastActicleId, objs[2]);
        }
        if (StringUtils.isNotBlank(objs[1])) {
            // 投稿到日报
            contributeToDaily(lastActicleId, objs[1]);
        }
    }

    private String findUserIds(Integer userId, String obj) {
        // 1:朋友 2:对我感兴趣 3:我感兴趣的人
        List<Integer> list = new ArrayList<>();
        if (obj.contains("1")) {
            // 查询朋友
            List<Integer> list1 = friend(userId);
            list.addAll(list1);
        }
        if (obj.contains("2")) {
            // 查询对我感兴趣的人
            List<Integer> list2 = listInterested(userId);
            list.addAll(list2);
        }
        if (obj.contains("3")) {
            // 查询我感兴趣的人
            List<Integer> list3 = listIntereste(userId);
            list.addAll(list3);
        }
        StringBuilder buffer = new StringBuilder();
        for (Integer _userId : list) {
            buffer.append(_userId).append(",");

        }
        return buffer.substring(0, buffer.length() - 1);
    }

    private List<Integer> friend(Integer userId) {
        String sql = Blade.dao().getScript("UserFriend.list").getSql();
        StringBuilder where = new StringBuilder("");
        Record _r = Record.create().set("userId", userId);
        where.append(" where 1 = 1");
        where.append(" and uf.user_id = #{userId}");

        List<Record> friends = Db.init().selectList(sql + where.toString(), _r);
        List<Integer> voList = new ArrayList<>();
        for (Record record : friends) {
            voList.add(record.getInt("userId"));
        }
        return voList;
    }

    private List<Integer> listInterested(Integer userId) {
        List<Record> list = Db.init().selectList(ShouPageServiceImpl.SQL_INTERESTED_USER, Record.create().set("userId", userId));
        List<Integer> voList = new ArrayList<>();
        for (Record record : list) {
            voList.add(record.getInt("userId"));
        }
        return voList;
    }

    private List<Integer> listIntereste(Integer userId) {

        List<Integer> voList = new ArrayList<>();
        List<Record> userList = Db.init().selectList(ShouPageServiceImpl.SQL_INTEREST_USER,
                Record.create().set("userId", userId));
        for (Record user : userList) {
            voList.add(user.getInt("userId"));
        }
        return voList;
    }

    /**
     * 分享到关系用户
     *
     * @param lastActicleId 最后文章ID
     * @param obj           选择的关系用户类型
     */
    private void contributeToMyFriend(Integer lastActicleId, String obj) {
        String[] userIds = obj.split(",");
        ArticleShow articleShow;
        for (String userId : userIds) {
            articleShow = createArticleShowBean(lastActicleId, Integer.parseInt(userId), ARTICLE_SHOW_FRIEND);
            articleShowService.save(articleShow);
        }
    }

    /**
     * 创建文章
     *
     * @param article          文章内容
     * @param multipartRequest 文件
     */
    private Integer createActicle(Article article, MultipartRequest multipartRequest) {
        uploadImage(multipartRequest);
        article.setInterestQuantity(0);
        article.setReadingQuantity(0);
        article.setForwardingQuantity(0);
        return saveRtId(article);
    }

    /**
     * 上传图片
     *
     * @param multipartRequest 文件
     */
    private void uploadImage(MultipartRequest multipartRequest) {

    }

    /**
     * 投稿到杂志
     *
     * @param lastActicleId 文章ID
     * @param obj           选择的杂志ID
     */
    private void contributeToMagazine(Integer lastActicleId, String obj) {
        String[] idss = obj.split(",");
        try {
            Maganize maganize;
            for (String toId : idss) {
                maganize = new Maganize();
                maganize.setArticleId(lastActicleId);
                maganize.setType(ARTICLE_SHOW_MAGAZINE);
                maganize.setMagazineId(Integer.parseInt(toId));
                maganize.setStatus(ARTICLE_NOT_PROCESS);
                maganize.setCreateTime(DateTimeKit.nowLong());
                maganizeService.save(maganize);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 投稿到日报
     */
    private void contributeToDaily(Integer lastActicleId, String obj) {
        String[] idss = obj.split(",");
        Daily daily;
        for (String toId : idss) {
            daily = new Daily();
            daily.setArticleId(lastActicleId);
            daily.setCreateTime(DateTimeKit.nowLong());
            daily.setStatus(ARTICLE_NOT_PROCESS);
            daily.setType(ARTICLE_SHOW_DAILY);
            daily.setGroupId(Integer.parseInt(toId));
            dailyService.save(daily);
        }
    }

    private ArticleShow createArticleShowBean(Integer articleId, Integer toId, Integer type) {
        ArticleShow articleShow = new ArticleShow();
        articleShow.setType(type);
        articleShow.setArticleId(articleId);
        articleShow.setCreateTime(DateTimeKit.nowLong());
        articleShow.setToId(toId);
        return articleShow;
    }
}
