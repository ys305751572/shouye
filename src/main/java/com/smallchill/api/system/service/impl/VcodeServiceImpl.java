package com.smallchill.api.system.service.impl;

import com.smallchill.api.common.plugin.KXUitl;
import com.smallchill.api.system.service.VcodeService;
import com.smallchill.core.toolbox.LeomanKit;
import com.smallchill.core.toolbox.kit.QuickCacheKit;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

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
        return flag;
    }

    @Override
    public Boolean sendYP(String code, String... mobiles) {
        return null;
    }

    public void putCache(String value, String... keys) {
        for (String key : keys) {
            QuickCacheKit.initSms().put(key, LeomanKit.generateCode());
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
        try {
            return (String) QuickCacheKit.initSms().get(key, new Callable() {
                @Override
                public Object call() throws Exception {
                    return "" + Boolean.FALSE;
                }
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
