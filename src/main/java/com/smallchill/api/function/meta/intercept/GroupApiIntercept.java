package com.smallchill.api.function.meta.intercept;

import com.smallchill.core.aop.AopContext;
import com.smallchill.core.meta.PageIntercept;
import com.smallchill.core.toolbox.support.BladePage;

import java.util.List;
import java.util.Map;

/**
 * 组织API拦截器
 * Created by yesong on 2016/11/3 0003.
 */
public class GroupApiIntercept extends PageIntercept {

    @Override
    public void queryAfter(AopContext ac) {
        BladePage bladePage = (BladePage) ac.getObject();
        List<Map> list = bladePage.getRows();
        for (Map record : list) {
            if(record.get("status") == null || Integer.parseInt(record.get("status").toString()) != 1) {
                record.put("isjoin", "未加入");
            }
            else {
                record.put("isjoin", "已加入");
            }
            record.remove("status");
            record.remove("city");
            record.remove("province");
            record.remove("type");
        }
    }

    @Override
    public void queryBefore(AopContext ac) {
        ac.setCondition(" and ( userId = #{userId_equal} or userId IS NULL)");
    }
}
