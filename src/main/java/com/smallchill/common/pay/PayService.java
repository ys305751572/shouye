//package com.smallchill.common.pay;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.Map;
//
///**
// * 支付接口
// * Created by yesong on 2016/12/3 0003.
// */
//public interface PayService {
//
//    String IP = "121.40.63.108:8080";
//    String NOTIFY_URL_WEIXIN = "http://" + IP + "/app/pay/weixiNotify";
//    String NOTIFY_URL_ALIPAY = "http://" + IP + "/app/pay/alipayNotify";
//
//    String WEIXIN = "WEIXIN";
//    String ALIPAY = "ALIPAY";
//
//    Map<String, Object> getPrepayId(HttpServletRequest request, HttpServletResponse response);
//
//    Map<String, Object> getPayConfig(HttpServletRequest request, HttpServletResponse response, String sn, Double totalFee, String payWay);
//
//    Map<String, Object> refund();
//
//}
