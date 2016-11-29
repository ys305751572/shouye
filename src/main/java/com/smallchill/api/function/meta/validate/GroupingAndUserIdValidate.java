package com.smallchill.api.function.meta.validate;

import com.smallchill.api.common.model.ErrorType;
import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.ApiValidator;

/**
 * 分组用户列表API参数验证
 * Created by yesong on 2016/11/29 0029.
 */
public class GroupingAndUserIdValidate extends ApiValidator{
    @Override
    protected void doValidate(Invocation inv) {
        validateRequired("groupingId", ErrorType.ERROR_CODE_PARAM_EXCEPTION);
        validateRequired("userId", ErrorType.ERROR_CODE_PARAM_EXCEPTION);
    }
}
