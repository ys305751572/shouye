package com.smallchill.api.function.meta.validate;

import com.smallchill.api.common.model.ErrorType;
import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.ApiValidator;

/**
 * 组织ID参数验证
 * Created by yesong on 2016/12/26 0026.
 */
public class GroupIdValidate extends ApiValidator {
    @Override
    protected void doValidate(Invocation inv) {
        validateRequired("groupId", ErrorType.ERROR_CODE_PARAM_EXCEPTION);
    }
}
