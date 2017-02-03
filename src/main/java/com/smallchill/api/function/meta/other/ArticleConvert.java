package com.smallchill.api.function.meta.other;

import com.smallchill.api.function.modal.vo.ArticleVo;
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
}
