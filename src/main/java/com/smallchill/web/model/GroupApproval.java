package com.smallchill.web.model;

import com.smallchill.core.toolbox.kit.DateTimeKit;
import org.beetl.sql.core.annotatoin.Table;
import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;

import javax.persistence.Column;

/**
 *
 * Generated by Blade.
 * 2016-10-27 11:45:35
 */
@Table(name = "tb_group_approval")
@BindID(name = "id")
@SuppressWarnings("serial")
public class GroupApproval extends BaseModel {

    @Column(name = "id")
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "group_id")
    private Integer groupId;
    @Column(name = "status")
    private Integer status = 1;
    @Column(name = "paied")
    private Integer paied = 1;
    @Column(name = "match_type")
    private String matchType;
    @Column(name = "validate_info")
    private String validateInfo;
    @Column(name = "through_time")
    private Long throughTime;
    @Column(name = "create_time")
    private Long createTime = DateTimeKit.nowLong();

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getPaied() {
        return paied;
    }

    public void setPaied(Integer paied) {
        this.paied = paied;
    }

    public String getValidateInfo() {
        return validateInfo;
    }

    public void setValidateInfo(String validateInfo) {
        this.validateInfo = validateInfo;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getThroughTime() {
        return throughTime;
    }

    public void setThroughTime(Long throughTime) {
        this.throughTime = throughTime;
    }
}
