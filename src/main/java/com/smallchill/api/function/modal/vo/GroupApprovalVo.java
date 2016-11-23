package com.smallchill.api.function.modal.vo;

import com.smallchill.api.common.model.BaseVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 申请信息
 * Created by yesong on 2016/11/23 0023.
 */
public class GroupApprovalVo extends BaseVo{

    private List<String> target;
    private BigDecimal money;

    public List<String> getTarget() {
        return target;
    }

    public void setTarget(List<String> target) {
        this.target = target;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
