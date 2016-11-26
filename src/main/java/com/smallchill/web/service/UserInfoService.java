package com.smallchill.web.service;

import com.smallchill.api.common.exception.UserExitsException;
import com.smallchill.api.function.modal.vo.UserVo;
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

    UserVo findUserInfoDetail(Integer userId, Integer toUserId, Integer groupId);

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
     * 组织给用户发送消息
     *
     * @param id       用户ID
     * @param sendNum  发送次数
     * @param sendData 发送日期
     * @param title    标题
     * @param content  内容
     */
    void sendMessage(Integer id, Integer sendNum, Long sendData, String title, String content);

    /**
     * 根据关键字查询用户列表
     *
     * @param userId
     * @param keyWord
     * @return
     */
    List<UserVo> findByKeyWord(Integer userId, String keyWord);

    /**
     * 创建用户好友分组
     *
     * @param userId  用户ID
     * @param name    分组名字
     * @param userIds 加入分组用户ID
     */
    void createGrouping(Integer userId, String name, String userIds);

    /**
     * 加入分组用户
     *
     * @param userId  用户ID
     * @param userIds 加入分组用户ID
     */
    void joinToGrouping(Integer userId, String userIds, Integer groupingId);

    /**
     * 删除用户分组
     *
     * @param userId     用户ID
     * @param groupingId 分组ID
     */
    void deleteGrouping(Integer userId, Integer groupingId);

    /**
     * 查看默认分组（熟人，校友，同组织）
     *
     * @param usereId 用户ID
     * @return result
     */
    List<Record> findDefaultGrouping(Integer usereId);

    /**
     * 查询用户自定义分组
     *
     * @param userId 用户ID
     * @return records
     */
    List<Record> findCustomGrouping(Integer userId);

    /**
     * 查询分组首页
     *
     * @param userId 用户ID
     * @return records
     */
    List<Record> findIndexGrouping(Integer userId);

    /**
     * 用户感兴趣多选
     *
     * @param userId    当前用户ID
     * @param toUserIds 感兴趣用户ID,多个ID用","号分割
     */
    void interest(Integer userId, String toUserIds);

    /**
     * 查看交集
     *
     * @param userId
     * @param toUserId
     * @return
     */
    Record intersection(Integer userId, Integer toUserId);

    /**
     * 查询用户详细信息（带与当前用户关系）
     *
     * @param userId   当前用户ID
     * @param toUserId 目标用户ID
     * @return uservo
     */
    UserVo findUserDetailWithUa(Integer userId, Integer toUserId);
}
