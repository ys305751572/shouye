package com.smallchill.api.function.service.impl;

import com.smallchill.api.common.exception.*;
import com.smallchill.api.common.model.ErrorType;
import com.smallchill.api.function.meta.consts.StatusConst;
import com.smallchill.api.function.meta.other.Convert;
import com.smallchill.api.function.service.PayService;
import com.smallchill.api.system.model.PayConfig;
import com.smallchill.common.pay.refund.MobiMessage;
import com.smallchill.common.pay.refund.RefundReqData;
import com.smallchill.common.pay.refund.RefundRequest;
import com.smallchill.common.pay.util.ConstantUtil;
import com.smallchill.common.pay.util.WXUtil;
import com.smallchill.common.pay.util.XMLUtil;
import com.smallchill.core.toolbox.kit.CollectionKit;
import com.smallchill.core.toolbox.kit.CommonKit;
import com.smallchill.core.toolbox.kit.DateTimeKit;
import com.smallchill.web.model.*;
import com.smallchill.web.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 支付相关service
 * Created by yesong on 2016/12/3 0003.
 */
@Service
public class PayServiceImpl implements PayService, StatusConst {

    private static final Logger LOGGER = LoggerFactory.getLogger(PayServiceImpl.class);

    @Autowired
    private GroupExtendService groupExtendService;
    @Autowired
    private GroupApprovalService groupApprovalService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private FriendExpandService friendExpandService;
    @Autowired
    private UserInfoExtendService userInfoExtendService;
    @Autowired
    private RefundService refundService;

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
    public Map<String, Object> getPrepayId(Integer userId, Integer groupId, Double cost, String validateInfo,
                                           Integer matchType, Integer targetType, HttpServletResponse response, HttpServletRequest request)
            throws GroupCostException, UserHasApprovalException, UserHasJoinGroupException, UserInOthersBlankException,
            GroupLimitException {

        Double realCost = groupExtendService.getCost(groupId);
        if (realCost != null && !cost.equals(realCost)) {
            throw new GroupCostException();
        }
        if (realCost == null) return null;
        String orderNo = CommonKit.generateSn();
        Double c = Double.parseDouble(realCost.toString());


        Map<String, Object> resultMap = PayConfig.config(request, response, orderNo, c, WEIXIN, PayConfig.NOTIFY_URL_WEIXIN_GROUP_JOIN);
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
            order.setCounts(1);
            order.setPlatform(1);
            order.setFlow(flow_exit);
            orderService.saveRtId(order);
        }
        return resultMap;
    }

    /**
     * 加入群-支付回调
     *
     * @param request
     * @param response
     */
    @Transactional
    @Override
    public void joinGroupWxNotify(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> resultMap = parse(request);
        if (resultMap.get("result_code").equals("SUCCESS")) {
            // 成功
            String orderNo = resultMap.get("out_trade_no");
            // 修改订单状态
            // 修改入群申请信息状态
            Order order = orderService.findByOrderNo(orderNo);
            if (order != null && order.getStatus() == ORDER_STATUS_ERROR) {
                orderService.setOrderSuccess(order);
                groupApprovalService.setPaiedStatusSuccess(order.getGaId());
            }
        }
    }

    /**
     * 增值服务--获取预支付款ID
     *
     * @param userId 当前用户ID
     * @param type   增值类型 1: 感兴趣人数 2: 熟人人数
     * @param number 增加数量
     * @param money  金额
     * @return result
     */
    @Transactional
    @Override
    public Map<String, Object> getPrepayIdOfValueaddService(Integer userId, Integer type, Integer number, Double money,
                                                            HttpServletResponse response, HttpServletRequest request)
            throws UserInfoExtendException, FriendExtendPriceException {
        FriendExpand fe = null;
        int allCount = 0;
        int userCount = 0;
        int dbType = 0;
        double unitPrice = 0;
        UserInfoExtend ue = userInfoExtendService.findByUserId(userId);
        if (type == VALUE_ADD_SERVICE_TYPE_INTEREST) {
            fe = friendExpandService.findInterestConfig();
            allCount = fe.getNum();
            userCount = ue.getInterestCount();
            dbType = SQL_VALUE_ADD_SERVICE_TYPE_INTEREST;
            unitPrice = fe.getAmount();
        } else {
            fe = friendExpandService.findAcquaintanceConfig();
            allCount = fe.getNum();
            userCount = ue.getAcquaintanceCount();
            dbType = SQL_VALUE_ADD_SERVICE_TYPE_ACQUAINTANCE;
            unitPrice = fe.getAmount();
        }
        if ((number + userCount) > allCount) {
            throw new UserInfoExtendException();
        }
        if ((number * unitPrice) != money) {
            throw new FriendExtendPriceException();
        }

        String orderNo = CommonKit.generateSn();
        Map<String, Object> resultMap = PayConfig.config(request, response, orderNo,
                Double.parseDouble(String.valueOf(money)), WEIXIN, PayConfig.NOTIFY_URL_WEIXIN_VALUEADD);
        if (CollectionKit.isNotEmpty(resultMap)) {
            Order order = new Order();
            order.setUserId(userId);
            order.setGroupId(0);
            order.setGaId(0);
            order.setOrderNo(orderNo);
            order.setOrderAmount(Double.parseDouble(String.valueOf(money)));
            order.setOrderType(dbType);
            order.setCounts(number);
            order.setStatus(ORDER_STATUS_ERROR);
            order.setCreateTime(DateTimeKit.nowLong());
            order.setFlow(flow_exit);
            order.setPlatform(1);
            orderService.save(order);
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getPrepayIdOfValueaddServiceInterest(Integer userId, Integer number, Integer money) {
        return null;
    }

    @Override
    public Map<String, Object> getPrepayIdOfValueaddServiceAcquaintance(Integer userId, Integer number, Integer money) {
        return null;
    }


    /**
     * 增值服务--微信回调
     *
     * @param request
     * @param response
     */
    @Transactional
    @Override
    public void valueaddServiceWxNotify(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> resultMap = parse(request);
        if (resultMap.get("result_code").equals("SUCCESS")) {
            // 成功
            String orderNo = resultMap.get("out_trade_no");
            Order order = orderService.findByOrderNo(orderNo);
            if (order != null && order.getStatus() == ORDER_STATUS_ERROR) {
                // 修改订单状态
                // 修改用户拓展信息数据
                orderService.setOrderSuccess(order);

                int interestCount = 0;
                int acquaintanceCount = 0;
                int type = order.getOrderType();
                if (type == SQL_VALUE_ADD_SERVICE_TYPE_INTEREST) {
                    interestCount = order.getCounts();
                } else if (type == SQL_VALUE_ADD_SERVICE_TYPE_ACQUAINTANCE) {
                    acquaintanceCount = order.getCounts();
                }
                userInfoExtendService.saveUserInfoExtend(order.getUserId(), interestCount, acquaintanceCount);
            }
        }
    }

    @Override
    public void refund(Integer gaId, HttpServletRequest request, HttpServletResponse response) {
        //获得当前目录
        String path = request.getSession().getServletContext().getRealPath("/");
        path = path + File.separator + "cert" + File.separator + "10016225.p12";
        LOGGER.info("path:" + path);
        String outRefundNo = CommonKit.generateSn();
        Order order = orderService.findByGaId(gaId);
        //获得退款的传入参数
        String outTradeNo = order.getOrderNo();
        Integer totalFee = Double.valueOf(order.getOrderAmount() * 100).intValue();
        Integer refundFee = totalFee;

        RefundReqData refundReqData = new RefundReqData(null, outTradeNo, outRefundNo, totalFee, refundFee);
        String info = MobiMessage.RefundReqData2xml(refundReqData).replaceAll("__", "_");
        LOGGER.info("info:" + info);
        try {
            RefundRequest refundRequest = new RefundRequest();
            String result = refundRequest.httpsRequest(ConstantUtil.REFUNDEURL, info, path);
            LOGGER.info("result:" + result);
            Map<String, String> getMap = MobiMessage.parseXml(new String(result.getBytes(), "utf-8"));
            Refund refund = Convert.resultMapToRefund(getMap);
            refund.setUserId(order.getUserId());
            refund.setOrderNo(order.getOrderNo());
            refund.setRefundType(ORDER_TYPE_COST);
            refundService.save(refund);
            if ("SUCCESS".equals(getMap.get("return_code"))) {
                // 正确
                orderService.setOrderRefuseSuccess(order);
            }
            else {
                orderService.setOrderRefuseRefuse(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
