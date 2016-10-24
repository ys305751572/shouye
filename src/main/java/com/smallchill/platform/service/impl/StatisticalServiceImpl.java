package com.smallchill.platform.service.impl;

import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.kit.DateKit;
import com.smallchill.core.toolbox.kit.NumberKit;
import com.smallchill.platform.model.Conditions;
import com.smallchill.platform.model.StatisticalResult;
import org.apache.commons.lang3.StringUtils;
import org.beetl.sql.core.annotatoin.Table;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 统计查询
 * Created by yesong on 2016/10/19 0019.
 */
@Service
public class StatisticalServiceImpl {

    /**
     * 分类统计
     */
    public StatisticalResult classificationStatistics(Class<?> clazz, Conditions conditions) {
        Table table = clazz.getAnnotation(Table.class);
        String tableName = null;
        if (table != null) {
            tableName = table.name();
        }
        StringBuilder sql = new StringBuilder("select ");
        sql.append("count(*) as order_num");
        if (StringUtils.isNotBlank(conditions.getGroupByName())) {
            sql.append(",");
            sql.append(conditions.getGroupByName());

            if (StringUtils.isBlank(conditions.getForeignCol())) {
                sql.append(" AS col_name");
            }
        }
        if (StringUtils.isNotBlank(conditions.getForeignCol())) {
            sql.append(",ft.");
            sql.append(conditions.getForeignCol());
            sql.append(" as col_name");
        }
        sql.append(" from ");
        sql.append(tableName);
        sql.append(" tn ");
        if (conditions.getForeignClass() != null) {
            String foreigonTableName = conditions.getForeignClass().getAnnotation(Table.class).name();
            sql.append(" join ");
            sql.append(foreigonTableName);
            sql.append(" ft on ft.");
            String fCol = "id";
//            if(StringUtils.isNotBlank(conditions.getForeignCol())) {
//                fCol = conditions.getForeignCol();
//            }
            sql.append(fCol);
            sql.append(" = ");
            sql.append(" tn.");
            sql.append(conditions.getGroupByName());
        }
        if (StringUtils.isNotBlank(conditions.getGroupByName())) {
            sql.append(" group by ");
            sql.append(conditions.getGroupByName());
        }
        if (StringUtils.isNotBlank(conditions.getSort())) {
            sql.append(" order by ");
            sql.append(" order_num ");

            if (StringUtils.isNotBlank(conditions.getSort())) {
                sql.append(conditions.getSort());
            }
        }
        if (conditions.getLimit() != null) {
            sql.append(" limit ");
            sql.append(conditions.getLimit());
        }
        Db db = Db.init();
        List<Record> list = db.selectList(sql.toString());
        return processingResult(list, conditions);
    }

    /**
     * 时间段统计
     */
    public StatisticalResult timeStatistics(Class<?> clazz, Conditions conditions) {
        Table table = clazz.getAnnotation(Table.class);
        String tableName = null;
        if (table != null) {
            tableName = table.name();
        }
        StringBuilder sql = new StringBuilder("select ");
        sql.append(" count(*) order_num");
        if (StringUtils.isNotBlank(conditions.getTimeType())) {
            String timeType = "";
            switch (conditions.getTimeType()) {
                case "month":
                    timeType = "%Y-%m";
                    break;
                case "day":
                    timeType = "%Y-%m-%d";
            }
            sql.append(" ,DATE_FORMAT(");
            if (conditions.isTimeTypeIsLong()) {
                sql.append(" FROM_UNIXTIME(");
            }
            sql.append("t.");
            sql.append(conditions.getGroupByName());
            if (conditions.isTimeTypeIsLong()) {
                sql.append(",\'%Y-%M-%D\')");
            }
            sql.append(",\'");
            sql.append(timeType);
            sql.append("\') as col_name");
        }
        if (StringUtils.isNotBlank(conditions.getSecondCol())) {
            sql.append(" , ");
            sql.append(conditions.getSecondCol());
            sql.append(" AS second_col");
        }
        sql.append(" from ");
        sql.append(tableName);
        sql.append(" as t");
        sql.append(" WHERE ");
        sql.append(" DATE_FORMAT( ");
        if (conditions.isTimeTypeIsLong()) {
            sql.append(" FROM_UNIXTIME(t.");
        }
        sql.append(conditions.getGroupByName());
        if (conditions.isTimeTypeIsLong()) {
            sql.append(",\'%Y-%M-%D\')");
        }
        sql.append(",\'%Y\')");
        sql.append(" = \'");
        sql.append(conditions.getYear());
        sql.append("\'");

        // 根据时间范围筛选
        if (conditions.getBeforDays() != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            long endTime = calendar.getTimeInMillis();
            Date endTimeDATE = calendar.getTime();

            calendar.add(Calendar.DAY_OF_MONTH, -1 * conditions.getBeforDays());
            long startTime = calendar.getTimeInMillis();
            Date startTimeDate = calendar.getTime();
            sql.append(" and t.");
            sql.append(conditions.getGroupByName());
            sql.append(" < ");
            sql.append(conditions.isTimeTypeIsLong() ? endTime : "\'" + DateKit.format(endTimeDATE, "yyyy-MM-dd HH:mm:ss") + "\'");
            sql.append(" and t.");
            sql.append(conditions.getGroupByName());
            sql.append(" > ");
            sql.append(conditions.isTimeTypeIsLong() ? startTime : "\'" + DateKit.format(startTimeDate, "yyyy-MM-dd HH:mm:ss") + "\'");
        }

        sql.append(" group by col_name");
        if (StringUtils.isNotBlank(conditions.getSecondCol())) {
            sql.append(" ,second_col");
        }
        sql.append(" order by col_name ASC");

        Db db = Db.init();
        List<Record> list = db.selectList(sql.toString());
        return processingResult(list, conditions);
    }

    /**
     * 处理查询结果
     *
     * @param list
     */
    private StatisticalResult processingResult(List<Record> list, Conditions conditions) {
        double totalRecord = 0;
        for (Record record : list) {
            totalRecord += Double.parseDouble(record.get("order_num").toString());
        }
        for (Record record : list) {
            double data = Double.parseDouble(record.get("order_num").toString());
            double per = data / totalRecord * 100;
            record.put("per", NumberKit.format(per) + "%");
        }
        StatisticalResult result = new StatisticalResult();
        result.setAll(totalRecord);
        if(conditions.getBeforDays() != null || "MONTH".endsWith(conditions.getTimeType())) {
            result.setList(fill(list,conditions));
            return result;
        }
        result.setList(list);
        return result;
    }

    public List<Record> fill(List<Record> list, Conditions conditions) {
        List<Record> newList = new ArrayList<>();
        if (conditions.getBeforDays() != null) {
            // 填充中间没有的天数
            if (conditions.getBeforDays() <= 7) {
                // 7天以内的用 N天前
                // 填充中间没有的天数
                for (int i = 1; i <= 7; i++) {
                    String dayFormat = DateKit.getBeforeDay(-1 * i);
                    if (!isExist(list,newList,String.valueOf(dayFormat),i)) {
                        newList.add(createNewRecord(i + "天前"));
                    }
                }
            } else {
                // 7天已上的用 日期
                // 填充中间没有的天数
                int beforeDays = conditions.getBeforDays();
                for (int i = 1;i <= beforeDays; i++) {
                    String dayFormat = DateKit.getBeforeDay(-1 * i);
                    if (!isExist(list, newList,String.valueOf(dayFormat),null)) {
                        newList.add(createNewRecord(dayFormat));
                    }

                }
            }
        }
        if ("MONTH".endsWith(conditions.getTimeType())) {
            // 填充中间没有的月份
            for (int i = 1; i <= 12; i++) {
                String dayFormat = DateKit.getBeforMonth(i);
                if (!isExist(list,newList, String.valueOf(dayFormat),null)) {
                    newList.add(createNewRecord( i + "月份"));
                }
            }
        }
        return newList;
    }

    public Record createNewRecord(String colName) {
        Record record = Record.create();
        record.put("col_name",colName);
        record.put("order_num","0");
        record.put("per","0%");
        return record;
    }

    public boolean isExist(List<Record> list,List<Record> newList, String key,Integer index) {
        for (Record record : list) {
            if (key.equals(record.get("col_name"))) {
                if(index != null) {
                    record.set("col_name",index + "天前");
                }
                newList.add(record);
                return true;
            }
        }
        return false;
    }
}