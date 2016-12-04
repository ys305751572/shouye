package com.smallchill.web.service.impl;

import com.smallchill.api.function.meta.consts.StatusConst;
import com.smallchill.core.base.service.BaseService;
import com.smallchill.core.toolbox.Record;
import com.smallchill.web.model.Order;
import com.smallchill.web.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator
 * on 2016/11/28.
 */
@Service
public class OrderServiceImpl extends BaseService<Order> implements OrderService, StatusConst {

    /**
     * 根据订单号查询订单信息
     * @param orderNo 订单号
     * @return order
     */
    @Override
    public Order findByOrderNo(String orderNo) {
        return this.findFirstBy("order_no = #{orderNo}", Record.create().set("orderNo", orderNo));
    }

    @Override
    public void setOrderSuccess(Order order) {
        order.setStatus(ORDER_STATUS_SUCCESS);
        this.update(order);
    }
}
