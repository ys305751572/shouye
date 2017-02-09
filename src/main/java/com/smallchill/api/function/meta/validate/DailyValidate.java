package com.smallchill.api.function.meta.validate;

import com.smallchill.api.common.model.ErrorType;
import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.ApiValidator;

/**
 * Created by Administrator on 2017/2/9 0009.
 */
public class DailyValidate extends ApiValidator{
    @Override
    protected void doValidate(Invocation inv) {
        validateRequired("dailyId", ErrorType.ERROR_CODE_PARAM_EXCEPTION);
    }
}
