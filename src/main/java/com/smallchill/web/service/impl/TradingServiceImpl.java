package com.smallchill.web.service.impl;

import com.smallchill.common.pay.util.TimeUtil;
import com.smallchill.core.base.service.BaseService;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.grid.JqGrid;
import com.smallchill.core.toolbox.kit.DateKit;
import com.smallchill.core.toolbox.kit.DateTimeKit;
import com.smallchill.core.toolbox.support.BladePage;
import com.smallchill.web.model.Trading;
import com.smallchill.web.service.GroupLoadService;
import com.smallchill.web.service.TradingService;
import org.beetl.sql.core.kit.CaseInsensitiveHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 * Created by Administrator on 2016/11/15.
 */
@Service
public class TradingServiceImpl extends BaseService<Trading> implements TradingService {

    @Override
    public List yearMonth(String date,Integer type){
        String query = "%m";
        String where = "%Y";
        if(type==2){
            //根据月查询
            query = "%d";
            where = "%Y-%m";
        }
        String sql = "SELECT\n" +
                "  SUM(order_amount) AS amount,\n" +
                "  DATE_FORMAT(FROM_UNIXTIME(create_time / 1000),'"+query+"') t\n" +
                "FROM\n" +
                "  tb_order\n" +
                "  WHERE flow = '1' \n" +
                "AND DATE_FORMAT(FROM_UNIXTIME(create_time / 1000),'"+where+"') = #{date}\n" +
                "AND status='1' OR status='3' \n" +
                "  GROUP BY t";

        List<Record> list = Db.init().selectList(sql,Record.create().set("date",date));
        List list1 = new ArrayList();

        Map<String,Object> map = new TreeMap<>();
        //年
        if(type==1){
            for (int i=1;i<13;i++) {
                String y="";
                if(i<10){
                    y = "0";
                }
                map.put(y+i,0.00);
            }
        }
        //月
        if(type==2){
            Date d = DateTimeKit.parse(date,"yyyy-MM");
            int monthDay = DateKit.getMonthDay(d);
            for (int i=1;i<monthDay;i++) {
                String y="";
                if(i<10){
                    y = "0";
                }
                map.put(y+i,0.00);
            }
        }

        for(Record record :list){
                String time = (String) record.get("t");
                Double amount = (Double) record.get("amount");
                if(map.get(time)!=null){
                    map.put(time,amount);
                }else {
                    map.put(time,0.00);
                }
            }

        for(Map.Entry<String, Object> entry:map.entrySet()){
            list1.add(entry);
//            System.out.println(entry.getKey()+"--->"+entry.getValue());
        }
        return list1;
    }

    @Override
    public List day(Long date){
        // 7 14 30
        Long start = TimeUtil.getTimesnight() - (date * 86400000L);
        Long end = TimeUtil.getTimesnight();

        String sql = "SELECT\n" +
                "  SUM(order_amount) AS amount,\n" +
                "  DATE_FORMAT(FROM_UNIXTIME(create_time / 1000),'%m-%d') t\n" +
                "FROM\n" +
                "  tb_order\n" +
                "  WHERE flow = '1' \n" +
                "AND create_time>=#{start} AND create_time< #{end}\n" +
                "AND status='1' OR status='3' \n" +
                "  GROUP BY t";
        List<Record> list = Db.init().selectList(sql,Record.create().set("start",start).set("end",end));

        Date beginDate = DateTimeKit.date(start);
        Date endDate = DateTimeKit.date(TimeUtil.getTimesmorning());
        List<Date> dateList = getDatesBetweenTwoDate(beginDate,endDate);

        Map<String,Object> map = new TreeMap<>();
        List list1 = new ArrayList();

        for(Date d : dateList){
            String formatDate = DateTimeKit.format(d,"MM-dd");
            map.put(formatDate,0.00);
        }

        for(Record record :list){
            String time = (String) record.get("t");
            Double amount = (Double) record.get("amount");
            if(map.get(time)!=null){
                map.put(time,amount);
            }else {
                map.put(time,0.00);
            }
        }

        for(Map.Entry<String, Object> entry:map.entrySet()){
            list1.add(entry);
//            System.out.println(entry.getKey()+"--->"+entry.getValue());
        }

        return list1 ;
    }

    public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate) {
        List<Date> lDate = new ArrayList<Date>();
        lDate.add(beginDate);// 把开始时间加入集合
        Calendar cal = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        cal.setTime(beginDate);
        boolean bContinue = true;
        while (bContinue) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cal.add(Calendar.DAY_OF_MONTH, 1);
            // 测试此日期是否在指定日期之后
            if (endDate.after(cal.getTime())) {
                lDate.add(cal.getTime());
            } else {
                break;
            }
        }
        lDate.add(endDate);// 把结束时间加入集合
        return lDate;
    }

}
