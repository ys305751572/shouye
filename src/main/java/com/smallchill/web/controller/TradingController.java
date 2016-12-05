package com.smallchill.web.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.common.pay.util.TimeUtil;
import com.smallchill.core.interfaces.ILoader;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.grid.JqGrid;
import com.smallchill.core.toolbox.kit.CacheKit;
import com.smallchill.core.toolbox.support.BladePage;
import com.smallchill.web.meta.intercept.GroupIntercept;
import com.smallchill.web.meta.intercept.OrderIntercept;
import com.smallchill.web.model.GroupExtend;
import com.smallchill.web.model.Trading;
import com.smallchill.web.service.TradingService;
import org.beetl.sql.core.kit.CaseInsensitiveHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 交易流水-交易
 * Created by Administrator on 2016/11/15.
 */
@Controller
@RequestMapping(value = "/trading")
public class TradingController extends BaseController {
    private static String CODE = "trading";
    private static String LIST_SOURCE = "Order.groupOrderList";
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

    @RequestMapping(value = "/weekRecord")
    @ResponseBody
    public Map<String, Double> weekRecord(){
        try{

            return tradingService.week();

        }catch (RuntimeException e){
            e.printStackTrace();
        }
        return null;
    }




}
