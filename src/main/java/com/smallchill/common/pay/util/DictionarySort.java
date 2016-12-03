package com.smallchill.common.pay.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * Created by Administrator on 2016/12/3 0003.
 */
public class DictionarySort {

    public static String createSign(SortedMap<String,String> parameters) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            sb.append(k + "=" + v + "&");
        }
        sb.append("key=").append(ConstantUtil.API_KEY);

        String appsign = MD5Util.MD5Encode(sb.toString(), "UTF-8").toUpperCase();
        System.out.println("appsign:" + appsign);
        return appsign;
    }
}
