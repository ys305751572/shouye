package com.smallchill.api.function.modal.vo;

import java.io.Serializable;

/**
 * api group vo
 * Created by yesong on 2016/10/26 0026.
 */
public class Groupvo implements Serializable {

    private Integer id;
    private String name;
    private String avater;
    private String target;
    private Integer memberCount;
    private String city;
    private Integer type;
    private Integer status;
    private String info;
    private String remainingTime;

    private Groupvo() {
    }

    public Groupvo(Integer id, String name, String avater, String target, Integer memberCount, String city) {
        this.id = id;
        this.name = name;
        this.avater = avater;
        this.target = target;
        this.memberCount = memberCount;
        this.city = city;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
