package com.smallchill.web.service;

import com.smallchill.core.base.service.IService;
import com.smallchill.web.model.Order;

import java.util.List;

/**
 * Created by shilong
 * on 2016/11/28.
 */
public interface OrderService extends IService<Order> {

    Order findByOrderNo(String orderNo);

    Order findByGaId(Integer gaid);

    void setOrderSuccess(Order order);

    void setOrderRefuseSuccess(Order order);

    void setOrderRefuseRefuse(Order order);

    void setOrderAgree(Order order);

    List<Order> findByUserId(Integer userId);



}
