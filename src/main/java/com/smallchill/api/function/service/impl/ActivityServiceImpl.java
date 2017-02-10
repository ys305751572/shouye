package com.smallchill.api.function.service.impl;

import com.smallchill.api.function.modal.vo.ActivityVo;
import com.smallchill.api.function.service.ActivityService;
import com.smallchill.core.base.service.BaseService;
import com.smallchill.core.toolbox.grid.JqGrid;
import com.smallchill.web.model.Article;

import java.util.List;

/**
 * 活动service
 * Created by yesong on 2017/2/10 0010.
 */
public class ActivityServiceImpl extends BaseService<Article> implements ActivityService {

    /**
     * 活动列表
     *
     * @return list
     */
    @Override
    public List<ActivityVo> list(JqGrid page) {

        return null;
    }

    /**
     * 活动详情
     *
     * @param activityId 活动ID
     * @return ActivityVo
     */
    @Override
    public ActivityVo detail(Integer activityId) {
        return null;
    }

    /**
     * 活动报名
     *
     * @param activityId 活动ID
     * @param userId     当前用户ID
     */
    @Override
    public void apply(Integer activityId, Integer userId) {

    }

    /**
     * 活动感兴趣
     *
     * @param activityId 活动ID
     * @param userId     当前用户ID
     */
    @Override
    public void interest(Integer activityId, Integer userId) {

    }
}
