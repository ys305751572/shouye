package com.smallchill.api.function.meta.validate;

import com.smallchill.api.common.model.ErrorType;
import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.ApiValidator;
import org.apache.commons.lang3.StringUtils;

/**
 * 用户好友请求参数验证
 * Created by yesong on 2016/11/8 0008.
 */
public class UserApprovalValidate extends ApiValidator{

    @Override
    protected void doValidate(Invocation inv) {
        validateRequired("fromUserId", ErrorType.ERROR_CODE_PARAM_EXCEPTION);
        validateRequired("toUserId", ErrorType.ERROR_CODE_PARAM_EXCEPTION);
        validateType("type", ErrorType.ERROR_CODE_PARAM_EXCEPTION);
        validateTwoNotEqual("fromUserId", "toUserId", ErrorType.ERROR_CODE_APP_MYSELF);
        validateGroupIdAndIntroduceUserId(ErrorType.ERROR_CODE_PARAM_EXCEPTION);
    }

    private void validateGroupIdAndIntroduceUserId(ErrorType errorCodeParamException) {
        String _groupId = request.getParameter("groupId");
        String _introduceUserId = request.getParameter("introduceUserId");
        if(StringUtils.isNotBlank(_groupId) && StringUtils.isNotBlank(_introduceUserId)) {
            addError(errorCodeParamException);
        }
    }


    private void validateType(String type, ErrorType errorCodeParamException) {
        String type1 = request.getParameter(type);
        if(StringUtils.isNotBlank(type1)) {
            int typenum = Integer.parseInt(type1);
            if (typenum > 2) {
                this.addError(errorCodeParamException);
            }
        }
        else {
            this.addError(errorCodeParamException);
        }
    }
}
