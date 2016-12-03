package com.smallchill.api.function.modal;

import org.beetl.sql.core.annotatoin.Table;
import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;

import javax.persistence.Column;

/**
 * Generated by Blade.
 * 2016-11-04 17:35:21
 */
@Table(name = "tb_user_last_read_time")
@BindID(name = "id")
@SuppressWarnings("serial")
public class UserLastReadTime extends BaseModel {

    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "new_time")
    private Long newTime = 0L;

    @Column(name = "intereste")
    private Long intereste = 0L;

    @Column(name = "interested")
    private Long interested = 0L;

    @Column(name = "acquaintances")
    private Long acquaintances = 0L;

    @Column(name = "group")
    private Long group = 0L;

    @Column(name = "feekback")
    private Long feekback = 0L;

    public Long getFeekback() {
        return feekback;
    }

    public void setFeekback(Long feekback) {
        this.feekback = feekback;
    }

    public Long getGroup() {
        return group;
    }

    public void setGroup(Long group) {
        this.group = group;
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

    public Long getNewTime() {
        return newTime;
    }

    public void setNewTime(Long newTime) {
        this.newTime = newTime;
    }

    public Long getIntereste() {
        return intereste;
    }

    public void setIntereste(Long intereste) {
        this.intereste = intereste;
    }

    public Long getInterested() {
        return interested;
    }

    public void setInterested(Long interested) {
        this.interested = interested;
    }

    public Long getAcquaintances() {
        return acquaintances;
    }

    public void setAcquaintances(Long acquaintances) {
        this.acquaintances = acquaintances;
    }
}
