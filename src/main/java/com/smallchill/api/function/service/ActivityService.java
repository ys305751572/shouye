package com.smallchill.api.function.service;

import com.smallchill.api.common.exception.ApiException;
import com.smallchill.api.common.exception.FriendExtendPriceException;
import com.smallchill.api.function.modal.vo.ActivityVo;
import com.smallchill.core.base.service.IService;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.grid.JqGrid;
import com.smallchill.web.model.Article;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 活动service
 * Created by yesong on 2017/2/10 0010.
 */
public interface ActivityService extends IService<Article> {

    List<ActivityVo> list(JqGrid page);

    ActivityVo detail(Integer activityId, Integer userId);

    Map<String, Object> apply(Integer activityId, Integer userId, Integer isPay, Integer money,
                              HttpServletRequest request, HttpServletResponse response) throws FriendExtendPriceException;

    void interest(Integer activityId, Integer userId);

    Integer getCost(Integer activityId);

    Map<Integer, List<Record>> myList(Integer userId);

    boolean isConform(Integer activityId, Integer userId) throws ApiException;
}
