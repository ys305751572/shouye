package com.smallchill.api.function.meta.intercept;

import com.smallchill.core.aop.AopContext;
import com.smallchill.core.meta.ApiQueryIntercept;
import com.smallchill.core.toolbox.kit.MapKit;
import com.smallchill.core.toolbox.support.BladePage;
import com.smallchill.web.model.UserInfo;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by yesong on 2016/11/17 0017.
 */
public class GroupApiIntercept2 extends ApiQueryIntercept{

    @Override
    public void queryBefore(AopContext ac) {
        setParma("userId", ac);
        UserInfo userInfo = (UserInfo) this.getRecord().get("userInfo");
        String career = userInfo.getCareer();
        String domain = userInfo.getDomain();
        String professional = userInfo.getProfessional();
        String zy = userInfo.getZy();
        String sc = userInfo.getSc();
        String zy2 = userInfo.getZy2();
        String zl = userInfo.getZl();
        String keyWord = userInfo.getKeyWord();
        List<String> list = new ArrayList<>();
        list.add(career);
        list.add(domain);
        list.add(professional);
        list.add(zy);
        list.add(sc);
        list.add(zy2);
        list.add(zl);
        list.add(keyWord);

        List<String> list2 = new ArrayList<>();
        for (String key : list) {
            if (StringUtils.isNotBlank(key)) {
                String[] keys = key.split("\\|");
                for (String key2 : keys) {
                    if (StringUtils.isNotBlank(key2)) {
                        list2.add(key2.trim());
                    }
                }
            }
        }
        StringBuffer condition = new StringBuffer("and (");
        for (int i=0; i < list2.size(); i++) {
            condition.append(" targat like concat('%','").append(list2.get(i)).append("','%')");
            if (i != (list2.size() - 1)) {
                condition.append(" or ");
            }
        }
        condition.append(")");
        ac.setCondition(condition.toString());
    }

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
            record.put("targat", MapKit.getStr(record, "targat").replace("\\|", "\\/"));
            record.remove("status");
            record.remove("city");
            record.remove("province");
            record.remove("type");
        }
    }
}
