package com.smallchill.web.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.web.service.TradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 交易流水-交易
 * Created by Administrator on 2016/11/15.
 */
@Controller
@RequestMapping(value = "/trading")
public class TradingController extends BaseController {
    private static String CODE = "trading";
    private static String PERFIX = "tb_trading";
    private static String LIST_SOURCE = "Trading.list";
    private static String BASE_PATH = "/web/trading/";

    @Autowired
    TradingService tradingService;

    @RequestMapping(KEY_MAIN)
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "trading_list.html";
    }

    @ResponseBody
    @RequestMapping(KEY_LIST)
    public Object list() {
        Object grid = paginate(LIST_SOURCE);
        return grid;
    }

    @RequestMapping(value = "/trend_chart")
    public String trendChart(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "trend_chart.html";
    }



}
