package com.smallchill.api.function.meta.validate;

import com.smallchill.api.common.model.ErrorType;
import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.ApiValidator;

/**
 * 用户ID & 杂志ID 验证条件
 * Created by yesong on 2017/2/8 0008.
 */
public class UserIdAndMagazineIdValidate extends ApiValidator{
    @Override
    protected void doValidate(Invocation inv) {
        validateRequired("userId", ErrorType.ERROR_CODE_PARAM_EXCEPTION);
        validateRequired("magazineId", ErrorType.ERROR_CODE_PARAM_EXCEPTION);
    }
}
