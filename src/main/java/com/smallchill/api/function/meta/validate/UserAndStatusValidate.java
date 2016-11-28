package com.smallchill.api.function.meta.validate;

import com.smallchill.api.common.model.ErrorType;
import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.ApiValidator;

/**
 *
 * Created by yesong on 2016/11/28 0028.
 */
public class UserAndStatusValidate extends ApiValidator{

    @Override
    protected void doValidate(Invocation inv) {
        validateRequired("userId", ErrorType.ERROR_CODE_PARAM_EXCEPTION);
        validateRequired("status", ErrorType.ERROR_CODE_PARAM_EXCEPTION);
    }

    private  void validateStatus(ErrorType errorType) {
        int status = Integer.parseInt(request.getParameter("status"));
        if (status != 1 && status != 2) {
            this.addError(errorType);
        }
    }
}
