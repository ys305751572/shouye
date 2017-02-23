package com.smallchill.api.function.service.impl;

import com.smallchill.api.common.exception.ApiException;
import com.smallchill.api.common.exception.FriendExtendPriceException;
import com.smallchill.api.common.model.ErrorType;
import com.smallchill.api.function.meta.consts.StatusConst;
import com.smallchill.api.function.meta.other.ActivityConvert;
import com.smallchill.api.function.modal.ActivityApply;
import com.smallchill.api.function.modal.vo.ActivityVo;
import com.smallchill.api.function.service.ActivityApplyService;
import com.smallchill.api.function.service.ActivityService;
import com.smallchill.api.system.model.PayConfig;
import com.smallchill.core.base.service.BaseService;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.grid.JqGrid;
import com.smallchill.core.toolbox.kit.CollectionKit;
import com.smallchill.core.toolbox.kit.CommonKit;
import com.smallchill.core.toolbox.kit.DateTimeKit;
import com.smallchill.web.model.Article;
import com.smallchill.web.model.Order;
import com.smallchill.web.service.OrderService;
import com.smallchill.web.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 活动service
 * Created by yesong on 2017/2/10 0010.
 */
@Service
public class ActivityServiceImpl extends BaseService<Article> implements ActivityService, StatusConst {

    @Autowired
    private ActivityApplyService activityApplyService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserGroupService userGroupService;

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
    public ActivityVo detail(Integer activityId,Integer userId) {
        String sql = Blade.dao().getScript("Activity.detail").getSql();
        Record record = Db.init().selectOne(sql, Record.create().set("id", activityId));
        record.set("btnStatus", getBtnStatus(activityId, userId));
        Record r1 = isInterest(activityId, userId);
        if (r1 != null && r1.getInt("is_intereste") == 1) {
            record.set("isInterest", 2);
        }
        else if (r1 != null && r1.getInt("is_intereste") == 2){
            record.set("isInterest", 3);
        }
        else {
            record.set("isInterest", 1);
        }
        return ActivityConvert.recordToVo(record);
    }

    private Record isInterest(Integer activityId, Integer userId) {
        return Db.init().selectOne("select is_intereste from tb_activity_interest ai where ai.article_id = #{articleId} AND ai.user_id = #{userId}",
                Record.create().set("articleId", activityId).set("userId", userId));
    }

    /**
     * 活动报名
     *
     * @param activityId 活动ID
     * @param userId     当前用户ID
     */
    @Override
    public Map<String, Object> apply(Integer activityId, Integer userId, Integer isPay, Integer money,
                                     HttpServletRequest request, HttpServletResponse response) throws FriendExtendPriceException {
        if (isPay == 1) {
            // 未支付
            return applyWithPay(activityId, userId, money, request, response);
        } else {
            // 已支付
            applyWithoutPay(activityId, userId);
            return null;
        }
    }

    /**
     * 报名 报名费为0 或者选择已支付
     *
     * @param activityId 活动ID
     * @param userId     当前用户ID
     */
    private void applyWithoutPay(Integer activityId, Integer userId) {
        ActivityApply activityApply = new ActivityApply();
        activityApply.setCreateTime(DateTimeKit.nowLong());
        activityApply.setUserId(userId);
        activityApply.setActivityId(activityId);
        activityApply.setStatus(NOT_PAY);
        activityApplyService.save(activityApply);
    }

    private Map<String, Object> applyWithPay(Integer activityId, Integer userId, Integer money,
                                             HttpServletRequest request, HttpServletResponse response)
            throws FriendExtendPriceException {
        Integer cost = getCost(activityId);
        if (cost != money) {
            throw new FriendExtendPriceException();
        }
        String orderno = CommonKit.generateSn();
        Map<String, Object> payConfig = PayConfig.config(request, response, orderno, cost * 1.0,
                WEIXIN, PayConfig.NOTIFY_URL_WEIXIN_APPLY);
        if (CollectionKit.isNotEmpty(payConfig)) {
            Order order = new Order();
            order.setUserId(userId);
            order.setGroupId(activityId);
            order.setGaId(0);
            order.setOrderNo(orderno);
            order.setOrderAmount(Double.parseDouble(String.valueOf(money)));
            order.setOrderType(SQL_VALUE_ADD_SERVICE_TYPE_APPLY);
            order.setCounts(1);
            order.setStatus(ORDER_STATUS_ERROR);
            order.setCreateTime(DateTimeKit.nowLong());
            order.setFlow(flow_exit);
            order.setPlatform(1);
            orderService.save(order);
        } else {
            return null;
        }
        return payConfig;
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

    @Override
    public Integer getCost(Integer activityId) {
        String sql = "SELECT cost FROM tb_article a WHERE a.id = #{id}";
        Record record = Db.init().selectOne(sql, Record.create().set("id", activityId));
        return record.getInt("cost");
    }

    /**
     * 我的活动列表
     *
     * @param userId 当前用户ID
     * @return list
     */
    @Override
    public List myList(Integer userId) {
        String sql = Blade.dao().getScript("Activity.myList").getSql();
        List<Record> records = Db.init().selectList(sql, Record.create().set("userId", userId));
        return groupMap(records);
    }

    @Override
    public boolean isConform(Integer activityId, Integer userId) throws ApiException {
        int btnStatus = getBtnStatus(activityId, userId);
        if (btnStatus == ACTIVITY_BTN_STATUS_NOMAL) {
            return true;
        }
        // 判断报名时间是否截止
        else if (btnStatus == ACTIVITY_BTN_STATUS_APPLY_END) {
            throw new ApiException(ErrorType.ERROR_CODE_APP_ACTIVITY_APPLY_END_FAIL);
        }
        // 判断活动时间是否结束
        else if (btnStatus == ACTIVITY_BTN_STATUS_END) {
            throw new ApiException(ErrorType.ERROR_CODE_APP_ACTIVITY_TIME_END_FAIL);
        }
        // 判断人数是否已满
        else if (btnStatus == ACTIVITY_BTN_STATUS_ENOUGH) {
            throw new ApiException(ErrorType.ERROR_CODE_APP_ACTIVITY_APPLY_ENOUGH_FAIL);
        }
        // 判断用户是否符合限制
        else if (btnStatus == ACTIVITY_BTN_STATUS_MEMBER) {
            throw new ApiException(ErrorType.ERROR_CODE_APP_ACTIVITY_APPLY_MEMBER_FAIL);
        }
        else if (btnStatus == ACTIVITY_BTN_STATUS_NOT_MEMBER) {
            throw new ApiException(ErrorType.ERROR_CODE_APP_ACTIVITY_APPLY_NOT_MEMBER_FAIL);
        }
        else if (btnStatus == ACTIVITY_BTN_STATUS_NOT_INVITATION) {
            throw new ApiException(ErrorType.ERROR_CODE_APP_ACTIVITY_APPLY_NOT_INVITATION_FAIL);
        }
        else {
            throw new ApiException(ErrorType.ERROR_CODE_APP_ACTIVITY_APPLY_FAIL);
        }
    }

    /**
     * 判断活动详情报名按钮状态
     * @param activityId 活动ID
     * @param userId 用户ID
     * @return 状态
     */
    private int getBtnStatus(Integer activityId, Integer userId) {
        int btnStatus = ACTIVITY_BTN_STATUS_NOMAL;
        Article article = findById(activityId);
        // 判断报名时间是否截止
        long applyEndTime = article.getApplyEndTime();
        if (applyEndTime < DateTimeKit.nowLong()) {
            return ACTIVITY_BTN_STATUS_APPLY_END;
        }
        // 判断活动时间是否结束
        long endTime = article.getEndTime();
        if (endTime < DateTimeKit.nowLong()) {
            return ACTIVITY_BTN_STATUS_END;
        }
        // 判断人数是否已满
        int count = activityApplyService.count("article_id = #{articleId}", Record.create().set("articleId", activityId));
        if (article.getCeiling() <= count) {
            return ACTIVITY_BTN_STATUS_ENOUGH;
        }
        // 判断用户是否符合限制
        Integer limit = article.getLimit();
        if (limit == 2 || limit == 3) {
            // 是否需要会员
            Integer groupId = article.getFromId();
            boolean flag = userGroupService.isExist("select * from tb_user_group ug where ug.user_id = #{userId} and ug.group_id = #{groupId}",
                    Record.create().set("userId", userId).set("groupId", groupId));
            if (limit == 2 && !flag) {
                return ACTIVITY_BTN_STATUS_NOT_MEMBER;
            }
            if (limit == 3 && flag) {
                return ACTIVITY_BTN_STATUS_MEMBER;
            }
        } else if (limit == 4) {
            // 是否特邀嘉宾
            boolean flag = Db.init().isExist("select * from tb_invitation_user iu where iu.user_id = #{userId} and iu.article_id = #{articleId}",
                    Record.create().set("userId", userId).set("articleId", activityId));
            if (!flag) {
                return ACTIVITY_BTN_STATUS_NOT_INVITATION;
            }
        }
        // 是否已经报名
        boolean flag2 = Db.init().isExist("select * from article_id = #{articleId} and user_id = #{userId}",
                Record.create().set("articleId", activityId).set("userId", userId));
        if (flag2) {
            return ACTIVITY_BTN_STATUS_HAS_APPLY;
        }
        return btnStatus;
    }

    /**
     * 对结果数据进行分组
     * 分组未 1:未开始 2:进行中 3:已结束
     *
     * @param records list
     * @return map
     */
    private List groupMap(List<Record> records) {
        Map<String, Object> groupMap = new HashMap<>();
        List<Map> list = new ArrayList<>();
        List<Record> list1 = new ArrayList<>();
        List<Record> list2 = new ArrayList<>();
        List<Record> list3 = new ArrayList<>();
        if (CollectionKit.isNotEmpty(records)) {
            for (Record record : records) {
                long startTime = record.getLong("startTime");
                long endTime = record.getLong("endTime");
                long nowTime = DateTimeKit.nowLong();
                if (nowTime < startTime) {
                    // 未开始
                    list1.add(record);
                } else if (nowTime > endTime) {
                    // 已结束
                    list3.add(record);
                } else {
                    // 进行中
                    list2.add(record);
                }
            }
        }
        groupMap.put("type", 1); // 未开始
        groupMap.put("list", list1);
        list.add(groupMap);

        groupMap = new HashMap<>();
        groupMap.put("type", 2); // 进行中
        groupMap.put("list", list2);
        list.add(groupMap);

        groupMap = new HashMap<>();
        groupMap.put("type", 3);
        groupMap.put("list", list3);
        list.add(groupMap);
        return list;
    }
}
