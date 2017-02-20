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
        vo.setArticleId(article.getId());
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
        vo.setAuthorId(record.getInt("userId"));
        vo.setFromName(record.getStr("sharename"));
        vo.setAuthorType(record.getInt("type2"));
        vo.setType(record.getInt("type"));
        vo.setTitle(record.getStr("title"));
        return vo;
    }

    public static ArticleVo articleDetailToArticleVo(Record record) {
        ArticleVo vo = new ArticleVo();
        vo.setTypename(record.getStr("typename"));
        vo.setArticleId(record.getInt("id"));
        vo.setAuthor(record.getStr("username"));
        vo.setAuthorId(record.getInt("from_id"));
        vo.setAuthorType(record.getInt("from_type"));
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

    public static ArticleVo publishToArticleVo(Record record) {
        ArticleVo vo = new ArticleVo();
        vo.setArticleId(record.getInt("id"));
        vo.setTitle(record.getStr("title"));
        vo.setCover(record.getStr("cover"));
        vo.setTypename(record.getStr("typename"));
        vo.setAuthor(record.getStr("username"));
        vo.setForwardingQuantity(record.getInt("forwarding_quantity"));
        vo.setInterestQuantity(record.getInt("interest_quantity"));
        vo.setReadingQWantity(record.getInt("reading_quantity"));
        vo.setCreateTime(record.getLong("create_time"));
        return vo;
    }

    public static List<ArticleVo> publishToArticleVos(List<Record> records) {
        List<ArticleVo> list = new ArrayList<>();
        for (Record record : records) {
            list.add(publishToArticleVo(record));
        }
        return list;
    }
}
