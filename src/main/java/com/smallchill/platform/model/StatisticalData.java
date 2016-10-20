package com.smallchill.platform.model;

/**
 * 统计数据
 * Created by yesong on 2016/10/19 0019.
 */
public class StatisticalData {

    private String name;   // 分类名称
    private Double result; // 统计数量
    private Double per;    // 所在百分比

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public Double getPer() {
        return per;
    }

    public void setPer(Double per) {
        this.per = per;
    }
}
