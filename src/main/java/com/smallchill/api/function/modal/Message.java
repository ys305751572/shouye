package com.smallchill.api.function.modal;

import com.smallchill.api.function.meta.other.MessageAction;
import org.apache.commons.lang3.StringUtils;
import org.beetl.sql.core.annotatoin.Table;
import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;

import javax.persistence.Column;

/**
 * 消息实体类
 * Generated by yesong.
 * 2016-10-27 17:24:13
 */
@Table(name = "tb_message")
@BindID(name = "id")
@SuppressWarnings("serial")
public class Message extends BaseModel {

    @Column(name = "id")
    private Integer id;
    @Column(name = "from_id")
    private Integer fromId;
    @Column(name = "to_id")
    private Integer toId;
    @Column(name = "label")
    private String label = "";
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "replaces")
    private String replaces = "";
    @Column(name = "action1")
    private String action1 = "";
    @Column(name = "action2")
    private String action2 = "";
    @Column(name = "action3")
    private String action3 = "";
    @Column(name = "action4")
    private String action4 = "";

    //接收类型 1:用户 2:组织
    @Column(name = "receive_type")
    private Integer receiveType;

    //发送类型 1:消息发送 2:手机短信 3:滚动公告
    @Column(name = "send_type")
    private Integer sendType;
    //定时发送时间
    @Column(name = "send_time")
    private Long sendTime;

    //组织: 1:单发 2:群发
    @Column(name = "send_mass")
    private Integer sendMass;
    //组织: 发送日期
    @Column(name = "send_date")
    private Long sendDate;

    @Column(name = "create_time")
    private Long createTime;

    public void addMessageAction(MessageAction ma) {
        if(StringUtils.isBlank(action1)) {
            action1 = combinationMa(ma);
        }
        if(StringUtils.isBlank(action2)) {
            action2 = combinationMa(ma);
        }
        if(StringUtils.isBlank(action3)) {
            action3 = combinationMa(ma);
        }
        if(StringUtils.isBlank(action4)) {
            action4 = combinationMa(ma);
        }
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    private String combinationMa(MessageAction ma) {
        return ma.getActionName() + "|" + ma.getActionUrl();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFromId() {
        return fromId;
    }

    public void setFromId(Integer fromId) {
        this.fromId = fromId;
    }

    public Integer getToId() {
        return toId;
    }

    public void setToId(Integer toId) {
        this.toId = toId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReplaces() {
        return replaces;
    }

    public void setReplaces(String replaces) {
        this.replaces = replaces;
    }

    public String getAction1() {
        return action1;
    }

    public void setAction1(String action1) {
        this.action1 = action1;
    }

    public String getAction2() {
        return action2;
    }

    public void setAction2(String action2) {
        this.action2 = action2;
    }

    public String getAction3() {
        return action3;
    }

    public void setAction3(String action3) {
        this.action3 = action3;
    }

    public String getAction4() {
        return action4;
    }

    public void setAction4(String action4) {
        this.action4 = action4;
    }

    public Integer getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(Integer receiveType) {
        this.receiveType = receiveType;
    }

    public Integer getSendType() {
        return sendType;
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }

    public Long getSendTime() {
        return sendTime;
    }

    public void setSendTime(Long sendTime) {
        this.sendTime = sendTime;
    }

    public Long getSendDate() {
        return sendDate;
    }

    public void setSendDate(Long sendDate) {
        this.sendDate = sendDate;
    }

    public Integer getSendMass() {
        return sendMass;
    }

    public void setSendMass(Integer sendMass) {
        this.sendMass = sendMass;
    }
}
