package com.smallchill.common.pay.excute;


import com.smallchill.api.common.exception.PayErrorException;
import com.smallchill.api.common.plugin.CommonUtils;
import com.smallchill.common.pay.AccessTokenRequestHandler;
import com.smallchill.common.pay.ClientRequestHandler;
import com.smallchill.common.pay.PrepayIdRequestHandler;
import com.smallchill.common.pay.util.ConstantUtil;
import com.smallchill.common.pay.util.WXUtil;
import com.smallchill.core.toolbox.kit.CollectionKit;
import com.smallchill.core.toolbox.kit.CommonKit;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class PayRequest {

    public static Map<String, Object> pay(HttpServletRequest request, HttpServletResponse response, String notifyUrl) {

        response.resetBuffer();
        response.setHeader("ContentType", "text/xml");
        String out_trade_no = request.getAttribute("sn").toString();
        PrepayIdRequestHandler prepayReqHandler = new PrepayIdRequestHandler(request, response);
        ClientRequestHandler clientHandler = new ClientRequestHandler(request, response);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 预支付ID
        String prepayid = "";
        //token
        String token = AccessTokenRequestHandler.getAccessToken();
        if (!"".equals(token)) {
            prepayReqHandler.setParameter("appid", ConstantUtil.APP_ID);

            prepayReqHandler.setParameter("body", "签约支付");
            prepayReqHandler.setParameter("device_info", ConstantUtil.DEVICE_INFO);
            prepayReqHandler.setParameter("fee_type", "CNY");
            prepayReqHandler.setParameter("mch_id", ConstantUtil.PARTNER);
            String noncestr = WXUtil.getNonceStr();
            prepayReqHandler.setParameter("nonce_str", noncestr);
            prepayReqHandler.setParameter("notify_url", notifyUrl);
            prepayReqHandler.setParameter("out_trade_no", out_trade_no);
//            prepayReqHandler.setParameter("spbill_create_ip", CommonUtils.getRealAddress(request));
            prepayReqHandler.setParameter("spbill_create_ip", "4.16.2.73");
            prepayReqHandler.setParameter("total_fee", "1");
            //prepayReqHandler.setParameter("total_fee", request.getAttribute("fee").toString());
            prepayReqHandler.setParameter("trade_type", ConstantUtil.TRADE_TYPE);

            String sign = prepayReqHandler.createMD5Sign();
            prepayReqHandler.setParameter("sign", sign);
            prepayid = prepayReqHandler.sendPrepay();
            if (null != prepayid && !"".equals(prepayid)) {
                clientHandler.setParameter("appid", ConstantUtil.APP_ID);
                clientHandler.setParameter("noncestr", noncestr);
                clientHandler.setParameter("package", "Sign=WXPay");
                clientHandler.setParameter("partnerid", ConstantUtil.PARTNER);
                clientHandler.setParameter("prepayid", prepayid);
                clientHandler.setParameter("timestamp", WXUtil.getTimeStamp());
                sign = clientHandler.createMD5Sign();
                clientHandler.setParameter("sign", sign);
                resultMap = clientHandler.getMap();
            }
        }
        return resultMap;
    }

    /**
     * 退款
     *
     * @param request
     * @param response
     * @return
     */
    public static Map<String, Object> refund(HttpServletRequest request, HttpServletResponse response) {
        response.resetBuffer();
        response.setHeader("ContentType", "text/xml");
        String out_trade_no = request.getAttribute("orderNo").toString();
        String out_refund_no = CommonKit.generateSn();
        PrepayIdRequestHandler prepayReqHandler = new PrepayIdRequestHandler(request, response);
        ClientRequestHandler clientHandler = new ClientRequestHandler(request, response);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //token
        String token = AccessTokenRequestHandler.getAccessToken();
        if (!"".equals(token)) {
            prepayReqHandler.setParameter("appid", ConstantUtil.APP_ID); // 微信分配的公众账号ID
            prepayReqHandler.setParameter("device_info", ConstantUtil.DEVICE_INFO); // 终端设备号
            prepayReqHandler.setParameter("mch_id", ConstantUtil.PARTNER); // 微信支付分配的商户号
            String noncestr = WXUtil.getNonceStr();
            prepayReqHandler.setParameter("nonce_str", noncestr); // 随机字符串，不长于32位
            prepayReqHandler.setParameter("op_user_id", ConstantUtil.PARTNER); // 操作员帐号, 默认为商户号
            prepayReqHandler.setParameter("out_trade_no", out_trade_no); // 商户侧传给微信的订单号
//            prepayReqHandler.setParameter("spbill_create_ip", CommonUtils.getRealAddress(request));
            prepayReqHandler.setParameter("out_refund_no", out_refund_no); // 商户系统内部的退款单号，商户系统内部唯一，同一退款单号多次请求只退一笔
            prepayReqHandler.setParameter("refund_fee","1"); // 退款总金额，订单总金额，单位为分，只能为整数
            prepayReqHandler.setParameter("total_fee", "1"); // 订单总金额，单位为分，只能为整数
            //prepayReqHandler.setParameter("total_fee", request.getAttribute("fee").toString());
            String sign = prepayReqHandler.createMD5Sign();
            prepayReqHandler.setParameter("sign", sign); // 签名
            resultMap = prepayReqHandler.refund();

            for (Map.Entry<String,Object> entity : resultMap.entrySet()) {
                System.out.println("key:" + entity.getKey() + "==== value:" + entity.getValue());
            }
        }
        return resultMap;
    }
}
