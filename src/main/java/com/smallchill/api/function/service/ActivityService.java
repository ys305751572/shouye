package com.smallchill.api.function.service;

import com.smallchill.api.function.modal.vo.ActivityVo;
import com.smallchill.core.base.service.IService;
import com.smallchill.core.toolbox.grid.JqGrid;
import com.smallchill.web.model.Article;

import java.util.List;

/**
 * 活动service
 * Created by yesong on 2017/2/10 0010.
 */
public interface ActivityService extends IService<Article> {

    List<ActivityVo> list(JqGrid page);

    ActivityVo detail(Integer activityId);

    void apply(Integer activityId, Integer userId);

    void interest(Integer activityId, Integer userId);
}
