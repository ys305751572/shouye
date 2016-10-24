package com.smallchill.api.function.controller;

import com.smallchill.api.common.model.Result;
import com.smallchill.api.system.service.VcodeService;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.toolbox.kit.JsonKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户登录controller
 * Created by yesong on 2016/10/24 0024.
 */
@Controller
@RequestMapping(value = "/api/index")
public class LoginController extends BaseController {

    @Autowired
    private VcodeService vcodeService;

    /**
     * 登录
     * @return result
     */
    @RequestMapping(value = "/loginCheck")
    public String loginCheck(String mobile, String code) {

        if(!vcodeService.validate(mobile, code)) {
            return JsonKit.toJson(Result.fail());
        }


        return null;
    }
}
