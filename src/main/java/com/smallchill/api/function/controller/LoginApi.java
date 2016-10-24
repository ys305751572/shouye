package com.smallchill.api.function.controller;

import com.smallchill.api.common.exception.UserFreezeException;
import com.smallchill.api.common.exception.UserLockException;
import com.smallchill.api.common.exception.UserNotFoundException;
import com.smallchill.api.common.model.ErrorType;
import com.smallchill.api.common.model.Result;
import com.smallchill.api.system.service.VcodeService;
import com.smallchill.common.base.BaseController;
import com.smallchill.platform.model.UserLogin;
import com.smallchill.platform.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户登录controller
 * Created by yesong on 2016/10/24 0024.
 */
@Controller
@RequestMapping(value = "/api/index")
public class LoginApi extends BaseController {

    @Autowired
    private VcodeService vcodeService;
    @Autowired
    private UserLoginService userLoginService;

    /**
     * 登录
     *
     * @return result
     */
    @RequestMapping(value = "/loginCheck")
    @ResponseBody
    public String loginCheck(String mobile, String code) {

        try {
            UserLogin userLogin = userLoginService.loginCheck(mobile);
            userLoginService.afterLoginQuery(userLogin, this.getRequest());
        } catch (UserNotFoundException e) {
            return toJson(Result.fail(ErrorType.ERROR_CODE_USERNOTFOUND));
        } catch (UserFreezeException e) {
            return toJson(Result.fail(ErrorType.ERROR_CODE_USERHASFREEZE));
        } catch (UserLockException e) {
            return toJson(Result.fail(ErrorType.ERROR_CODE_USERHASLOCK));
        }
        if (!vcodeService.validate(mobile, code)) {
            return toJson(Result.fail());
        }
        return null;
    }
}
