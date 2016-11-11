package com.smallchill.web.model;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 *
 * Created by shilong on 2016/11/11.
 */
@Table(name = "tb_feekback")
@BindID(name = "id")
@SuppressWarnings("serial")
public class Feekback extends BaseModel {

    @Column(name = "id")
    private Integer id;

    @Column(name = "rno")
    private String rno = "";

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "content")
    private String content = "";

    @Column(name = "reply")
    private String reply = "";

    @Column(name = "delegate")
    private Integer delegate;

    @Column(name = "admin_id")
    private Integer adminId;

    @Column(name = "status")
    private Integer status;

    @Column(name = "create_time")
    private Long createTime;

    @Column(name = "update_time")
    private Long updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRno() {
        return rno;
    }

    public void setRno(String rno) {
        this.rno = rno;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public Integer getDelegate() {
        return delegate;
    }

    public void setDelegate(Integer delegate) {
        this.delegate = delegate;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
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

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
}
