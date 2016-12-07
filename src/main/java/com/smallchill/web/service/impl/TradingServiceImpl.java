package com.smallchill.web.service.impl;

import com.smallchill.common.pay.util.TimeUtil;
import com.smallchill.core.base.service.BaseService;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.grid.JqGrid;
import com.smallchill.core.toolbox.support.BladePage;
import com.smallchill.web.model.Trading;
import com.smallchill.web.service.GroupLoadService;
import com.smallchill.web.service.TradingService;
import org.beetl.sql.core.kit.CaseInsensitiveHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by Administrator on 2016/11/15.
 */
@Service
public class TradingServiceImpl extends BaseService<Trading> implements TradingService {

    @Override
    public Map<String,Double> week() {
        Long start = TimeUtil.getTimesnight() - 7L * 86400000L;
        Long end = TimeUtil.getTimesnight();
        String sql = "SELECT id AS id, date_time AS dataTime, create_time AS createTime, SUM(amount) AS amount FROM tb_trading WHERE flow = '2' AND create_time>=#{start} AND create_time<#{end} GROUP BY date_time ORDER BY create_time";

//        Record record = Record.create();
//        record.put("start", start);
//        record.put("end", end);
//        List<Trading> tradingList = find(sql, record);

        Map<String, Object> map = new HashMap<>();
        map.put("start", start);
        map.put("end" , end);
        BladePage page = Db.init().paginate(sql, Map.class, map, 1, 10);
        List<CaseInsensitiveHashMap> list =  page.getRows();
        Map<String,Double> doubleMap = new HashMap<>();
        for(CaseInsensitiveHashMap l : list){
            Double amount = (Double) l.get("amount");
            String dataTime = (String) l.get("dataTime");
            doubleMap.put(dataTime,amount);
        }
        return doubleMap;

    }

    @Override
    public List yearMonth(String date,Integer type){
        String query = "%m";
        String where = "%Y";
        if(type==1){
            //根据月查询
            query = "%d";
            where = "%m";
        }
        String sql = "SELECT\n" +
                "  SUM(order_amount) ,\n" +
                "  DATE_FORMAT(FROM_UNIXTIME(create_time / 1000),'"+query+"') t\n" +
                "FROM\n" +
                "  tb_order\n" +
                "  WHERE flow = '1' \n" +
                "AND DATE_FORMAT(FROM_UNIXTIME(create_time / 1000),'"+where+"') = #{date}\n" +
                "AND status='1' OR status='3' \n" +
                "  GROUP BY t";

        List list = Db.init().selectList(sql,Record.create().set("date",date));
        return list;
    }

    @Override
    public List day(Long date){
        // 7 14 30
        Long start = TimeUtil.getTimesnight() - (date * 86400000L);
        Long end = TimeUtil.getTimesnight();
        String sql = "SELECT\n" +
                "  SUM(order_amount) ,\n" +
                "  DATE_FORMAT(FROM_UNIXTIME(create_time / 1000),'%m-%d') t\n" +
                "FROM\n" +
                "  tb_order\n" +
                "  WHERE flow = '1' \n" +
                "AND create_time>=#{start} AND create_time< #{end}\n" +
                "AND status='1' OR status='3' \n" +
                "  GROUP BY t";
        List list = Db.init().selectList(sql,Record.create().set("start",start).set("end",end));
        return list ;
    }


}
