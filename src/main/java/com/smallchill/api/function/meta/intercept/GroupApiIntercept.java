package com.smallchill.api.function.meta.intercept;

import com.smallchill.api.function.meta.other.Convert;
import com.smallchill.core.aop.AopContext;
import com.smallchill.core.meta.ApiQueryIntercept;
import com.smallchill.core.toolbox.support.BladePage;
import com.smallchill.web.model.UserInfo;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 组织API拦截器
 * Created by yesong on 2016/11/3 0003.
 */
public class GroupApiIntercept extends ApiQueryIntercept {

    @Override
    public void queryAfter(AopContext ac) {
        BladePage bladePage = (BladePage) ac.getObject();
        List<Map> list = bladePage.getRows();
        for (Map record : list) {
            if (record.get("status") == null || Integer.parseInt(record.get("status").toString()) == 1
                    || Integer.parseInt(record.get("status").toString()) == 3
                    || Integer.parseInt(record.get("status").toString()) == 4) {
                record.put("isjoin", 0);
            } else {
                record.put("isjoin", 1);
            }
            record.remove("status");
            record.remove("city");
            record.remove("province");
            record.remove("type");
        }
    }

    @Override
    public void queryBefore(AopContext ac) {
        setParma("userId", ac);

    }
}
