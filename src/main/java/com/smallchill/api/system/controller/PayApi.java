package com.smallchill.api.system.controller;

import com.smallchill.common.base.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 支付API
 * Created by yesong on 2016/10/27 0027.
 */
@RequestMapping(value = "/api/pay")
public class PayApi extends BaseController {

    /**
     * 获取支付信息
     * @return result
     */
    @RequestMapping(value = "/info")
    @ResponseBody
    public String payInfo() {

        return null;
    }
}
