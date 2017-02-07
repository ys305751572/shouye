package com.smallchill.api.function.meta.other;

import com.smallchill.api.function.modal.vo.ArticleVo;
import com.smallchill.core.toolbox.Record;
import com.smallchill.web.model.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * 文章转换器
 * Created by yesong on 2017/2/3 0003.
 */
public class ArticleConvert {

    public static ArticleVo toArticleVo(Article article) {
        ArticleVo vo = new ArticleVo();
        vo.setId(article.getId());
        vo.setCreateTime(article.getCreateTime());
        vo.setForwardingQuantity(article.getForwardingQuantity());
        vo.setInterestQuantity(article.getInterestQuantity());
        vo.setReadingQWantity(article.getReadingQuantity());
        vo.setTypename(null);
        return vo;
    }

    public static List<ArticleVo> toArticleVos(List<Article> articles) {
        List<ArticleVo> vos = new ArrayList<>();
        for (Article article : articles) {
            vos.add(toArticleVo(article));
        }
        return vos;
    }

    public static ArticleVo toArticleVo(Record record) {
        ArticleVo vo = new ArticleVo();
        vo.setId(record.getInt("id"));
        vo.setTypename(record.getStr("typename"));
        vo.setCreateTime(record.getLong("create_time"));
        vo.setArticleId(record.getInt("article_id"));
        vo.setAuthor(record.getStr("username"));
        vo.setCover(record.getStr("cover"));
        vo.setPushTime(record.getLong("push_time"));
        vo.setFromName(record.getStr("sharename"));
        vo.setType(record.getInt("type"));
        vo.setTitle(record.getStr("title"));
        return vo;
    }

    public static ArticleVo articleDetailToArticleVo(Record record) {
        ArticleVo vo = new ArticleVo();
        vo.setTypename(record.getStr("typename"));
        vo.setArticleId(record.getInt("id"));
        vo.setAuthor(record.getStr("username"));
        vo.setCover(record.getStr("cover"));
        vo.setPushTime(record.getLong("push_time"));
        vo.setTitle(record.getStr("title"));
        vo.setContent(record.getStr("content"));
        return vo;
    }

    public static List<ArticleVo> recordsToArticleVos(List<Record> records) {
        List<ArticleVo> vos = new ArrayList<>();
        for (Record record : records) {
            vos.add(toArticleVo(record));
        }
        return vos;
    }
}
