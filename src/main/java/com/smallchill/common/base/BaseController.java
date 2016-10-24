package com.smallchill.common.base;

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
}
