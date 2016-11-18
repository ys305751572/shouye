package com.smallchill.web.model;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;

/**
 * 交易流水-提现
 * Created by Administrator on 2016/11/15.
 */
@Table(name = "tb_withdrawal")
@BindID(name = "id")
@SuppressWarnings("serial")
public class Withdrawal extends BaseModel {

    @Column(name = "id")
    private Integer id;

    @Column(name = "group_id")
    private Integer groupId;

    //订单号
    @Column(name = "order_no")
    private String orderNo;

    //转账银行
    @Column(name = "bank")
    private String bank;

    //金额
    @Column(name = "amount")
    private Double amount;

    //手续费
    @Column(name = "poundage")
    private Double poundage;

    //1:交易成功 2:处理中 3:提现失败
    @Column(name = "type")
    private Integer type;

    //1:交易成功 2:处理中 3:提现失败
    @Column(name = "create_time")
    private Long createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getPoundage() {
        return poundage;
    }

    public void setPoundage(Double poundage) {
        this.poundage = poundage;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
