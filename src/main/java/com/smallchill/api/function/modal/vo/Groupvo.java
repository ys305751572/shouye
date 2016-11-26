package com.smallchill.api.function.modal.vo;

import com.smallchill.core.base.model.BaseModel;

import java.io.Serializable;

/**
 * api group vo
 * Created by yesong on 2016/10/26 0026.
 */
public class Groupvo extends BaseModel implements Serializable {

    private Integer id;
    private String name;
    private String avater;
    private String target;
    private Integer memberCount;
    private String provinceCity;
    private Integer type;
    private Integer status;
    private String info;
    private String remainingTime;
    private Integer isjoin;

    private Groupvo() {
    }

    public Groupvo(Integer id, String name, String avater, String target, Integer memberCount, String provinceCity) {
        this.id = id;
        this.name = name;
        this.avater = avater;
        this.target = target;
        this.memberCount = memberCount;
        this.provinceCity = provinceCity;
    }

    public Integer getIsjoin() {
        return isjoin;
    }

    public void setIsjoin(Integer isjoin) {
        this.isjoin = isjoin;
    }

    public String getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(String remainingTime) {
        this.remainingTime = remainingTime;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvater() {
        return avater;
    }

    public void setAvater(String avater) {
        this.avater = avater;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    public String getProvinceCity() {
        return provinceCity;
    }

    public void setProvinceCity(String provinceCity) {
        this.provinceCity = provinceCity;
    }
}
