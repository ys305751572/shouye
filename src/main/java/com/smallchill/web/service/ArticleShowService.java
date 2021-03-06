package com.smallchill.web.service;

import com.smallchill.core.base.service.IService;
import com.smallchill.web.model.ArticleShow;

/**
 * Generated by Blade.
 * 2017-01-13 16:15:01
 */
public interface ArticleShowService extends IService<ArticleShow> {

    void interest(int id, int articleId, int userId);

    void uninterest(int id, int articleId, int position, int userId);

    void move(int id,int articleId, int userId);

    void shielding(int fromId, int toId, int type, int userId);

    void updateInterestById(int id, int type, int userId);
}
