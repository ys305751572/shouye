package com.smallchill.api.function.meta.validate;

import com.smallchill.api.common.model.ErrorType;
import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.ApiValidator;

/**
 * 附近用户API参数验证
 * Created by yesong on 2016/11/26.
 */
public class AroundUserValidate extends ApiValidator{
    @Override
    protected void doValidate(Invocation inv) {
        validateRequired("userId", ErrorType.ERROR_CODE_PARAM_EXCEPTION);
        validateRequired("lat", ErrorType.ERROR_CODE_PARAM_EXCEPTION);
        validateRequired("lon", ErrorType.ERROR_CODE_PARAM_EXCEPTION);
    }
}
