package com.smallchill.api.function.service;

import com.smallchill.api.function.modal.Message;
import com.smallchill.core.base.service.IService;

import java.util.List;

/**
 * 消息service
 * Generated by yesong.
 * 2016-10-27 17:24:13
 */
public interface MessageService extends IService<Message>{

    /**
     * 发送用户审核信息-同意
     * @param grouoId 组织ID
     * @param userId  用户ID
     */
    void sendMsgForUserAuditAgree(int grouoId, int userId);

    /**
     * 发送用户审核信息-拒绝
     * @param grouoId 组织ID
     * @param userId  用户ID
     */
    void sendMsgForUserAuditRefuse(int grouoId, int userId,String orderNo);

    /**
     * 发送用户审核信息-拉黑
     * @param grouoId 组织ID
     * @param userId  用户ID
     */
    void sendMsgForUserAuditBlank(int grouoId, int userId, String orderNo);

    /**
     * 发送用户审核信息-移除黑名单
     * @param groupId 组织ID
     * @param userId  用户ID
     */
    void sendMsgForUserAuditUnBlank(int groupId, int userId);

    /**
     * 向组织发送审核消息
     * @param groupId 组织ID
     */
    void sendmsgForGroupAudit(int groupId);

    /**
     * 组织发送消息给单个用户
     * @param id       用户ID
     * @param sendMass  单/群发
     * @param sendData 发送日期
     * @param title    标题
     * @param content  内容
     */
    void sendMsgFromGroupToUser(Integer id,Integer sendMass ,Long sendData ,String title, String content);

    /**
     * 组织发送消息给多个用户
     * @param ids       用户ID
     * @param sendMass  单/群发
     * @param sendData 发送日期
     * @param title    标题
     * @param content  内容
     */
    void sendMsgFromGroupToUser(List<Integer> ids,Integer sendMass ,Long sendData ,String title, String content);


    /**
     * 后台向单个用户发送系统消息
     * @param userId 用户ID
     * @param sendType 发送类型
     * @param sendTime 发送时间
     * @param title 标题
     * @param content 内容
     */
    void sendMsgFromSysToUser(int userId,Integer sendType, Long sendTime, String title, String content);

    /**
     * 后台向多个用户发送系统消息
     * @param userIds 用户IDs
     * @param title 标题
     * @param content 内容
     */
    void sendMsgFromSysToUsers(List<Integer> userIds, String title, String content);

    /**
     * 查询用户消息
     * @param userId 用户ID
     * @return 消息集合
     */
    List<Message> findByUserId(Integer userId);
}
