package com.smallchill.common.base;

import com.smallchill.api.common.model.ErrorType;
import com.smallchill.api.common.model.Result;
import com.smallchill.core.base.controller.BladeController;
import com.smallchill.core.toolbox.kit.JsonKit;

/**
 * 用于拓展controller类
 */
public class BaseController extends BladeController {

    /**
     * 返回结果转换为json
     * @param result 结果
     * @return string类型的结果
     */
    public String toJson(Result result) {
        return JsonKit.toJson(result);
    }

    /**
     * 返回不带信息的成功相应信息
     * @return result
     */
    public String success() {
        return toJson(Result.success());
    }

    /**
     * 返货带结果的
     * @param data 结果
     * @return result
     */
    public String success(Object data) {
        return toJson(Result.success(data));
    }

    /**
     * 返回默认错误信息
     * @return
     */
    public String fail() {
        return toJson(Result.fail());
    }

    /**
     * 返回自定义错误信息
     * @param errorType 错误码
     * @return result
     */
    public String fail(ErrorType errorType) {
        return toJson(Result.fail(errorType));
    }
}
