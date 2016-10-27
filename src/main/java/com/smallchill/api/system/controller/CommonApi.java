package com.smallchill.api.system.controller;

import com.smallchill.api.system.service.VcodeService;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.toolbox.LeomanKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 系统方法controller
 * Created by yesong on 2016/10/24 0024.
 */
@RequestMapping("/api/common")
@Controller
public class CommonApi extends BaseController{

    @Autowired
    private VcodeService vcodeService;

    /**
     * 发送验证码
     * @param mobile 手机号
     * @return result
     */
    @RequestMapping(value = "/code/send")
    @ResponseBody
    public String SendVerificationCode(String mobile) {
        vcodeService.sendKX(LeomanKit.generateCode(),mobile);
        System.out.println("SendVerificationCode");
        return success();
    }
}
