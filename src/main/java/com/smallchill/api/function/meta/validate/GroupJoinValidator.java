package com.smallchill.api.function.meta.validate;

import com.smallchill.api.common.model.ErrorType;
import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.ApiValidator;

/**
 * 新增组织字段验证
 * Created by yesong on 2016/10/27 0027.
 */
public class GroupJoinValidator extends ApiValidator{

    @Override
    protected void doValidate(Invocation inv) {
        validateRequired("groupId", ErrorType.ERROR_CODE_PARAM_EXCEPTION);
        validateRequired("userId", ErrorType.ERROR_CODE_PARAM_EXCEPTION);
    }
}
