package com.smallchill.api.function.meta.validate;

import com.smallchill.api.common.model.ErrorType;
import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.ApiValidator;

/**
 * 用户-感兴趣参数验证
 * Created by yesong on 2016/11/11 0011.
 */
public class UserUnInterestValidate extends ApiValidator{
    @Override
    protected void doValidate(Invocation inv) {
        validateRequired("userId", ErrorType.ERROR_CODE_PARAM_EXCEPTION);
        validateRequired("toUserId", ErrorType.ERROR_CODE_PARAM_EXCEPTION);
    }
}
