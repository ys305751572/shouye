//package com.smallchill.common.pay;
//
//import com.smallchill.common.pay.excute.PayRequest;
//import org.springframework.stereotype.Service;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * 支付API
// * Created by yesong on 2016/12/3 0003.
// */
//@Service
//public class PayServiceImpl implements PayService{
//
//    @Override
//    public Map<String, Object> getPrepayId(HttpServletRequest request, HttpServletResponse response) {
//        return PayRequest.pay(request, response, NOTIFY_URL_WEIXIN);
//    }
//
//    @Override
//    public Map<String, Object> getPayConfig(HttpServletRequest request, HttpServletResponse response, String sn, Double totalFee, String payWay) {
//        Map<String, Object> params = new HashMap<String, Object>();
//        int totelFee = 1;
//        if (ALIPAY.equals(payWay)) {
//            // 支付宝
//            params.put("sn", sn);
//            params.put("totelFee", totelFee);
//            params.put("itemName", "支付");
//            params.put("itemDesc", "支付");
//            params.put("tag", "3");
//            params.put("url", NOTIFY_URL_ALIPAY);
//        } else {
//            // 微信
//            String prepayid = null; //预支付款ID
//            request.setAttribute("fee", totelFee);
//            request.setAttribute("sn", sn);
//            request.setAttribute("prepayid", prepayid);
//            params = getPrepayId(request, response);
//        }
//        return params;
//    }
//
//    @Override
//    public Map<String, Object> refund() {
//        return null;
//    }
//}
