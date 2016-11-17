package com.smallchill.web.model;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 交易流水-交易
 * Created by Administrator on 2016/11/15.
 */
@Table(name = "tb_trading")
@BindID(name = "id")
@SuppressWarnings("serial")
public class Trading extends BaseModel{

    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    //订单号
    @Column(name = "order_no")
    private String orderNo;

    //金额
    @Column(name = "amount")
    private Double amount;

    //数量
    @Column(name = "num")
    private Integer num;

    //1:会员费 2:报名费 3:熟人上限 4:感兴趣上限(对人)
    @Column(name = "type")
    private Integer type;

    //1:支出 2:收入
    @Column(name = "flow")
    private Integer flow;

    //1:微信 2:支付宝
    @Column(name = "platform")
    private Integer platform;

    //状态 1:交易成功 2:审核中 3:进行中 4:扣款失败 5:退款成功 6:等待退款 7:退款失败
    @Column(name = "status")
    private Integer status;

    @Column(name = "date_time")
    private String dateTime;

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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getFlow() {
        return flow;
    }

    public void setFlow(Integer flow) {
        this.flow = flow;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
