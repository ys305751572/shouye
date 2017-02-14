package com.smallchill.api.function.meta.intercept;

import com.smallchill.core.aop.AopContext;
import com.smallchill.core.meta.ApiQueryIntercept;
import com.smallchill.core.toolbox.grid.JqGrid;
import com.smallchill.core.toolbox.support.BladePage;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 活动查询拦截器
 * Created by yesong on 2017/2/13 0013.
 */
public class ActivityIntercept extends ApiQueryIntercept{

    @Override
    public void queryBefore(AopContext ac) {
        setParma("time", ac);
        setParma("provinceId", ac);
        setParma("cityId", ac);
        setParma("like", ac);
        setParma("maganzineIds", ac);
        setParma("dailyIds", ac);
        setParma("userId", ac);

        String keyWord = getRecord().getStr("keyWord");
        if (StringUtils.isNotBlank(keyWord)) {
            StringBuilder sql = new StringBuilder();
            String[] keyWords = keyWord.split("\\|");
            sql.append("AND (");
            for (int var = 0; var < keyWords.length; var++) {
                sql.append(" title LIKE CONCAT('%', '"+ keyWords[var] +"', '%') ")
                        .append(" OR content LIKE CONCAT('%', '"+ keyWords[var] +"', '%') ");
                if (var != (keyWords.length - 1)) {
                    sql.append(" OR ");
                }
            }
            sql.append(")");
            System.out.println("Condition:" + sql);
            ac.setCondition(sql.toString());
        }
    }

    @Override
    public void queryAfter(AopContext ac) {
        BladePage page = (BladePage) ac.getObject();
        List<Map> list = page.getRows();
        for (Map map : list) {
            map.remove("content");
        }
    }
}
