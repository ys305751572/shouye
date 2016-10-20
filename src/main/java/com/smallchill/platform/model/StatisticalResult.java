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
        String str = "";
        for (Record record : list) {
            str += "数量:" + record.get("order_num") + "==名字:" +record.get("col_name") + "==所占百分比:" + record.get("per") ;
        }
        return "total:" + all + ",集合:" + str;
    }
}
