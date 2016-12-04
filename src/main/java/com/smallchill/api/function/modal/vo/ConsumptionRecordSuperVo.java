package com.smallchill.api.function.modal.vo;

import com.smallchill.api.common.model.BaseVo;

import java.util.List;

/**
 *
 * Created by yesong on 2016/12/4 0004.
 */
public class ConsumptionRecordSuperVo extends BaseVo{

    private Double money;
    private List<ConsumptionRecordVo> list;

    public List<ConsumptionRecordVo> getList() {
        return list;
    }

    public void setList(List<ConsumptionRecordVo> list) {
        this.list = list;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }
}
