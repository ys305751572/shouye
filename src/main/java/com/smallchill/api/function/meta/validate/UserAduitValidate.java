package com.smallchill.api.function.meta.validate;

import com.smallchill.api.common.model.ErrorType;
import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.ApiValidator;
import org.apache.commons.lang3.StringUtils;

/**
 * 用户好友请求参数验证
 * Created by yesong on 2016/11/8 0008.
 */
public class UserAduitValidate extends ApiValidator{

    @Override
    protected void doValidate(Invocation inv) {
        validateRequired("fromUserId", ErrorType.ERROR_CODE_PARAM_EXCEPTION);
        validateRequired("toUserId", ErrorType.ERROR_CODE_PARAM_EXCEPTION);
        validateType("status", ErrorType.ERROR_CODE_PARAM_EXCEPTION);
        validateTwoNotEqual("fromUserId", "toUserId", ErrorType.ERROR_CODE_APP_MYSELF);
    }

    private void validateType(String staus, ErrorType errorCodeParamException) {
        String status1 = request.getParameter(staus);
        if(StringUtils.isNotBlank(status1)) {
            int status2 = Integer.parseInt(status1);
            if (status2 > 2) {
                this.addError(errorCodeParamException);
            }
        }
        else {
            this.addError(errorCodeParamException);
        }
    }
}
