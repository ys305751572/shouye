package com.smallchill.api.system.model;

import com.smallchill.common.pay.excute.PayRequest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PayConfig {

    public final static String NOTIFY_URL_WEIXIN = "http://121.40.63.108:8081/app/pay/weixiNotify";
    public final static String NOTIFY_URL_ALIPAY = "http://121.40.63.108:8081/app/pay/alipayNotify";

    public final static String WEIXIN = "WEIXIN";
    public final static String ALIPAY = "ALIPAY";

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
}
