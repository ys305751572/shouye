package com.smallchill.api.function.meta.validate;

import com.smallchill.api.common.model.ErrorType;
import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.ApiValidator;

/**
 * 杂志ID参数验证
 * Created by yesong on 2017/2/9 0009.
 */
public class MagazineIdValidate extends ApiValidator{
    @Override
    protected void doValidate(Invocation inv) {
        validateRequired("magazineId", ErrorType.ERROR_CODE_PARAM_EXCEPTION);
    }
}
