package com.smallchill.api.system.service.impl;

import com.smallchill.api.common.plugin.KXUitl;
import com.smallchill.api.common.plugin.YPUtil;
import com.smallchill.api.system.service.VcodeService;
import com.smallchill.core.toolbox.LeomanKit;
import com.smallchill.core.toolbox.kit.QuickCacheKit;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * 验证码发送与验证
 * Created by yesong on 2016/6/13.
 */
@Service
public class VcodeServiceImpl implements VcodeService {

    @Override
    public Boolean sendKX(String code, String... mobiles) {
        Boolean flag = KXUitl.sendKX(code, mobiles);
        putCache(code, mobiles);
        return true;
    }

    @Override
    public Boolean sendYP(String code, String... mobiles) {
//        String ENCODING = "UTF-8";
//        //设置模板ID，如使用1号模板:【#company#】您的验证码是#code#
//        long tpl_id = 1;
//        try {
//            //设置对应的模板变量值
//            String tpl_value = URLEncoder.encode("#code#",ENCODING) +"="
//                    + URLEncoder.encode(code, ENCODING) + "&"
//                    + URLEncoder.encode("#company#",ENCODING) + "="
//                    + URLEncoder.encode("云片网",ENCODING);
//            for (String mobile : mobiles) {
//                System.out.println(YPUtil.tplSendSms(YPUtil.apikey, tpl_id, tpl_value, mobile));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        putCache(code, mobiles);
        return true;
    }

    public void putCache(String value, String... keys) {
        for (String key : keys) {
            QuickCacheKit.initSms().put(key, value);
        }
    }

    @Override
    public Boolean validate(String mobile, String code) {
        String _code = get(mobile);
        if ("false".equals(_code) || StringUtils.isBlank(_code) || StringUtils.isBlank(code)) {
            return Boolean.FALSE;
        }
        if (!_code.equals(code)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public String get(String key) {
        String code = "";
        try {
            code = (String) QuickCacheKit.initSms().get(key, new Callable() {
                @Override
                public Object call() throws Exception {
                    return "" + Boolean.FALSE;
                }
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return code;
    }
}
