package com.smallchill.api.function.meta.validate;

import com.smallchill.api.common.model.ErrorType;
import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.ApiValidator;

/**
 * 字典编码验证
 * Created by yesong on 2016/11/7 0007.
 */
public class CodeValidate extends ApiValidator{
    @Override
    protected void doValidate(Invocation inv) {
        validateRequired("code", ErrorType.ERROR_CODE_PARAM_EXCEPTION);
    }
}
