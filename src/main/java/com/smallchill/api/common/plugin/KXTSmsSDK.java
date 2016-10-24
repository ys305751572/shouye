package com.smallchill.api.common.plugin;/*
 *  Copyright (c) 2015 The KXTSMS project authors. All Rights Reserved.
 *
 *  http://www.kesense.com
 *
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;

public class KXTSmsSDK {
    private String address = null;
    private int port = 0;
    private String account = null;
    private String token = null;

    /**
     * 初始化必须参数
     *
     * @param address 必选参数，服务器地址 不带 http://
     * @param port    必选参数，服务器点端口号
     * @param token   必选参数，API接口密码
     */
    public void init(String address, int port, String account, String token) {
        this.address = address;
        this.port = port;
        this.account = account;
        this.token = token;
    }

    /**
     * 发送短信（单条）
     *
     * @param tempId 短信模板ID
     * @param mobile 手机号码
     * @param body   短信内容
     * @param rType  响应数据类型   0 Json 类型，1 xml 类型
     * @return
     */
    public String send(int tempId, String body, Integer userid, short rType, String mToken, String extno, String... mobile) {
        StringBuffer buffer = new StringBuffer("");
        for (String m : mobile) {
            buffer.append(m).append(",");
        }
        String mobiles = buffer.toString().substring(0, buffer.length() - 1);
        String uriStr = MessageFormat.format("http://{0}:{1}/sms.aspx?action=send&userid={2}", address, String.valueOf(port), userid);
        return getResponse(uriStr, MessageFormat.format("account={0}&password={1}&tempid={2}&mobile={3}&content={4}&type={5}&sendTime={6}&extno={7}", account, token, String.valueOf(tempId), mobiles, body, String.valueOf(rType), mToken, extno));
    }

    /**
     * 获取剩余短信数量
     *
     * @param rType 响应数据类型   0 Json 类型，1 xml 类型
     * @return
     */
    public String smsNum(short rType) {
        String uriStr = MessageFormat.format("http://{0}:{1}/SMS/SMSNum", address, String.valueOf(port));
        return getResponse(uriStr, MessageFormat.format("account={0}&token={1}&type={2}", account, token, String.valueOf(rType)));
    }

    /**
     * 获取短信状态
     *
     * @param smsId 短信模板ID
     * @param rType 响应数据类型   0 Json 类型，1 xml 类型
     * @return
     */
    public String smsStatus(String smsId, short rType) {
        String uriStr = MessageFormat.format("http://{0}:{1}/SMS/SMSStatus", address, String.valueOf(port));
        return getResponse(uriStr, MessageFormat.format("account={0}&token={1}&smsid={2}&Type={3}", account, token, smsId, String.valueOf(rType)));
    }

    /**
     * 发送短信（群发）
     *
     * @param tempId 短信模板ID
     * @param mobile 手机号码，多个号码用英文分号;分割
     * @param body   短信内容
     * @param rType  响应数据类型   0 Json 类型，1 xml 类型
     * @return
     */
    public String sendMultiple(int tempId, String mobile, String body, short rType, String mToken, String extno) {
        String uriStr = MessageFormat.format("http://{0}:{1}/SMS/Send", address, String.valueOf(port));
        return getResponse(uriStr, MessageFormat.format("account={0}&token={1}&tempid={2}&mobile={3}&content={4}&type={5}&mToken={6}&extno={7}", account, token, String.valueOf(tempId), mobile, body, String.valueOf(rType), mToken, extno));
    }

    /**
     * 获取请求数据
     *
     * @param uriStr 请求路径
     * @param param  请求参数
     *               响应数据类型   0 Json 类型，1 xml 类型
     * @return
     */
    private String getResponse(String uriStr, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(uriStr);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setConnectTimeout(50000);
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
