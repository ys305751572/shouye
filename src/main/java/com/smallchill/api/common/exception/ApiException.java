package com.smallchill.api.common.exception;

import com.smallchill.api.common.model.ErrorType;

/**
 * API接口异常
 * Created by yesong on 2017/2/14 0014.
 */
public class ApiException extends Exception{

    private ErrorType errorType;

    public ApiException(ErrorType errorType) {
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }
}
