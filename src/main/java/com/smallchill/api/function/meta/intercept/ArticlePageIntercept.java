package com.smallchill.api.function.meta.intercept;

import com.smallchill.core.aop.AopContext;
import com.smallchill.core.meta.ApiQueryIntercept;

/**
 * 文章列表查询拦截器
 * Created by yesong on 2017/2/16 0016.
 */
public class ArticlePageIntercept extends ApiQueryIntercept{

    @Override
    public void queryBefore(AopContext ac) {
        setParma("userId", ac);
    }
}
