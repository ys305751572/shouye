package com.smallchill.api.function.service.impl;

import com.smallchill.api.common.exception.*;
import com.smallchill.api.common.model.ErrorType;
import com.smallchill.api.function.meta.consts.StatusConst;
import com.smallchill.api.function.service.PayService;
import com.smallchill.api.system.model.PayConfig;
import com.smallchill.common.pay.util.XMLUtil;
import com.smallchill.core.modules.support.Conver;
import com.smallchill.core.toolbox.kit.CollectionKit;
import com.smallchill.core.toolbox.kit.CommonKit;
import com.smallchill.core.toolbox.kit.DateTimeKit;
import com.smallchill.web.model.GroupApproval;
import com.smallchill.web.model.Order;
import com.smallchill.web.service.GroupApprovalService;
import com.smallchill.web.service.GroupExtendService;
import com.smallchill.web.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * 支付相关service
 * Created by yesong on 2016/12/3 0003.
 */
@Service
public class PayServiceImpl implements PayService, StatusConst {

    @Autowired
    private GroupExtendService groupExtendService;

    @Autowired
    private GroupApprovalService groupApprovalService;

    @Autowired
    private OrderService orderService;

    /**
     * 获取预支付款ID
     *
     * @param userId
     * @param groupId
     * @param cost
     * @return
     * @throws GroupCostException
     */
    @Transactional
    @Override
    public Map<String, Object> getPrepayId(Integer userId, Integer groupId, Integer cost, String validateInfo,
                                           String matchType, HttpServletResponse response, HttpServletRequest request)
            throws GroupCostException, UserHasApprovalException, UserHasJoinGroupException, UserInOthersBlankException,
            GroupLimitException {

        Integer realCost = groupExtendService.getCost(groupId);
        if (realCost != null && !cost.equals(realCost)) {
            throw new GroupCostException();
        }
        if (realCost == null) return null;
        String orderNo = CommonKit.generateSn();
        Double c = Double.parseDouble(realCost.toString());


        Map<String, Object> resultMap = PayConfig.config(request, response, orderNo, c, WEIXIN);
        if (CollectionKit.isNotEmpty(resultMap)) {
            GroupApproval ga = new GroupApproval();
            ga.setUserId(userId);
            ga.setGroupId(groupId);
            ga.setValidateInfo(validateInfo);
            ga.setStatus(WAITING);
            ga.setPaied(NOT_PAY);
            ga.setMatchType(matchType);
            ga.setType(INITIATIVE);
            ga.setCreateTime(DateTimeKit.nowLong());
            ga.setThroughTime(0L);

            // 判断用户是否满足组织的加入限制条件
            if (!groupApprovalService.isMeetConditions(ga.getUserId(), ga.getGroupId())) {
                throw new GroupLimitException();
            }
            int gaId = groupApprovalService.join(ga);
            Order order = new Order();
            order.setGroupId(groupId);
            order.setUserId(userId);
            order.setGaId(gaId);
            order.setOrderNo(orderNo);
            order.setOrderAmount(c);
            order.setOrderType(ORDER_TYPE_COST);
            order.setStatus(ORDER_STATUS_ERROR);
            order.setCreateTime(DateTimeKit.nowLong());
            orderService.saveRtId(order);
        }
        return resultMap;
    }

    @Override
    public void joinGroupWxNotify(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> resultMap = parse(request);
        if (resultMap.get("result_code").equals("SUCCESS")) {
            // 成功
            String orderNo = resultMap.get("out_trade_no");

        }
    }

    @SuppressWarnings("unchecked")
    public Map<String, String> parse(HttpServletRequest request) {
        Map<String, String> resultMap = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String line = null;
            String result = "";
            while ((line = reader.readLine()) != null) {
                result += line;
            }
            System.out.println("result:" + result);
            resultMap = XMLUtil.doXMLParse(result);
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }
}
