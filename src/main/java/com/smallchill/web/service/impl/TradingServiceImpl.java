package com.smallchill.web.service.impl;

import com.smallchill.core.base.service.BaseService;
import com.smallchill.web.model.Trading;
import com.smallchill.web.service.GroupLoadService;
import com.smallchill.web.service.TradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Created by Administrator on 2016/11/15.
 */
@Service
public class TradingServiceImpl extends BaseService<Trading> implements TradingService {

    @Autowired
    private TradingService tradingService;
}
