package com.smallchill.api.function.controller;

import com.smallchill.api.common.exception.ApiException;
import com.smallchill.api.common.exception.FriendExtendPriceException;
import com.smallchill.api.common.kit.ExcludeParams;
import com.smallchill.api.common.model.ErrorType;
import com.smallchill.api.function.meta.intercept.ActivityIntercept;
import com.smallchill.api.function.meta.intercept.ApplyUsersIntercept;
import com.smallchill.api.function.meta.intercept.ArticleIntercept;
import com.smallchill.api.function.meta.validate.ActivityApplyValidate;
import com.smallchill.api.function.meta.validate.ActivityIdValidate;
import com.smallchill.api.function.meta.validate.UserIdValidate;
import com.smallchill.api.function.modal.vo.ActivityVo;
import com.smallchill.api.function.service.ActivityService;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.grid.JqGrid;
import com.smallchill.core.toolbox.kit.CollectionKit;
import com.smallchill.web.model.MagazineInfo;
import com.smallchill.web.model.UserInfo;
import com.smallchill.web.service.DailyService;
import com.smallchill.web.service.MagazineInfoService;
import com.smallchill.web.service.UserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 活动API
 * Created by yesong on 2017/2/10 0010.
 */
@RequestMapping(value = "/api/activity")
@Controller
public class ActivityApi extends BaseController {

    @Autowired
    private ActivityService activityService;
    @Autowired
    private MagazineInfoService magazineInfoService;
    @Autowired
    private DailyService dailyService;
    @Autowired
    private UserInfoService userInfoService;

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
    public String list(Integer userId, Integer like) {
        List<MagazineInfo> maganizeInfoVos = magazineInfoService.simpleListByUserId(userId);
        List<Record> dailys = dailyService.simpleListByUserId(userId);

        StringBuilder maganzineIds = new StringBuilder();
        if (CollectionKit.isNotEmpty(maganizeInfoVos)) {
            for (MagazineInfo magazineInfo : maganizeInfoVos) {
                maganzineIds.append(magazineInfo.getId()).append(",");
            }
        }

        StringBuffer dailyIds = new StringBuffer();
        if (CollectionKit.isNotEmpty(dailys)) {
            for (Record record : dailys) {
                dailyIds.append(record.getInt("id")).append(",");
            }
        }
        String keyWord = "";
        if (like != null && like == 1) {
            UserInfo userInfo = userInfoService.findByUserId(userId);
            keyWord = userInfo.getKeyWord();
        }

        JqGrid page;
        try {
            page = apiPaginate("Activity.list", new ActivityIntercept().addRecord(Record.create()
                            .set("time", getParameter("time"))
                            .set("provinceId", getParameter("provinceId"))
                            .set("cityId", getParameter("cityId"))
                            .set("like", this.getParameter("like"))
                            .set("userId", this.getParameter("userId"))
                            .set("keyWord", keyWord)
                            .set("maganzineIds", StringUtils.isNotBlank(maganzineIds) ? maganzineIds.substring(0, maganzineIds.length() - 1) : "")
                            .set("dailyIds", StringUtils.isNotBlank(dailyIds) ? dailyIds.substring(0, dailyIds.length() - 1) : "")),
                    ExcludeParams.create().set("time").set("provinceId").set("cityId").set("like").set("userId"));
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
        return success(page);
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
    @Before(UserIdValidate.class)
    public String detail(Integer activityId,Integer userId) {
        ActivityVo vo;
        try {
            vo = activityService.detail(activityId, userId);
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
        return success(vo);
    }

    /**
     * 活动报名用户列表
     *
     * @param activityId 活动ID
     * @return result
     */
    @PostMapping(value = "/applyuser/list")
    @ResponseBody
    @Before(ActivityIdValidate.class)
    public String applyUsers() {
        JqGrid page;
        try {
            page = apiPaginate("Activity.applyUserList", new ApplyUsersIntercept().addRecord(Record.create()
                            .set("userId", this.getParameter("userId")).set("activityId", this.getParameter("activityId")))
                    , ExcludeParams.create().set("userId").set("activityId"));
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
        return success(page);
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
    @Before(ActivityApplyValidate.class)
    public String apply(Integer userId, Integer activityId, Integer isPay, Integer money) {
        Map<String, Object> resultMap;

        try {
            activityService.isConform(activityId, userId);
        } catch (ApiException e) {
            return fail(e.getErrorType());
        }
        try {
            resultMap = activityService.apply(activityId, userId, isPay, money, this.getRequest(), this.getResponse());
            if (isPay == 1) {
                return success(resultMap, "prepayIdConfig");
            } else {
                return success();
            }
        } catch (FriendExtendPriceException e) {
            return fail(ErrorType.ERROR_CODE_APP_USERINFO_EXTEND_PRICE_FAIL);
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
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
        Map<Integer, List<Record>> resultMap;
        try {
            resultMap = activityService.myList(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
        return success(resultMap, "myActivitys");
    }
}
