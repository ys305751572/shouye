package com.smallchill.api.function.modal.vo;

import com.smallchill.api.common.model.BaseVo;

import java.util.List;

/**
 * 活动VO
 * Created by yesong on 2017/2/10 0010.
 */
public class ActivityVo extends BaseVo {

    private Integer activityId;
    private String cover;
    private String title;
    private String content;
    private Integer groupId;
    private String groupname;
    private Long createTime;
    private Long startTime;
    private Long endTime;
    private Long applyEndTime;
    private String address;
    private Integer ceiling;
    private Integer limit;
    private Integer cost;
    private Integer btnStatus;

    private Integer count;
    private List<UserVo> userVos;

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
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

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Long getApplyEndTime() {
        return applyEndTime;
    }

    public void setApplyEndTime(Long applyEndTime) {
        this.applyEndTime = applyEndTime;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCeiling() {
        return ceiling;
    }

    public void setCeiling(Integer ceiling) {
        this.ceiling = ceiling;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public List<UserVo> getUserVos() {
        return userVos;
    }

    public void setUserVos(List<UserVo> userVos) {
        this.userVos = userVos;
    }

    public Integer getBtnStatus() {
        return btnStatus;
    }

    public void setBtnStatus(Integer btnStatus) {
        this.btnStatus = btnStatus;
    }

    public Integer getCount() {
        return count;
    }
}
