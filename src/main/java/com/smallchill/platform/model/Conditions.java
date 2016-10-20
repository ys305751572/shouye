package com.smallchill.platform.model;

import com.smallchill.core.toolbox.kit.DateKit;

/**
 * 查询条件
 * Created by yesong on 2016/10/19 0019.
 */
public class Conditions {

    private String groupByName;

    // 分类统计
    private Integer limit;
    private String sort;
    private Class<?> foreignClass = null;
    private String foreignCol = null;

    // 分段统计
    private String timeType = "day";
    private String year = DateKit.getYear();
    private boolean timeTypeIsLong = true;
    private String secondCol = "";
    private Integer beforDays;

    public Integer getBeforDays() {
        return beforDays;
    }

    public Conditions setBeforDays(Integer beforDays) {
        this.beforDays = beforDays;
        return this;
    }

    public String getSecondCol() {
        return secondCol;
    }

    public Conditions setSecondCol(String secondCol) {
        this.secondCol = secondCol;
        return this;
    }

    public boolean isTimeTypeIsLong() {
        return timeTypeIsLong;
    }

    public Conditions setTimeTypeIsLong(boolean timeTypeIsLong) {
        this.timeTypeIsLong = timeTypeIsLong;
        return this;
    }

    public String getYear() {
        return year;
    }

    public Conditions setYear(String year) {
        this.year = year;
        return this;
    }

    public String getTimeType() {
        return timeType;
    }

    public Conditions setTimeType(Time time) {
        switch (time) {
            case DAY:
                this.timeType = "day";
                break;
            case MOMTH:
                this.timeType = "month";
                break;
        }
        return this;
    }

    public Class<?> getForeignClass() {
        return foreignClass;
    }

    public Conditions setForeignClass(Class<?> foreignClass) {
        this.foreignClass = foreignClass;
        return this;
    }

    public String getForeignCol() {
        return foreignCol;
    }

    public Conditions setForeignCol(String foreignCol) {
        this.foreignCol = foreignCol;
        return this;
    }

    public String getGroupByName() {
        return groupByName;
    }

    public Integer getLimit() {
        return limit;
    }

    public String getSort() {
        return sort;
    }

    public static Conditions create() {
        return new Conditions();
    }
    public Conditions setLimit(Integer limit) {
        this.limit = limit;
        return this;
    }
    public Conditions setGroupByName(String groupByName) {
        this.groupByName = groupByName;
        return this;
    }

    public Conditions setSort(Sort sort) {
        if(sort.name().endsWith("ASC")) {
            this.sort = "ASC";
        }
        else {
            this.sort = "DESC";
        }
        return this;
    }

    public enum Sort{
        ASC,
        DESC
    }

    public enum Time {
        YEAR,
        MOMTH,
        DAY
    }
}
