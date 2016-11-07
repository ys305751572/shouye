package com.smallchill.web.model;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;

/**
 * 用户数量字段
 * Created by 史龙 on 2016/11/7.
 */
@Table(name = "tb_userinfo_statistical")
@BindID(name = "id")
@SuppressWarnings("serial")
public class UserinfoStatistical extends BaseModel {

    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    //已加入组织数量
    @Column(name = "organization_num")
    private Integer organizationNum;

    //朋友数量
    @Column(name = "friend_num")
    private Integer friendNum;

    //熟人数量
    @Column(name = "acquaintances_num")
    private Integer acquaintances_num;

    //活动报名数
    @Column(name = "activity_apply_num")
    private Integer activity_apply_num;

    //活动签到数
    @Column(name = "activity_sign_num")
    private Integer activity_sign_num;

    //内容发布数
    @Column(name = "content_num")
    private Integer content_num;

    @Column(name = "create_time")
    private Long createTime;

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

    public Integer getOrganizationNum() {
        return organizationNum;
    }

    public void setOrganizationNum(Integer organizationNum) {
        this.organizationNum = organizationNum;
    }

    public Integer getFriendNum() {
        return friendNum;
    }

    public void setFriendNum(Integer friendNum) {
        this.friendNum = friendNum;
    }

    public Integer getAcquaintances_num() {
        return acquaintances_num;
    }

    public void setAcquaintances_num(Integer acquaintances_num) {
        this.acquaintances_num = acquaintances_num;
    }

    public Integer getActivity_apply_num() {
        return activity_apply_num;
    }

    public void setActivity_apply_num(Integer activity_apply_num) {
        this.activity_apply_num = activity_apply_num;
    }

    public Integer getActivity_sign_num() {
        return activity_sign_num;
    }

    public void setActivity_sign_num(Integer activity_sign_num) {
        this.activity_sign_num = activity_sign_num;
    }

    public Integer getContent_num() {
        return content_num;
    }

    public void setContent_num(Integer content_num) {
        this.content_num = content_num;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
