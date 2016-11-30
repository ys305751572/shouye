package com.smallchill.api.function.modal.vo;

import com.smallchill.api.common.model.BaseVo;
import com.smallchill.api.function.modal.Button;
import com.smallchill.api.function.modal.UserDomain;
import com.smallchill.api.function.modal.UserProfessional;
import com.smallchill.web.model.UserinfoCareer;

import java.io.Serializable;
import java.util.List;

/**
 * 用户vo
 * Created by yesong on 2016/11/1 0001.
 */
public class UserVo extends BaseVo implements Serializable {

    private Integer userId;
    private String username;
    private String mobile;
    private String avater;
    private String provinceCity;
    private String domain;
    private String keyWord;
    private String organization;
    private String professional;
    private String validateInfo;
    private String info; // 新结识左上角提示
    private Integer status; // 用户与好友的状态
    private Integer per; // 用户信息完整度
    private String carrer; // 事业状态
    private String school; // 学校
    private String desc;  // 个人介绍

    private Integer isMember; // 是否会员 1：非会员 2：会员
    private Integer paied; // 是否支付会费 1:未支付 2:已支付
    private Integer type; // 1:朋友 2:熟人

    private List<Button> btnList;
    private List<String> sameKeyList;

    private List<UserProfessional> professionalList;
    private List<UserDomain> userDomainList;
    private List<UserinfoCareer> userinfoCareerList;

    public List<UserProfessional> getProfessionalList() {
        return professionalList;
    }

    public void setProfessionalList(List<UserProfessional> professionalList) {
        this.professionalList = professionalList;
    }

    public List<UserDomain> getUserDomainList() {
        return userDomainList;
    }

    public void setUserDomainList(List<UserDomain> userDomainList) {
        this.userDomainList = userDomainList;
    }

    public List<UserinfoCareer> getUserinfoCareerList() {
        return userinfoCareerList;
    }

    public void setUserinfoCareerList(List<UserinfoCareer> userinfoCareerList) {
        this.userinfoCareerList = userinfoCareerList;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPaied() {
        return paied;
    }

    public void setPaied(Integer paied) {
        this.paied = paied;
    }

    public List<String> getSameKeyList() {
        return sameKeyList;
    }

    public void setSameKeyList(List<String> sameKeyList) {
        this.sameKeyList = sameKeyList;
    }

    public Integer getIsMember() {
        return isMember;
    }

    public void setIsMember(Integer isMember) {
        this.isMember = isMember;
    }

    public Integer getPer() {
        return per;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public List<Button> getBtnList() {
        return btnList;
    }

    public void setBtnList(List<Button> btnList) {
        this.btnList = btnList;
    }

    public String getAvater() {
        return avater;
    }

    public void setAvater(String avater) {
        this.avater = avater;
    }

    public String getCarrer() {
        return carrer;
    }

    public void setCarrer(String carrer) {
        this.carrer = carrer;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public UserVo(Integer userId, String username, String provinceCity, String domain, String keyWord, String organization, String professional, String avater) {
        this.userId = userId;
        this.username = username;
        this.provinceCity = provinceCity;
        this.domain = domain;
        this.keyWord = keyWord;
        this.organization = organization;
        this.professional = professional;
        this.avater = avater;
    }

    public UserVo(Integer userId, String username, String provinceCity, String domain, String keyWord, String organization, String professional, String validateInfo,
                  String info, Integer status) {
        this.userId = userId;
        this.username = username;
        this.provinceCity = provinceCity;
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

    public void setPer(Integer per) {
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProvinceCity() {
        return provinceCity;
    }

    public void setProvinceCity(String provinceCity) {
        this.provinceCity = provinceCity;
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
