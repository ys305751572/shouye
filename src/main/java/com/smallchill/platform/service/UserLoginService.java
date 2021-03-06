package com.smallchill.platform.service;

import com.smallchill.api.common.exception.UserFreezeException;
import com.smallchill.api.common.exception.UserLockException;
import com.smallchill.api.common.exception.UserNotFoundException;
import com.smallchill.core.base.service.IService;
import com.smallchill.platform.model.UserLogin;
import com.smallchill.web.model.UserInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * Generated by Blade.
 * 2016-10-18 09:41:36
 */
public interface UserLoginService extends IService<UserLogin> {

    UserLogin loginCheck(String mobile) throws UserNotFoundException, UserLockException, UserFreezeException;

    UserInfo afterLoginQuery(UserLogin userLogin, HttpServletRequest request);

    boolean userIfExtis(String mobile);
}
