package com.smallchill.web.model;

import org.beetl.sql.core.annotatoin.Table;
import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;

import javax.persistence.Column;

/**
 * Generated by Blade.
 * 2016-10-28 14:31:09
 */
@Table(name = "tb_user_approval")
@BindID(name = "id")
@SuppressWarnings("serial")
public class UserApproval extends BaseModel {

    @Column(name = "id")
    private Integer id;
    @Column(name = "group_id")
    private Integer groupId;

    @Column(name = "from_user_id")
    private Integer fromUserId;

    @Column(name = "to_user_id")
    private Integer toUserId;

    @Column(name = "introduce_user_id")
    private Integer introduceUserId;

    @Column(name = "validate_info")
    private String validateInfo = "";
    @Column(name = "status")
    private Integer status;
    @Column(name = "type")
    private Integer type;

    @Column(name = "create_time")
    private Long createTime;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

    public Integer getIntroduceUserId() {
        return introduceUserId;
    }

    public void setIntroduceUserId(Integer introduceUserId) {
        this.introduceUserId = introduceUserId;
    }

    public String getValidateInfo() {
        return validateInfo;
    }

    public void setValidateInfo(String validateInfo) {
        this.validateInfo = validateInfo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
