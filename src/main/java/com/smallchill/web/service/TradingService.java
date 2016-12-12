package com.smallchill.web.service;


import com.smallchill.core.base.service.IService;
import com.smallchill.web.model.Trading;

import java.util.List;
import java.util.Map;

/**
 *
 * Created by Administrator on 2016/11/15.
 */
public interface TradingService extends IService<Trading> {

    List yearMonth(String date, Integer type);

    List day(Long date);
}
