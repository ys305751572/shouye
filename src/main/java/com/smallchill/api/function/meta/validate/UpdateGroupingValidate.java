package com.smallchill.api.function.meta.validate;

import com.smallchill.api.common.model.ErrorType;
import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.ApiValidator;

/**
 * 修改分组信息API参数验证
 * Created by yesong on 2016/11/28 0028.
 */
public class UpdateGroupingValidate extends ApiValidator {

    @Override
    protected void doValidate(Invocation inv) {
        validateRequired("userId", ErrorType.ERROR_CODE_PARAM_EXCEPTION);
        validateRequired("name", ErrorType.ERROR_CODE_PARAM_EXCEPTION);
        validateRequired("groupingId", ErrorType.ERROR_CODE_PARAM_EXCEPTION);
    }
}
