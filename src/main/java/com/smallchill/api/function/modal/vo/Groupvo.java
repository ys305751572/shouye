package com.smallchill.api.function.modal.vo;

import java.io.Serializable;

/**
 * api group vo
 * Created by yesong on 2016/10/26 0026.
 */
public class Groupvo implements Serializable{

    private Integer id;
    private String name;
    private String avater;
    private String target;
    private Integer members;
    private String city;

    private Groupvo() {}

    public Groupvo(Integer id, String name, String avater, String target, Integer members, String city) {
        this.id = id;
        this.name = name;
        this.avater = avater;
        this.target = target;
        this.members = members;
        this.city = city;
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

    public Integer getMembers() {
        return members;
    }

    public void setMembers(Integer members) {
        this.members = members;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
