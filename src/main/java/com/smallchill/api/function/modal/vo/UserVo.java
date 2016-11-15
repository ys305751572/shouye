package com.smallchill.api.function.modal.vo;

import java.io.Serializable;

/**
 * 用户vo
 * Created by yesong on 2016/11/1 0001.
 */
public class UserVo implements Serializable{

    private Integer id;
    private String username;
    private String city;
    private String domain;
    private String keyWord;
    private String organization;
    private String professional;
    private String validateInfo;
    private String info; // 新结识左上角提示
    private Integer status; // 用户与好友的状态
    private String per; // 用户信息完整度

    private UserVo() {
    }

    public UserVo(Integer id, String username, String city, String domain, String keyWord, String organization, String professional) {
        this.id = id;
        this.username = username;
        this.city = city;
        this.domain = domain;
        this.keyWord = keyWord;
        this.organization = organization;
        this.professional = professional;
    }

    public UserVo(Integer id, String username, String city, String domain, String keyWord, String organization, String professional,String validateInfo,
                  String info, Integer status) {
        this.id = id;
        this.username = username;
        this.city = city;
        this.domain = domain;
        this.keyWord = keyWord;
        this.organization = organization;
        this.professional = professional;
        this.validateInfo = validateInfo;
        this.info = info;
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPer() {
        return per;
    }

    public void setPer(String per) {
        this.per = per;
    }

    public String getValidateInfo() {
        return validateInfo;
    }

    public void setValidateInfo(String validateInfo) {
        this.validateInfo = validateInfo;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }
}
