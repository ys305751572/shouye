package com.smallchill.web.service;

import com.smallchill.api.common.exception.UserExitsException;
import com.smallchill.core.base.service.IService;
import com.smallchill.core.toolbox.Record;
import com.smallchill.web.model.UserInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Generated by Blade.
 * 2016-10-18 09:47:31
 */
public interface UserInfoService extends IService<UserInfo> {
    UserInfo updateUserInfo(UserInfo userInfo, HttpServletRequest request) throws UserExitsException;

    UserInfo findByUserId(int userId);

    List<Record> findByParmas(Record params);

    Record findUserInfoDetail(Integer userId);

    Record findUserInfoDetail(Integer userId, Integer toUserId);

    String findUsernameByUserId(Integer userId);

    /**
     * 改变用户状态
     *
     * @param id         用户id
     * @param bannedTime 时间
     * @param content    原因
     * @param status     状态
     */
    void banned(Integer id, Integer bannedTime, String content, Integer status);


    /**
     * 发送消息
     *
     * @param request  request
     * @param id       用户ID
     * @param send     发送类型
     * @param sendTime 定时发送
     * @param title    标题
     * @param content  内容
     */
    void sendMessage(HttpServletRequest request, String id, Integer send, String sendTime, String title, String content);
}
