package com.smallchill.web.model;

import org.beetl.sql.core.annotatoin.Table;
import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;

import javax.persistence.Column;

/**
 * Generated by Blade.
 * 2016-10-18 09:47:31
 */
@Table(name = "tb_user_info")
@BindID(name = "id")
@SuppressWarnings("serial")
public class UserInfo extends BaseModel {

    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "username")
    private String username = "";

    @Column(name = "avater")
    private String avater = "";

    @Column(name = "mobile")
    private String mobile = "";

    @Column(name = "gender")
    private Integer gender = 1;

    @Column(name = "age")
    private String age = "";

    @Column(name = "birthday")
    private Long birthday = 0L;

    @Column(name = "age_interval_id")
    private Integer ageIntervalId = 0;

    @Column(name = "province_id")
    private Integer provinceId;

    @Column(name = "city_id")
    private Integer cityId;

    @Column(name = "province_city")
    private String provinceCity;

    @Column(name = "school")
    private String school;

    @Column(name = "domain")
    private String domain;

    @Column(name = "professional")
    private String professional;

    @Column(name = "professional_level")
    private String professionalLevel;

    @Column(name = "product_type")
    private Integer productType;

    @Column(name = "product_service_name")
    private String productService;

    @Column(name = "org_type")
    private Integer orgType;

    @Column(name = "organization")
    private String organization;

    @Column(name = "career")
    private String career;

    /**
     * 专业
     */
    @Column(name = "zy")
    private String zy = "";

    @Column(name = "sc")
    private String sc = "";

    @Column(name = "zl")
    private String zl = "";

    /**
     * 资源
     */
    @Column(name = "zy2")
    private String zy2;

    @Column(name = "org_is_open")
    private Integer orgIsOpen = 1;

    @Column(name = "key_word")
    private String keyWord = "";

    @Column(name = "group_status")
    private Integer groupStatus;

    @Column(name = "groups")
    private String groups = "";

    @Column(name = "targat")
    private String targat = "";

    @Column(name = "per")
    private Integer per = 0;

    @Column(name = "desc")
    private String desc = "";

    @Column(name = "industry_ranking")
    private String industryRanking = "";

    @Column(name = "qualification")
    private String qualification = "";

    @Column(name = "create_time")
    private Long createTime;

    public String getProfessionalLevel() {
        return professionalLevel;
    }

    public void setProfessionalLevel(String professionalLevel) {
        this.professionalLevel = professionalLevel;
    }

    public String getIndustryRanking() {
        return industryRanking;
    }

    public void setIndustryRanking(String industryRanking) {
        this.industryRanking = industryRanking;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getPer() {
        return per;
    }

    public void setPer(Integer per) {
        this.per = per;
    }

    public String getAvater() {
        return avater;
    }

    public void setAvater(String avater) {
        this.avater = avater;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public Integer getOrgType() {
        return orgType;
    }

    public void setOrgType(Integer orgType) {
        this.orgType = orgType;
    }

    public String getZy() {
        return zy;
    }

    public void setZy(String zy) {
        this.zy = zy;
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

    public String getZy2() {
        return zy2;
    }

    public void setZy2(String zy2) {
        this.zy2 = zy2;
    }

    public Integer getOrgIsOpen() {
        return orgIsOpen;
    }

    public void setOrgIsOpen(Integer orgIsOpen) {
        this.orgIsOpen = orgIsOpen;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public Integer getAgeIntervalId() {
        return ageIntervalId;
    }

    public void setAgeIntervalId(Integer ageIntervalId) {
        this.ageIntervalId = ageIntervalId;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getProvinceCity() {
        return provinceCity;
    }

    public void setProvinceCity(String provinceCity) {
        this.provinceCity = provinceCity;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    public String getProductService() {
        return productService;
    }

    public void setProductService(String productService) {
        this.productService = productService;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public String getTargat() {
        return targat;
    }

    public void setTargat(String targat) {
        this.targat = targat;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getGroupStatus() {
        return groupStatus;
    }

    public void setGroupStatus(Integer groupStatus) {
        this.groupStatus = groupStatus;
    }
}
