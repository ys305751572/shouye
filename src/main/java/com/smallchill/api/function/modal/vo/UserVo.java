package com.smallchill.api.function.modal.vo;

import com.smallchill.api.common.model.BaseVo;
import com.smallchill.api.function.modal.Button;
import com.smallchill.api.function.modal.UserDomain;
import com.smallchill.api.function.modal.UserProfessional;
import com.smallchill.core.toolbox.Record;
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
    private Integer province;
    private Integer city;
    private String provinceCity;
    private String domain;
    private String keyWord;
    private Integer orgIsOpen;
    private Integer orgType;
    private String organization;
    private String professional;
    private String validateInfo;
    private String info; // 新结识左上角提示
    private Integer status; // 用户与好友的状态
    private Integer per; // 用户信息完整度
    private String carrer; // 事业状态
    private String school; // 学校
    private String desc;  // 个人介绍
    private Integer productType;
    private String productServiceName;

    private Integer isMember; // 是否会员 1：非会员 2：会员
    private Integer paied; // 是否支付会费 1:未支付 2:已支付
    private Integer type; // 0:未结识 1:朋友 2:熟人

    private List<Button> btnList;
    private List<String> sameKeyList;

    private List<UserProfessional> professionalList;
    private List<UserDomain> userDomainList;
    private List<Record> userinfoCareerList;

    private UserExtendVo userExtendVo;

    private Integer gender;
    private Integer ageId;
    private String age;

    private String zy;
    private String zy2;
    private String sc;
    private String zl;

    private String qualification;
    private String industryRanking;

    private String shareUrl;

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getIndustryRanking() {
        return industryRanking;
    }

    public void setIndustryRanking(String industryRanking) {
        this.industryRanking = industryRanking;
    }

    public String getZy() {
        return zy;
    }

    public void setZy(String zy) {
        this.zy = zy;
    }

    public String getZy2() {
        return zy2;
    }

    public void setZy2(String zy2) {
        this.zy2 = zy2;
    }

    public String getSc() {
        return sc;
    }

    public void setSc(String sc) {
        this.sc = sc;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public String getProductServiceName() {
        return productServiceName;
    }

    public void setProductServiceName(String productServiceName) {
        this.productServiceName = productServiceName;
    }

    public Integer getOrgIsOpen() {
        return orgIsOpen;
    }

    public void setOrgIsOpen(Integer orgIsOpen) {
        this.orgIsOpen = orgIsOpen;
    }

    public Integer getOrgType() {
        return orgType;
    }

    public void setOrgType(Integer orgType) {
        this.orgType = orgType;
    }

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getAgeId() {
        return ageId;
    }

    public void setAgeId(Integer ageId) {
        this.ageId = ageId;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public UserExtendVo getUserExtendVo() {
        return userExtendVo;
    }

    public void setUserExtendVo(UserExtendVo userExtendVo) {
        this.userExtendVo = userExtendVo;
    }

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

    public List<Record> getUserinfoCareerList() {
        return userinfoCareerList;
    }

    public void setUserinfoCareerList(List<Record> userinfoCareerList) {
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
