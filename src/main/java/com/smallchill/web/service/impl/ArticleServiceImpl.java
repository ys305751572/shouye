package com.smallchill.web.service.impl;

import com.smallchill.api.function.meta.consts.StatusConst;
import com.smallchill.api.function.meta.other.ArticleConvert;
import com.smallchill.api.function.modal.vo.ArticleVo;
import com.smallchill.api.function.service.impl.ShouPageServiceImpl;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.kit.CollectionKit;
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
import java.util.Arrays;
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

    /**
     * 机遇列表
     *
     * @param userId 当前用户ID
     * @return list
     */
    @Override
    public List<ArticleVo> listByUserId(Integer userId) {
        String sql = Blade.dao().getScript("Acticle.listByUserId").getSql();
        List<Record> list = Db.init().selectList(sql, Record.create().set("userId", userId));
        return ArticleConvert.recordsToArticleVos(list);
    }

    @Transactional
    @Override
    public void create(Article article, Integer userId, MultipartRequest multipartRequest, String obj) {
        // 创建文章
        article.setFromId(userId);
        Integer lastActicleId = createActicle(article, multipartRequest);
        String[] objs = obj.split("\\|", 3);
        if (StringUtils.isNotBlank(objs[0])) {
            contributeToMyFriend(lastActicleId, userId, objs[0]);
            contributeToInterested(lastActicleId, userId, objs[0]);
            contributeToIntereste(lastActicleId, userId, objs[0]);
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

    /**
     * 投稿给我的好友
     *
     * @param lastActicleId
     * @param userId
     * @param obj
     */
    private void contributeToMyFriend(Integer lastActicleId, Integer userId, String obj) {
        if (obj.contains("1")) {
            // 查询朋友
            List<Integer> list1 = friend(userId);
            contribute(lastActicleId, userId, list1, ARTICLE_SHOW_FRIEND);
        }
    }

    /**
     * 投稿给我感兴趣的人
     *
     * @param lastActicleId
     * @param userId
     * @param obj0
     */
    private void contributeToIntereste(Integer lastActicleId, Integer userId, String obj0) {
        if (obj0.contains("2")) {
            // 查询对我感兴趣的人
            List<Integer> list2 = listInterested(userId);
            contribute(lastActicleId, userId, list2, ARTICLE_SHOW_INTERESTED);
        }
    }

    /**
     * 投稿到对我感兴趣的人
     *
     * @param lastActicleId
     * @param userId
     * @param obj0
     */
    private void contributeToInterested(Integer lastActicleId, Integer userId, String obj0) {
        if (obj0.contains("3")) {
            // 查询我感兴趣的人
            List<Integer> list3 = listIntereste(userId);
            contribute(lastActicleId, userId, list3, ARTICLE_SHOW_INTEREST);
        }
    }

    private List<Integer> friend(Integer userId) {
        String sql = "select friend_id userId from tb_user_friend where user_id = #{userId}";
        List<Record> friends = Db.init().selectList(sql, Record.create().set("userId", userId));
        List<Integer> voList = new ArrayList<>();
        for (Record record : friends) {
            voList.add(record.getInt("userId"));
        }
        return voList;
    }

    private List<Integer> listInterested(Integer userId) {

        String sql = "select user_id userId from tb_interest_user where to_user_id = #{userId}";
        List<Record> list = Db.init().selectList(sql,
                Record.create().set("userId", userId));
        List<Integer> voList = new ArrayList<>();
        for (Record record : list) {
            voList.add(record.getInt("userId"));
        }
        return voList;
    }

    private List<Integer> listIntereste(Integer userId) {

        String sql = "select to_user_id userId from tb_interest_user where user_id = #{userId}";
        List<Integer> voList = new ArrayList<>();
        List<Record> userList = Db.init().selectList(sql, Record.create().set("userId", userId));
        for (Record user : userList) {
            voList.add(user.getInt("userId"));
        }
        return voList;
    }

    /**
     * 分享到关系用户
     *
     * @param lastActicleId 最后文章ID
     */
    @Override
    public void contribute(Integer lastActicleId, Integer userId, List<Integer> userIds, int type) {
        ArticleShow articleShow;
        for (Integer _userId : userIds) {
            articleShow = createArticleShowBean(lastActicleId, userId, _userId, type);
            articleShowService.save(articleShow);
        }
    }

    /**
     * 查询用户发布
     *
     * @param userId 当前用户ID
     * @return list
     */
    @Override
    public List<ArticleVo> findByUserId(Integer userId) {
        String sql = Blade.dao().getScript("Acticle.publishListByUserId").getSql();
        List<Record> records = Db.init().selectList(sql, Record.create().set("userId", userId));
        return ArticleConvert.publishToArticleVos(records);
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
        article.setFromType(ARTICLE_FROM_TYPE_PEPOLE);
        article.setCreateTime(DateTimeKit.nowLong());
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
    @Override
    public void contributeToMagazine(Integer lastActicleId, String obj) {
        String[] idss = obj.split(",");
        try {
            Maganize maganize;
            for (String toId : idss) {
                maganize = new Maganize();
                maganize.setArticleId(lastActicleId);
                maganize.setType(ARTICLE_FROM_TYPE_PEPOLE);
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
    @Override
    public void contributeToDaily(Integer lastActicleId, String obj) {
        String[] idss = obj.split(",");
        Daily daily;
        try {
            for (String toId : idss) {
                daily = new Daily();
                daily.setArticleId(lastActicleId);
                daily.setCreateTime(DateTimeKit.nowLong());
                daily.setStatus(ARTICLE_NOT_PROCESS);
                daily.setType(ARTICLE_FROM_TYPE_PEPOLE);
                daily.setGroupId(Integer.parseInt(toId));
                dailyService.save(daily);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 删除文章
     *
     * @param id     文章ID
     * @param userId 当前用户ID
     */
    @Transactional
    @Override
    public void deleteById(Integer id, Integer userId) {
        deleteArticleShow(id);
        deleteDailyAndMagazine(id);
        delete(id);
    }

    private void deleteDailyAndMagazine(Integer id) {
        Record record = Record.create().set("articleId", id);
        String sql = "article_id = #{articleId}";
        maganizeService.deleteBy(sql, record);
        dailyService.deleteBy(sql, record);
    }

    /**
     * 文章详情
     *
     * @param id 文章ID
     * @return 文章详情
     */
    @Override
    public ArticleVo detail(Integer id) {

        String sql = Blade.dao().getScript("Acticle.findById").getSql();
        Record record = Db.init().selectOne(sql, Record.create().set("id", id));

        //  增加阅读量
        addReadCount(id);
        return ArticleConvert.articleDetailToArticleVo(record);
    }

    private void deleteArticleShow(Integer id) {
        articleShowService.deleteBy("article_id = #{articleId}", Record.create().set("articleId", id));
    }

    private ArticleShow createArticleShowBean(Integer articleId, Integer fromUserId, Integer toId, Integer type) {
        ArticleShow articleShow = new ArticleShow();
        articleShow.setType(type);
        articleShow.setFromId(fromUserId);
        articleShow.setIsIntereste(ARTICLE_SHOW_NOMAL_TYPE);
        articleShow.setArticleId(articleId);
        articleShow.setCreateTime(DateTimeKit.nowLong());
        articleShow.setToId(toId);
        return articleShow;
    }

    /**
     * 新增阅读数量
     *
     * @param articleId 文章ID
     */
    public void addReadCount(int articleId) {
        updateBy("reading_quantity = reading_quantity + 1", "id = #{id}", Record.create().set("id", articleId));
    }

    /**
     * 新增感兴趣数量
     *
     * @param articleId 文章ID
     */
    public void addInterestCount(int articleId) {
        updateBy("interest_quantity = interest_quantity + 1", "id = #{id}", Record.create().set("id", articleId));
    }

    /**
     * 减少感兴趣数量
     *
     * @param articleId 文章ID
     */
    public void subtractInterestCount(int articleId) {
        updateBy("interest_quantity = interest_quantity - 1", "id = #{id}", Record.create().set("id", articleId));
    }

    @Override
    public void share(int articleId, int userId, String toUserIds) {
        String[] toUserIdss = toUserIds.split(",");
        List<Integer> list = new ArrayList<>();
        for (String toUserId : toUserIdss) {
            list.add(Integer.parseInt(toUserId));
        }
        contribute(articleId, userId, list, ARTICLE_SHOW_SHARE);
        addShareCount(articleId);
    }

    /**
     * 新增分享数量
     *
     * @param articleId 文章ID
     */
    public void addShareCount(int articleId) {
        updateBy("forwarding_quantity = forwarding_quantity + 1", "id = #{id}", Record.create().set("id", articleId));
    }

    @Override
    public List<ArticleVo> listInterest(Integer userId) {
        return null;
    }

    @Override
    public List<ArticleVo> listUnInterest(Integer userId) {
        return null;
    }
}
