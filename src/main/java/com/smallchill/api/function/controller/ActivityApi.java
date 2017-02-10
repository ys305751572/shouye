package com.smallchill.api.function.controller;

import com.smallchill.common.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 活动API
 * Created by yesong on 2017/2/10 0010.
 */
@RequestMapping(value = "/api/activity")
@Controller
public class ActivityApi extends BaseController {

    /**
     * 活动列表
     *
     * @param userId     用户ID
     * @param day        时间 day天前
     * @param provinceId 省份ID
     * @param cityId     城市ID
     * @param like       猜你喜欢 1:选择猜你喜欢筛选 2:不筛选
     * @return result
     */
    @PostMapping(value = "/list")
    @ResponseBody
    public String list(Integer userId, Integer day, Integer provinceId, Integer cityId, Integer like) {

        return null;
    }

    /**
     * 活动详情
     *
     * @param activityId 活动ID
     * @param userId     当前用户ID
     * @return result
     */
    @PostMapping(value = "/detail")
    @ResponseBody
    public String detail(Integer userId, Integer activityId) {
        return null;
    }

    /**
     * 活动报名
     *
     * @param activityId 活动ID
     * @param userId     当前用户ID
     * @return result
     */
    @PostMapping(value = "/apply")
    @ResponseBody
    public String apply(Integer userId, Integer activityId) {
        return null;
    }

    /**
     * 我的活动列表
     *
     * @param userId 当前用户ID
     * @return result
     */
    @PostMapping(value = "/myList")
    @ResponseBody
    public String myList(Integer userId) {
        return null;
    }
}
