package com.smallchill.api.function.meta.intercept;

import com.smallchill.api.function.meta.other.Convert;
import com.smallchill.core.aop.AopContext;
import com.smallchill.core.meta.ApiQueryIntercept;
import com.smallchill.core.toolbox.kit.MapKit;
import com.smallchill.core.toolbox.support.BladePage;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 报名用户
 * Created by yesong on 2017/2/13 0013.
 */
public class ApplyUsersIntercept extends ApiQueryIntercept {

    @Override
    public void queryBefore(AopContext ac) {
        setParma("userId", ac);
        setParma("activityId", ac);
        ac.setCondition(" and userId IS NOT NULL ");
    }

    @Override
    public void queryAfter(AopContext ac) {
        BladePage bladePage = (BladePage) ac.getObject();
        List<Map> list = bladePage.getRows();
        Integer userId = Integer.parseInt(getRecord().get("userId").toString());

        for (Map record : list) {
            Object istatus = record.get("istatus");
            int type;
            if (istatus != null && Integer.parseInt(istatus.toString()) == 0) {
                type = Convert.INTEREST;
            } else {
                type = Convert.NOT_FRINED;
            }
            Object statusObj = record.get("status");
            if (statusObj == null) {
                type = Convert.NOT_FRINED;
                record.put("username", Convert.hiddenRealUsername(MapKit.getStr(record, "username")));
            }
            if (statusObj != null && (Integer.parseInt(statusObj.toString()) == 1)) {
                int fromUserId = (int) record.get("from_user_id");
                if (fromUserId == userId) {
                    type = Convert.NOT_PROCESS_TO_USER_ID;
                } else {
                    type = Convert.NOT_PROCESS_FROM_USER_ID;
                }
                if (MapKit.getInt(record, "type") != 2) {
                    record.put("username", Convert.hiddenRealUsername(MapKit.getStr(record, "username")));
                }
            } else if (statusObj != null && (Integer.parseInt(statusObj.toString()) == 2)) {
                type = Convert.FRIEND;
            }
            if (type == Convert.INTEREST || type == Convert.NOT_FRINED) {
                record.put("username", Convert.hiddenRealUsername(MapKit.getStr(record, "username")));
            }
            record.put("status", type);
            String organization = "";
            if (record.get("org_is_open") != null && Integer.parseInt(record.get("org_is_open").toString()) == 1) {
                organization = (String) record.get("organization");
            }
            record.put("organization", organization);
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