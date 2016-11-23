package com.smallchill.api.function.meta.validate;

import com.smallchill.api.common.model.ErrorType;
import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.ApiValidator;

/**
 *
 * Created by yesong on 2016/11/23 0023.
 */
public class IntersectionValidate extends ApiValidator{
    @Override
    protected void doValidate(Invocation inv) {
        validateRequired("userId", ErrorType.ERROR_CODE_PARAM_EXCEPTION);
        validateRequired("toUserId", ErrorType.ERROR_CODE_PARAM_EXCEPTION);
    }
}
