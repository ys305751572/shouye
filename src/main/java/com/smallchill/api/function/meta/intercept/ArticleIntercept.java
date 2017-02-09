package com.smallchill.api.function.meta.intercept;

import com.smallchill.core.aop.AopContext;
import com.smallchill.core.meta.ApiQueryIntercept;

/**
 * 文章查询拦截器
 * Created by yesong on 2017/2/9 0009.
 */
public class ArticleIntercept extends ApiQueryIntercept {

    @Override
    public void queryBefore(AopContext ac) {
        setParma("userId", ac);
        int isInterest = this.getRecord().getInt("isInterest");
        if (isInterest != 0) {
            ac.setCondition(" AND is_intereste = " + isInterest);
        }
    }
}
