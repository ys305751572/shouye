package com.smallchill.api.system.controller;

import com.smallchill.api.common.model.ErrorType;
import com.smallchill.api.common.model.Result;
import com.smallchill.api.system.service.VcodeService;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.toolbox.LeomanKit;
import com.smallchill.core.toolbox.grid.JqGrid;
import com.smallchill.core.toolbox.kit.JsonKit;
import com.smallchill.core.toolbox.kit.QuickCacheKit;
import com.smallchill.system.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统方法controller
 * Created by yesong on 2016/10/24 0024.
 */
@RequestMapping("/api/common")
@Controller
public class CommonController extends BaseController{

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
//        vcodeService.sendKX(LeomanKit.generateCode(),mobile);
        System.out.println("SendVerificationCode");
        return success();
    }
}
