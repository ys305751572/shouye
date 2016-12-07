package com.smallchill.web.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.common.pay.util.TimeUtil;
import com.smallchill.core.interfaces.ILoader;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.ajax.AjaxResult;
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

import java.util.ArrayList;
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

    @RequestMapping(value = "/yearMonth")
    @ResponseBody
    public List yearMonth(String date,Integer type){
        List list = new ArrayList();
        try{
            list =  tradingService.yearMonth(date,type);
        }catch (RuntimeException e){
            e.printStackTrace();
        }
        return list;
    }

    @RequestMapping(value = "/day")
    @ResponseBody
    public List day(Long date){
        List list = new ArrayList();
        try{
            list =  tradingService.day(date);
        }catch (RuntimeException e){
            e.printStackTrace();
        }
        return list;
    }

    @RequestMapping(value = "/scope")
    @ResponseBody
    public AjaxResult scope(Integer type){
        StringBuilder sb = new StringBuilder();
        try{
            if(type==1){    //年
                String sql = "SELECT\n" +
                        "  DATE_FORMAT(FROM_UNIXTIME(create_time / 1000),'%Y') t\n" +
                        "FROM\n" +
                        "  tb_order\n" +
                        "  WHERE flow = '1' \n" +
                        "  GROUP BY t \n" +
                        "  ORDER BY t";
                List<Record> list = Db.init().selectList(sql);
                sb.append("<select class=\"form-control\" style=\"margin:0 10px 0 -3px;cursor:pointer;width:auto;\" id=\"scope_list\">");
                sb.append("<option value></option>");
                for(Record record:list){
                    sb.append("<option value=\"" +  record.get("t") + "\">" +  record.get("t") + "</option>");
                }
                sb.append("</select>");
            }
            else if(type==2){    //月
                sb.append("<select class=\"form-control\" style=\"margin:0 10px 0 -3px;cursor:pointer;width:auto;\" id=\"scope_list\">");
                sb.append("<option value></option>");
                for (int i=1;i<13;i++) {
                    sb.append("<option value=\"" + i + "\">" + i + "</option>");
                }
                sb.append("</select>");
            }
            else if(type==3){    //日
                sb.append("<select class=\"form-control\" style=\"margin:0 10px 0 -3px;cursor:pointer;width:auto;\" id=\"scope_list\">");
                sb.append("<option value></option>");
                sb.append("<option value=\"7\">7天</option>");
                sb.append("<option value=\"14\">14天</option>");
                sb.append("<option value=\"30\">30天</option>");
                sb.append("</select>");
            }
            else { //null
                sb.append("<select class=\"form-control\" style=\"margin:0 10px 0 -3px;cursor:pointer;width:auto;\" id=\"scope_list\">");
                sb.append("<option value></option>");
                sb.append("</select>");
            }

        }catch (RuntimeException e){
            e.printStackTrace();
        }
        return json(sb.toString());

    }


}
