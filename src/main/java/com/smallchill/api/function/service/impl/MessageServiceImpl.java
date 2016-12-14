package com.smallchill.api.function.service.impl;

import com.smallchill.api.function.meta.consts.MessageConts;
import com.smallchill.api.function.modal.Message;
import com.smallchill.api.function.service.MessageService;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.kit.DateTimeKit;
import com.smallchill.web.model.Group;
import com.smallchill.web.service.GroupService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.smallchill.core.base.service.BaseService;

import java.util.List;

/**
 * 消息service
 * Generated by yesong.
 * 2016-10-27 17:24:13
 */
@Service
public class MessageServiceImpl extends BaseService<Message> implements MessageService, MessageConts {

    @Autowired
    private GroupService groupService;

    /**
     * 发送用户审核信息-同意
     *
     * @param grouoId 组织ID
     * @param userId  用户ID
     */
    @Override
    public void sendMsgForUserAuditAgree(int grouoId, int userId) {
        this.sendMsgForUserAudit(grouoId, userId, MSG_USER_AUDIT, MSG_USER_AUDIT_CONTENT_AGREE);
    }

    /**
     * 发送用户审核信息-拒绝
     *
     * @param grouoId 组织ID
     * @param userId  用户ID
     */
    @Override
    public void sendMsgForUserAuditRefuse(int grouoId, int userId, String orderNo) {
        String msg = MSG_USER_AUDIT_CONTENT_NOT_FREE_REFUSE;
        if (StringUtils.isNotBlank(orderNo)) {
            msg = msg.replace("s", orderNo);
        } else {
            msg = MSG_USER_AUDIT_CONTENT_FREE_REFUSE;
        }
        this.sendMsgForUserAudit(grouoId, userId, MSG_USER_AUDIT, msg);
    }

    /**
     * 发送用户审核信息-拉黑
     *
     * @param grouoId 组织ID
     * @param userId  用户ID
     */
    @Override
    public void sendMsgForUserAuditBlank(int grouoId, int userId, String orderNo) {
        sendMsgForUserAuditRefuse(grouoId, userId, orderNo);
    }

    /**
     * 发送用户审核信息-移除黑名单
     *
     * @param groupId 组织ID
     * @param userId  用户ID
     */
    @Deprecated
    @Override
    public void sendMsgForUserAuditUnBlank(int groupId, int userId) {
    }

    /**
     * 组织-用户审核
     *
     * @param groupId 组织ID
     * @param userId  用户ID
     */
    private void sendMsgForUserAudit(int groupId, int userId, String targat, String content) {
        Message msg = new Message();
        msg.setFromId(groupId);
        msg.setToId(userId);
        Group group = groupService.findById(groupId);
        msg.setTitle(targat + group.getName());
        msg.setLabel(targat.replace("[","").replace("]",""));
        msg.setContent(content);
        msg.setCreateTime(DateTimeKit.nowLong());
        this.save(msg);
    }

    /**
     * 向组织发送审核消息
     *
     * @param groupId 组织ID
     */
    @Override
    public void sendmsgForGroupAudit(int groupId) {

    }

    /**
     * 组织发送消息给单个用户
     *
     * @param id       用户ID
     * @param sendMass  发送次数
     * @param sendData 发送日期
     * @param title    标题
     * @param content  内容
     */
    @Override
    public void sendMsgFromGroupToUser(Integer id,Integer sendMass ,Long sendData ,String title, String content) {
        Group group = (Group) ShiroKit.getSession().getAttribute("groupAdmin");
        Message msg = new Message();
        msg.setFromId(group.getId());
        msg.setToId(id);
        msg.setTitle(MSG_GROUP + group.getName());
        msg.setLabel(MSG_GROUP.replace("[","").replace("]",""));
        msg.setContent(content);
        msg.setReceiveType(1);
        msg.setSendMass(sendMass);
        msg.setSendDate(sendData);
        msg.setCreateTime(DateTimeKit.nowLong());

        this.save(msg);
    }

    /**
     * 组织发送消息给多个用户
     *
     * @param ids       用户ID
     * @param sendMass  发送次数
     * @param sendData 发送日期
     * @param title    标题
     * @param content  内容
     */
    @Override
    public void sendMsgFromGroupToUser(List<Integer> ids,Integer sendMass ,Long sendData ,String title, String content) {
        Group group = (Group) ShiroKit.getSession().getAttribute("groupAdmin");
        Message msg = new Message();
        msg.setFromId(group.getId());
        msg.setTitle(MSG_GROUP + group.getName());
        msg.setLabel(MSG_GROUP.replace("[","").replace("]",""));
        msg.setContent(content);
        msg.setReceiveType(1);
        msg.setSendMass(sendMass);
        msg.setSendDate(sendData);
        msg.setCreateTime(DateTimeKit.nowLong());

        for (Integer userId : ids) {
            msg.setToId(userId);
            this.save(msg);
        }
    }

    /**
     * 后台向单个用户发送系统消息
     *
     * @param userId  用户ID
     * @param title   标题
     * @param content 内容
     */
    @Override
    public void sendMsgFromSysToUser(int userId,Integer sendType, Long sendTime, String title, String content) {
        Message msg = new Message();
        msg.setFromId(-1);
        msg.setTitle(MSG_SYS + title);
        msg.setLabel(MSG_SYS.replace("[","").replace("]",""));
        msg.setContent(content);
        msg.setToId(userId);
        msg.setCreateTime(DateTimeKit.nowLong());
        this.save(msg);
    }

    /**
     * 后台向多个用户发送系统消息
     *
     * @param userIds 用户IDs
     * @param title   标题
     * @param content 内容
     */
    @Override
    public void sendMsgFromSysToUsers(List<Integer> userIds, String title, String content) {
        Message msg = new Message();
        msg.setFromId(-1);
        msg.setTitle(MSG_SYS + title);
        msg.setLabel(MSG_SYS.replace("[","").replace("]",""));
        msg.setContent(content);
        msg.setCreateTime(DateTimeKit.nowLong());
        for (Integer userId : userIds) {
            msg.setToId(userId);
            this.save(msg);
        }
    }

    /**
     * 查询用户消息
     *
     * @param userId 用户ID
     * @return 消息集合
     */
    @Override
    public List<Message> findByUserId(Integer userId) {
        return this.findBy("to_id = #{userId} order by create_time desc", Record.create().set("userId", userId));
    }
}
