package com.smallchill.web.service;


import com.smallchill.core.base.service.IService;
import com.smallchill.web.model.Trading;

import java.util.Map;

/**
 *
 * Created by Administrator on 2016/11/15.
 */
public interface TradingService extends IService<Trading> {

    Map<String,Double> week();
}
