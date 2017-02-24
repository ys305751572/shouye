package com.smallchill.api.function.modal;

import org.beetl.sql.core.annotatoin.Table;
import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;

import javax.persistence.Column;

/**
 * Generated by Blade.
 * 2017-02-06 11:21:13
 */
@Table(name = "tb_article_shielding")
@BindID(name = "id")
@SuppressWarnings("serial")
public class Shielding extends BaseModel {

    @Column(name = "id")
    private Integer id;
    @Column(name = "from_id")
    private Integer fromId;
    @Column(name = "type")
    private Integer type;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "create_time")
    private Long createTime;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}