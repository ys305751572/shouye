package com.smallchill.web.model;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;
import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;

import javax.persistence.Column;

/**
 * 订单
 * Created by shilong
 * on 2016/11/28.
 */
@Table(name = "tb_order")
@BindID(name = "id")
@SuppressWarnings("serial")
public class Order extends BaseModel {

    @Column(name = "id")
    private Integer id;
    @Column(name = "group_id")
    private Integer groupId;
    @Column(name = "user_id")
    private Integer userId;
    //订单编号
    @Column(name = "order_no")
    private String orderNo;
    @Column(name = "ga_id")
    private Integer gaId;
    //订单金额
    @Column(name = "order_amount")
    private Double orderAmount;
    //订单类型 1:入会费 2:年续费
    @Column(name = "order_type")
    private Integer orderType;
    @Column(name = "counts")
    private Integer counts;
    //订单状态: 1:未审核 2:失败 3:交易成功 4:退款成功 5:退款失败
    @Column(name = "status")
    private Integer status;
    @Column(name = "create_time")
    private Long createTime;
    @Column(name = "flow")
    private Integer flow;
    @Column(name = "platform")
    private Integer platform;

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

    public Integer getCounts() {
        return counts;
    }

    public void setCounts(Integer counts) {
        this.counts = counts;
    }

    public Integer getGaId() {
        return gaId;
    }

    public void setGaId(Integer gaId) {
        this.gaId = gaId;
    }

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

    public Double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
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
}
