package com.smallchill.api.function.meta.intercept;

import com.smallchill.core.aop.AopContext;
import com.smallchill.core.meta.ApiQueryIntercept;

/**
 * 圈脉搜索组织拦截器
 * Created by yesong on 2016/11/18 0018.
 */
public class SearchGroupIntercept extends ApiQueryIntercept {

    @Override
    public void queryBefore(AopContext ac) {
        setParma("userId", ac);
        String keyWord = this.getRecord().getStr("keyWord");
        StringBuffer whereBuffer = new StringBuffer();
        whereBuffer.append(" and (")
                .append(" province_city like concat('%','").append(keyWord).append(",'%' or")
                .append(" name like concat('%','").append(keyWord).append(",'%' or")
                .append(" targat like concat('%','").append(keyWord).append(",'%' or")
                .append(" title1 like concat('%','").append(keyWord).append(",'%' or")
                .append(" content1 like concat('%','").append(keyWord).append(",'%' or")
                .append(" title2 like concat('%','").append(keyWord).append(",'%' or")
                .append(" content2 like concat('%','").append(keyWord).append(",'%' or")
                .append(" title3 like concat('%','").append(keyWord).append(",'%' or")
                .append(" content3 like concat('%','").append(keyWord).append(",'%')");
        ac.setCondition(whereBuffer.toString());
    }
}
