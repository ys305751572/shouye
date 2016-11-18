package com.smallchill.api.function.meta.intercept;

import com.smallchill.core.aop.AopContext;
import com.smallchill.core.meta.ApiQueryIntercept;

/**
 * 圈脉搜索用户拦截器
 * Created by yesong on 2016/11/18 0018.
 */
public class SearchUserIntercept extends ApiQueryIntercept{

    @Override
    public void queryBefore(AopContext ac) {
        setParma("userId", ac);
        String keyWord = this.getRecord().getStr("keyWord");
        StringBuffer whereBuffer = new StringBuffer();
        whereBuffer.append(" and (")
        .append(" province_city like concat('%','").append(keyWord).append(",'%' or")
        .append(" school like concat('%','").append(keyWord).append(",'%' or")
        .append(" career like concat('%','").append(keyWord).append(",'%' or")
        .append(" domain like concat('%','").append(keyWord).append(",'%' or")
        .append(" professional like concat('%','").append(keyWord).append(",'%' or")
        .append(" product_service_name like concat('%','").append(keyWord).append(",'%' or")
        .append(" organization like concat('%','").append(keyWord).append(",'%' or")
        .append(" zy like concat('%','").append(keyWord).append(",'%' or")
        .append(" zl like concat('%','").append(keyWord).append(",'%' or")
        .append(" sc like concat('%','").append(keyWord).append(",'%' or")
        .append(" zy2 like concat('%','").append(keyWord).append(",'%' or")
        .append(" industry_ranking like concat('%','").append(keyWord).append(",'%' or")
        .append(" qualification like concat('%','").append(keyWord).append(",'%' or")
        .append(" key_word like concat('%','").append(keyWord).append(",'%'");
        ac.setCondition(whereBuffer.toString());
        super.queryBefore(ac);
    }
}
