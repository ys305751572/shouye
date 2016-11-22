package com.smallchill.api.function.meta.intercept;

import com.smallchill.api.function.meta.other.Convert;
import com.smallchill.core.aop.AopContext;
import com.smallchill.core.meta.ApiQueryIntercept;
import com.smallchill.core.meta.PageIntercept;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.support.BladePage;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 用户列表查询api拦截器
 * Created by yesong on 2016/11/3 0003.
 */
public class UserApiIntercept extends ApiQueryIntercept {

    @Override
    public void queryBefore(AopContext ac) {
        setParma("userId", ac);
        setParma("groupId", ac);
        setParma("history", ac);
        setParma("domain", ac);
        ac.setCondition(" and userId IS NOT NULL");
    }

    @Override
    public void queryAfter(AopContext ac) {
        BladePage bladePage = (BladePage) ac.getObject();
        List<Map> list = bladePage.getRows();
        Integer userId = Integer.parseInt(getRecord().get("userId").toString());

        for (Map record : list) {
            record.put("status", 1);
            Object statusObj = record.get("status");
            if (statusObj != null && (Integer.parseInt(statusObj.toString()) == 0)) {
                int fromUserId = (int) record.get("from_user_id");
                if (fromUserId == userId) {
                    record.put("status", 4);
                }
            } else if (statusObj != null && (Integer.parseInt(statusObj.toString()) == 1)) {
                record.put("status", 2);
            }

            Object istatus = record.get("istatus");
            if (istatus != null && Integer.parseInt(istatus.toString()) == 0) {
                record.put("status", 3);
            }
            String keyWord = record.get("keyWord").toString();
            if (StringUtils.isNotBlank(keyWord)) {
                keyWord = keyWord.replaceAll("\\|", "\\/");
                record.put("keyWord", keyWord);
            }
            record.put("domain", record.get("domain"));
            record.remove("province");
            record.remove("city");
            record.remove("groupId");

            record.remove("from_user_id");
            record.remove("to_user_id");
            record.remove("istatus");
        }
    }
}
