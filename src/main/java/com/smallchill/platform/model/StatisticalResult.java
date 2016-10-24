package com.smallchill.platform.model;

import com.smallchill.core.toolbox.Record;

import java.util.List;

/**
 * 统计结果
 * Created by yesong on 2016/10/19 0019.
 */
public class StatisticalResult {

    private Double all;
    private List<Record> list;

    public Double getAll() {
        return all;
    }

    public void setAll(Double all) {
        this.all = all;
    }

    public List<Record> getList() {
        return list;
    }

    public void setList(List<Record> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        System.out.println("总计:" + all);
        for (Record record : list) {
            System.out.println(record.get("order_num") + "|" +record.get("col_name") + "|" + record.get("per") );
        }
        System.out.println();
        return "";
    }
}
