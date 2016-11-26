package com.smallchill.api.function.meta.intercept;

import com.smallchill.core.aop.AopContext;
import com.smallchill.core.meta.ApiQueryIntercept;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.support.BladePage;

import java.util.List;
import java.util.Map;

/**
 * 查询周围用户拦截器
 * Created by yesong on 2016/11/26.
 */
public class AroundUserIntercept extends ApiQueryIntercept {

    private int NOT_FRINED = 2000; // 未结识
    private int FRIEND = 2001; // 已结识
    private int NOT_PROCESS_FROM_USER_ID = 2002; // 显示忽略 结识按钮
    private int NOT_PROCESS_TO_USER_ID = 2003; // 等待对方确认
    private int PASS = 2004;                   // 已忽略
    @Override
    public void queryBefore(AopContext ac) {
        setParma("lat", ac);
        setParma("lon", ac);
        setParma("userId", ac);
        super.queryBefore(ac);
    }

    @Override
    public void queryAfter(AopContext ac) {
        Integer userId = getRecord().getInt("userId");
        BladePage bladePage = (BladePage) ac.getObject();
        List<Record> list = bladePage.getRows();
        for (Map record : list) {
            int type = NOT_FRINED;
            if (record.get("status") == null) {
                type = NOT_FRINED;
            }
            else {
                int status = Integer.parseInt(record.get("status").toString());
                if (status == 1) {
                    type = FRIEND;
                } else if (status == 2) {
                    type = PASS;
                } else if (status == 0) {
                    int fromUserId = Integer.parseInt(record.get("from_user_id").toString());
                    int toUserId = Integer.parseInt(record.get("to_user_id").toString());
                    if (userId == fromUserId) {
                        type = NOT_PROCESS_TO_USER_ID;
                    } else if (userId == toUserId) {
                        type = NOT_PROCESS_FROM_USER_ID;
                    }
                }
            }
            record.put("status", type);
            record.remove("city");
            record.remove("province");
        }
        super.queryAfter(ac);
    }
}
