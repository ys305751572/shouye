package com.smallchill.api.function.meta.validate;

import com.smallchill.api.common.model.ErrorType;
import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.ApiValidator;

/**
 * 组织列表参数验证
 * Created by yesong on 2016/11/3 0003.
 */
public class GroupPageValidator extends ApiValidator {
    @Override
    protected void doValidate(Invocation inv) {
        validateRequired("userId", ErrorType.ERROR_CODE_PARAM_EXCEPTION);
    }
}
