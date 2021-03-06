package com.smallchill.api.function.meta.validate;

import com.smallchill.api.common.model.ErrorType;
import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.ApiValidator;

/**
 *
 * Created by yesong on 2016/11/8 0008.
 */
public class UserBlankValidate extends ApiValidator{

    @Override
    protected void doValidate(Invocation inv) {
        validateRequired("fromUserId", ErrorType.ERROR_CODE_PARAM_EXCEPTION);
        validateRequired("toUserIds", ErrorType.ERROR_CODE_PARAM_EXCEPTION);
        validateContain("fromUserId", "toUserIds", ",",ErrorType.ERROR_CODE_APP_MYSELF2);
    }
}
