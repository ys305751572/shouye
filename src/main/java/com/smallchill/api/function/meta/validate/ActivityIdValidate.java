package com.smallchill.api.function.meta.validate;

import com.smallchill.api.common.model.ErrorType;
import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.ApiValidator;

/**
 * 活动ID参数验证
 * Created by yesong on 2017/2/14 0014.
 */
public class ActivityIdValidate extends ApiValidator{
    @Override
    protected void doValidate(Invocation inv) {
        validateRequired("activityId", ErrorType.ERROR_CODE_PARAM_EXCEPTION);
    }
}
