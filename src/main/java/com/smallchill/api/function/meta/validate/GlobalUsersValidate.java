package com.smallchill.api.function.meta.validate;

import com.smallchill.api.common.model.ErrorType;
import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.ApiValidator;

/**
 * 全局搜素用户api参数验证
 * Created by yesong on 2016/11/3 0003.
 */
public class GlobalUsersValidate extends ApiValidator{

    @Override
    protected void doValidate(Invocation inv) {
        validateRequired("userId_notequal", ErrorType.ERROR_CODE_APP_USERINBLANK);
    }
}
