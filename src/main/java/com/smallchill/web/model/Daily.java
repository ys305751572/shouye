package com.smallchill.web.model;

import org.beetl.sql.core.annotatoin.Table;
import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;

import javax.persistence.Column;

/**
 * Generated by Blade.
 * 2017-01-13 13:52:05
 */
@Table(name = "tb_daily")
@BindID(name = "id")
@SuppressWarnings("serial")
public class Daily extends BaseModel {

    private Integer id;
    @Column(name = "article_id")
    private Integer articleId;
    @Column(name = "group_id")
    private Integer groupId;
    @Column(name = "type")
    private Integer type;
    @Column(name = "status")
    private Integer status;
    @Column(name = "audit_time")
    private Long auditTime;
    @Column(name = "create_time")
    private Long createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Long auditTime) {
        this.auditTime = auditTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
