package com.smallchill.api.function.meta.validate;

import com.smallchill.api.common.model.ErrorType;
import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.ApiValidator;

/**
 * Created by Administrator on 2017/2/14 0014.
 */
public class ActivityApplyValidate extends ApiValidator{
    @Override
    protected void doValidate(Invocation inv) {
        validateRequired("userId", ErrorType.ERROR_CODE_PARAM_EXCEPTION);
        validateRequired("activityId", ErrorType.ERROR_CODE_PARAM_EXCEPTION);
        validateRequired("isPay", ErrorType.ERROR_CODE_PARAM_EXCEPTION);
        validateRequired("money", ErrorType.ERROR_CODE_PARAM_EXCEPTION);
    }
}
