package com.smallchill.core.meta;

import com.smallchill.api.common.model.Result;
import com.smallchill.core.interfaces.Interceptor;

/**
 * api 拦截器
 * Created by yesong on 2016/10/27 0027.
 */
public abstract class ApiInterceptor implements Interceptor {
    protected Result result = new Result();

    protected Object invoke() {
        return null;
    }
}
