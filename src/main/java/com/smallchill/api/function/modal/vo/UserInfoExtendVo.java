package com.smallchill.api.function.modal.vo;

import com.smallchill.api.common.model.BaseVo;

/**
 * 用户拓展信息vo
 * Created by yesong on 2016/12/5 0005.
 */
public class UserInfoExtendVo extends BaseVo {

    // 用户已购买 + 默认 感兴趣人数
    private int userInterestCount;
    // 用户已购买 + 默认 熟人数量
    private int userAcquaintanceCount;
    // 用户剩余 感兴趣人数
    private int userRemainInterestCount;
    // 用户剩余 熟人数量
    private int userRemainAcquaintanceCount;
    // 系统默认 感兴趣人数
    private int systemInterestCount;
    // 系统默认 感兴趣人数单价
    private double systemInterestPrice;
    // 系统默认 熟人数量
    private int systemAcquaintanceCount;
    // 系统默认 熟人人数单价
    private double systemAcquaintancePrice;

    public double getSystemInterestPrice() {
        return systemInterestPrice;
    }

    public void setSystemInterestPrice(double systemInterestPrice) {
        this.systemInterestPrice = systemInterestPrice;
    }

    public double getSystemAcquaintancePrice() {
        return systemAcquaintancePrice;
    }

    public void setSystemAcquaintancePrice(double systemAcquaintancePrice) {
        this.systemAcquaintancePrice = systemAcquaintancePrice;
    }

    public int getSystemInterestCount() {
        return systemInterestCount;
    }

    public void setSystemInterestCount(int systemInterestCount) {
        this.systemInterestCount = systemInterestCount;
    }

    public int getSystemAcquaintanceCount() {
        return systemAcquaintanceCount;
    }

    public void setSystemAcquaintanceCount(int systemAcquaintanceCount) {
        this.systemAcquaintanceCount = systemAcquaintanceCount;
    }

    public int getUserRemainAcquaintanceCount() {
        return userRemainAcquaintanceCount;
    }

    public void setUserRemainAcquaintanceCount(int userRemainAcquaintanceCount) {
        this.userRemainAcquaintanceCount = userRemainAcquaintanceCount;
    }

    public int getUserInterestCount() {
        return userInterestCount;
    }

    public void setUserInterestCount(int userInterestCount) {
        this.userInterestCount = userInterestCount;
    }

    public int getUserAcquaintanceCount() {
        return userAcquaintanceCount;
    }

    public void setUserAcquaintanceCount(int userAcquaintanceCount) {
        this.userAcquaintanceCount = userAcquaintanceCount;
    }

    public int getUserRemainInterestCount() {
        return userRemainInterestCount;
    }

    public void setUserRemainInterestCount(int userRemainInterestCount) {
        this.userRemainInterestCount = userRemainInterestCount;
    }
}
