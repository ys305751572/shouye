package com.smallchill.api.function.controller;

import com.smallchill.api.common.model.ErrorType;
import com.smallchill.api.system.service.VcodeService;
import com.smallchill.common.base.BaseController;
import com.smallchill.platform.service.UserLoginService;
import com.smallchill.web.model.UserInfo;
import com.smallchill.web.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 注册
 * Created by yesong on 2016/10/24 0024.
 */
@RequestMapping(value = "/api/index")
@Controller
public class RegisterApi extends BaseController {

    @Autowired
    private VcodeService vcodeService;

    @Autowired
    private UserInfoService userInfoService;

    private UserLoginService userLoginService;

    /**
     * 注释：这里一般来说验证成功之后会插入一条数据到userlogin，userinfo表
     * 但是此项目的注册流程实在是太长了，为了避免填好信息后发现验证码是错误的，所以这里先验证验证码是否正确
     *
     * @param mobile 手机
     * @param code   验证码
     * @return result
     */
    @RequestMapping(value = "/register")
    @ResponseBody
    public String register(String mobile, String code) {

        if (vcodeService.validate(mobile, code)) {
            return success();
        } else if (userLoginService.userIfExtis(mobile)) {
            return fail(ErrorType.ERROR_CODE_USERHASEXTIS);
        } else {
            return fail(ErrorType.ERROR_CODE_VALIDATECODE_FAIL);
        }
    }

    /**
     * 更新用户信息
     *
     * @param userInfo 用户信息
     * @return result
     */
    @RequestMapping(value = "/userinfo/upload")
    @ResponseBody
    public String uploadUserinfo(UserInfo userInfo) {
        UserInfo _userInfo;
        try {
            _userInfo = userInfoService.updateUserInfo(userInfo, this.getRequest());
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
        return success(_userInfo);
    }
}
