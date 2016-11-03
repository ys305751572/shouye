package com.smallchill.api.function.meta.validate;

import com.smallchill.api.common.model.ErrorType;
import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.ApiValidator;

/**
 * 组织用户列表请求参数验证
 * Created by yesong on 2016/11/3 0003.
 */
public class GroupUserValidate extends ApiValidator{

    @Override
    protected void doValidate(Invocation inv) {
        validateRequired("userId", ErrorType.ERROR_CODE_PARAM_EXCEPTION);
        validateRequired("groupId", ErrorType.ERROR_CODE_APP_USERINBLANK);
    }
}
