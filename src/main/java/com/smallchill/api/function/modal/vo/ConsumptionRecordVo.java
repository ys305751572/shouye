package com.smallchill.api.function.modal.vo;

import com.smallchill.api.common.model.BaseVo;

/**
 * 消费记录
 * Created by yesong on 2016/12/4 0004.
 */
public class ConsumptionRecordVo extends BaseVo{

    /**
     * id
     */
    private Integer id;
    /**
     * 标题
     */
    private String title;
    /**
     * 购买数量
     */
    private Integer counts;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 组织名称
     */
    private String groupName;

    /**
     * 金额
     */
    private Double money;

    /**
     * 订单状态
     * 1:未审核 2:失败 4:退款成功 5:退款失败
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Long createTime;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCounts() {
        return counts;
    }

    public void setCounts(Integer counts) {
        this.counts = counts;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
