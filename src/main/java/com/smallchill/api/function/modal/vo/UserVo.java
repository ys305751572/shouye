package com.smallchill.api.function.modal.vo;

/**
 * 用户vo
 * Created by yesong on 2016/11/1 0001.
 */
public class UserVo {

    private Integer id;
    private String username;
    private String city;
    private String domain;
    private String keyWord;
    private String organization;
    private String professional;
    private String validateInfo;
    private String info; // 新结识左上角提示
    private String status; // 用户与好友的状态
    private String actionName1; // 事件1
    private String actionUrl; // 事件1url
    private String actionName2; // 事件2
    private String actionUrl2; // 事件2url
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
                  String info, String status, String actionName1, String actionUrl, String actionName2, String actionUrl2) {
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
        this.actionName1 = actionName1;
        this.actionUrl = actionUrl;
        this.actionName2 = actionName2;
        this.actionUrl2 = actionUrl2;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getActionName1() {
        return actionName1;
    }

    public void setActionName1(String actionName1) {
        this.actionName1 = actionName1;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public String getActionName2() {
        return actionName2;
    }

    public void setActionName2(String actionName2) {
        this.actionName2 = actionName2;
    }

    public String getActionUrl2() {
        return actionUrl2;
    }

    public void setActionUrl2(String actionUrl2) {
        this.actionUrl2 = actionUrl2;
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
