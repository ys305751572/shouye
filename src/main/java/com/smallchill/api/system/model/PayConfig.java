package com.smallchill.api.system.model;

import com.smallchill.api.common.exception.PayErrorException;
import com.smallchill.common.pay.excute.PayRequest;
import com.smallchill.core.toolbox.kit.CollectionKit;
import com.smallchill.core.toolbox.kit.MapKit;
import com.smallchill.web.model.Order;
import com.smallchill.web.model.Refund;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PayConfig {

    public final static String NOTIFY_URL_WEIXIN = "http://121.40.63.108:8081/app/pay/weixiNotify";
    public final static String NOTIFY_URL_ALIPAY = "http://121.40.63.108:8081/app/pay/alipayNotify";



    /**
     * @param request
     * @param response
     * @param sn       订单号
     * @param totalFee 支付价格
     * @param payWay   支付方式 WEIXIN,ALIPAY
     * @return
     */
    public static Map<String, Object> config(HttpServletRequest request, HttpServletResponse response, String sn, Double totalFee, String payWay) {
        Map<String, Object> params = new HashMap<String, Object>();
        int totelFee = 1;
        if ("ALIPAY".equals(payWay)) {
            // 支付宝
            params.put("sn", sn);
            params.put("totelFee", totelFee);
            params.put("itemName", "支付");
            params.put("itemDesc", "支付");
            params.put("tag", "3");
            params.put("url", NOTIFY_URL_ALIPAY);
        } else {
            // 微信
            String prepayid = null; //预支付款ID
            request.setAttribute("fee", totelFee);
            request.setAttribute("sn", sn);
            request.setAttribute("prepayid", prepayid);
            params = PayRequest.pay(request, response, NOTIFY_URL_WEIXIN);
        }
        return params;
    }

    /**
     * 退款
     * @param request
     * @param response
     * @return
     */
    public static Refund refund(HttpServletRequest request, HttpServletResponse response, String orderNo, Integer userId) throws PayErrorException {

        request.setAttribute("orderNo", orderNo);
        Map<String,Object> resultMap = PayRequest.refund(request, response);
        if (CollectionKit.isNotEmpty(resultMap)) {
            String errCode = (String) resultMap.get("err_code");
            String returnCode = (String) resultMap.get("return_code");
            if (!"SUCCESS".equals(returnCode)) {
                throw new PayErrorException();
            }
            String err_code_des = MapKit.getStr(resultMap, "err_code_des");
            String out_trade_no = MapKit.getStr(resultMap, "out_trade_no");
            String transaction_id = MapKit.getStr(resultMap, "transaction_id");
            String out_refund_no = MapKit.getStr(resultMap, "out_refund_no");
            String refund_id = MapKit.getStr(resultMap, "refund_id");
            String refund_channel = MapKit.getStr(resultMap, "refund_channel");
            int refund_fee = MapKit.getInt(resultMap, "refund_fee");
            int settlement_refund_fee = MapKit.getInt(resultMap, "settlement_refund_fee");
            int total_fee = MapKit.getInt(resultMap, "total_fee");
            int settlement_total_fee = MapKit.getInt(resultMap, "settlement_total_fee");
            int cash_fee = MapKit.getInt(resultMap,"cash_fee");
            int cash_refund_fee = MapKit.getInt(resultMap, "cash_refund_fee");
            String coupon_type_$n = MapKit.getStr(resultMap, "coupon_type_$n");
            int coupon_refund_fee_$n = MapKit.getInt(resultMap, "coupon_refund_fee_$n");
            int coupon_refund_count_$n = MapKit.getInt(resultMap, "coupon_refund_count_$n");
            String coupon_refund_batch_id_$n_$m = MapKit.getStr(resultMap, "coupon_refund_batch_id_$n_$m");
            String coupon_refund_id_$n_$m = MapKit.getStr(resultMap, "coupon_refund_id_$n_$m");
            int coupon_refund_fee_$n_$m = MapKit.getInt(resultMap, "coupon_refund_fee_$n_$m");

            Refund refund = new Refund();
            refund.setUserId(userId);
            refund.setOrderNo(orderNo);
            refund.setErrCode(errCode);
            refund.setErrCodeDes(err_code_des);
            refund.setOutTradeNo(out_trade_no);
            refund.setTransactionId(transaction_id);
            refund.setOut_refund_no(out_refund_no);
            refund.setRefundId(refund_id);
            refund.setRefundChannel(refund_channel);
            refund.setRefundFee(refund_fee);
            refund.setSettlementRefundFee(settlement_refund_fee);
            refund.setTotalFee(total_fee);
            refund.setSettlementTotalFee(settlement_total_fee);
            refund.setCashFee(cash_fee);
            refund.setCashRefundFee(cash_refund_fee);
            refund.setCouponType$n(coupon_type_$n);
            refund.setCouponRefundFee$n(coupon_refund_fee_$n);
            refund.setCouponRefundCount$n(coupon_refund_count_$n);
            refund.setCouponRefundBatchId$n$m(coupon_refund_batch_id_$n_$m);
            refund.setCouponRefundId$n$m(coupon_refund_id_$n_$m);
            refund.setCouponRefundFee$n$m(coupon_refund_fee_$n_$m);
            return refund;
        }
        else {
            throw new PayErrorException();
        }
    }
}
