package com.smallchill.api.function.modal.vo;

import com.smallchill.api.common.model.BaseVo;

/**
 * 续费详情vo
 * Created by yesong on 2016/12/26 0026.
 */
public class RenewalInfoVo extends BaseVo{

    private Double money;
    private Long nextEndTime;

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Long getNextEndTime() {
        return nextEndTime;
    }

    public void setNextEndTime(Long nextEndTime) {
        this.nextEndTime = nextEndTime;
    }
}
