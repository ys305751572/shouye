package com.smallchill.api.function.modal.vo;

import com.smallchill.api.common.model.BaseVo;
import com.smallchill.core.toolbox.Record;

import java.math.BigDecimal;
import java.util.List;

/**
 * 申请信息
 * Created by yesong on 2016/11/23 0023.
 */
public class GroupApprovalVo extends BaseVo {

    private List<Record> target;
    private BigDecimal money;
    private Integer costType; //  收费方式  1:年费 2:永久
    private Integer costStatus; // 会费状态 1:免费 2:收费

    public Integer getCostStatus() {
        return costStatus;
    }

    public void setCostStatus(Integer costStatus) {
        this.costStatus = costStatus;
    }

    public Integer getCostType() {
        return costType;
    }

    public void setCostType(Integer costType) {
        this.costType = costType;
    }

    public List<Record> getTarget() {
        return target;
    }

    public void setTarget(List<Record> target) {
        this.target = target;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
