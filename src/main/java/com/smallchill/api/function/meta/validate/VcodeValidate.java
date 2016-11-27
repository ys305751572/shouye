package com.smallchill.api.function.meta.validate;

import com.smallchill.api.common.model.ErrorType;
import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.ApiValidator;

/**
 * 发送验证码API参数验证
 * Created by yesong on 2016/11/27.
 */
public class VcodeValidate extends ApiValidator {
    @Override
    protected void doValidate(Invocation inv) {
        validateRequired("mobile", ErrorType.ERROR_CODE_PARAM_EXCEPTION);
        validateRequired("code", ErrorType.ERROR_CODE_PARAM_EXCEPTION);
    }
}
