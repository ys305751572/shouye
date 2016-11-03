package com.smallchill.api.function.meta.intercept;

import com.smallchill.core.aop.AopContext;
import com.smallchill.core.meta.PageIntercept;

/**
 * 用户列表查询api拦截器
 * Created by yesong on 2016/11/3 0003.
 */
public class UserApiIntercept extends PageIntercept{

    @Override
    public void queryAfter(AopContext ac) {
        super.queryAfter(ac);
    }
}
