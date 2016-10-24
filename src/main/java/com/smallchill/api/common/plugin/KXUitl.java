package com.smallchill.api.common.plugin;/*
 * 短信发送（单条）示例
 *  Copyright (c) 2015 The KXTSMS project authors. All Rights Reserved.
 *
 *  http://www.kesense.com
 *
 */

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


public class KXUitl {

    private static String address = "120.76.25.160";//远程地址：不带http://
    private static int port = 7788;//远程端口
    private static Integer userid = 109;
    private static String account = "yqyl";//账户
    private static String token = "520620";//token
    private static int tempId = 0;
    //	private static String mobile = "15972129886";//发送手机号
    private static String body = "【亿次元】验证码：";//短信内容
    private static short rType = 1;//响应类型  0 json类型，1 xml类型
    private static String mToken = "";//可选
    private static String extno = "";//扩展号 可选

    public static boolean sendKX(String code, String... mobile) {
        KXTSmsSDK kxtsms = new KXTSmsSDK();
        kxtsms.init(address, port, account, token);
        try {
            body = URLEncoder.encode(("【亿奇娱乐】验证码：" + code), "UTF-8");//URL编码 UTF-8方式
        } catch (Exception e) {

        }
        String result = kxtsms.send(tempId, body, userid, rType, mToken, extno, mobile);
        Map<String, Object> hashMap = null;
        if (rType == 0) {
            //json
            hashMap = CommonUtils.jsonToMap(result);
        }
        if (rType == 1) {
            //xml
            hashMap = CommonUtils.xmlToMap(result);
        }
        if (hashMap != null) {
            String returnstatus = (String) hashMap.get("returnstatus");
            if ("Success".equals(returnstatus)) {
                return true;
            } else {
                return false;
            }
            //写自己的业务逻辑代码
            //hashMap.get("Code");
        } else {
            return false;
        }
    }
}
