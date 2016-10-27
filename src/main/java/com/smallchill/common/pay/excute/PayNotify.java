package com.smallchill.common.pay.excute;

import com.smallchill.common.pay.RequestHandler;
import com.smallchill.common.pay.ResponseHandler;
import com.smallchill.common.pay.client.ClientResponseHandler;
import com.smallchill.common.pay.client.TenpayHttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PayNotify {

    public static void notify(HttpServletRequest request, HttpServletResponse response) {
        String partner = "1900000109";
        // ��Կ
        String key = "8934e7d15453e97507ef794cf7b0519d";

        // ����֧��Ӧ�����
        ResponseHandler resHandler = new ResponseHandler(request, response);
        resHandler.setKey(key);
        if (resHandler.isTenpaySign()) {
            String notify_id = resHandler.getParameter("notify_id");
            RequestHandler queryReq = new RequestHandler(null, null);
            TenpayHttpClient httpClient = new TenpayHttpClient();
            ClientResponseHandler queryRes = new ClientResponseHandler();
            queryReq.init();
            queryReq.setKey(key);
            queryReq.setGateUrl("https://gw.tenpay.com/gateway/verifynotifyid.xml");
            queryReq.setParameter("partner", partner);
            queryReq.setParameter("notify_id", notify_id);

            httpClient.setTimeOut(5);
            try {
                httpClient.setReqContent(queryReq.getRequestURL());
                System.out.println("queryReq:" + queryReq.getRequestURL());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            // ��̨����
            if (httpClient.call()) {
                try {
                    queryRes.setContent(httpClient.getResContent());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("queryRes:" + httpClient.getResContent());
                queryRes.setKey(key);

                String retcode = queryRes.getParameter("retcode");
                String trade_state = queryRes.getParameter("trade_state");

                String trade_mode = queryRes.getParameter("trade_mode");

                if (queryRes.isTenpaySign() && "0".equals(retcode) && "0".equals(trade_state)
                        && "1".equals(trade_mode)) {
                    try {
                        resHandler.sendToCFT("Success");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {

                }

            } else {

            }
        } else {
        }
    }
}
