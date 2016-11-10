package com.smallchill.api.function.modal.vo;

import com.smallchill.core.base.model.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * 手页-首页
 * Created by yesong on 2016/11/1 0001.
 */
public class ShouPageVo extends BaseModel {

    private Integer newCount = 0;

    private Integer interestCount = 0;

    private Integer interestedCount = 0;

    private Integer acquaintancesCount = 0;

    private Integer groupCount = 0;

    private List<UserVo> list;

    public Integer getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(Integer groupCount) {
        this.groupCount = groupCount;
    }

    public Integer getNewCount() {
        return newCount;
    }

    public void setNewCount(Integer newCount) {
        this.newCount = newCount;
    }

    public Integer getInterestCount() {
        return interestCount;
    }

    public void setInterestCount(Integer interestCount) {
        this.interestCount = interestCount;
    }

    public Integer getInterestedCount() {
        return interestedCount;
    }

    public void setInterestedCount(Integer interestedCount) {
        this.interestedCount = interestedCount;
    }

    public Integer getAcquaintancesCount() {
        return acquaintancesCount;
    }

    public void setAcquaintancesCount(Integer acquaintancesCount) {
        this.acquaintancesCount = acquaintancesCount;
    }

    public List<UserVo> getList() {
        return list;
    }

    public void setList(List<UserVo> list) {
        this.list = list;
    }
}
