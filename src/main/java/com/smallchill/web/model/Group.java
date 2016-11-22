package com.smallchill.web.model;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;
import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;

import javax.persistence.Column;

/**
 * 组织基本信息
 * Generated by yesong.
 * 2016-10-25 17:34:42
 */
@Table(name = "tb_group")
@BindID(name = "id")
@SuppressWarnings("serial")
public class Group extends BaseModel {

    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "avater")
    private String avater;

    @Column(name = "type")
    private Integer type;
    @Column(name = "province")
    private Integer province;
    @Column(name = "city")
    private Integer city;
    @Column(name = "targat")
    private String targat;
    @Column(name = "activity_count")
    private Integer activityCount = 0;
    @Column(name = "member_count")
    private Integer memberCount = 0;
    @Column(name = "admin_count")
    private Integer adminCount = 0;
    @Column(name = "status")
    private Integer status = 0;

    //组织审核状态 0:待审核 1:申请重审 2:审核通过 3:否决
    @Column(name = "audit_status")
    private Integer auditStatus;
    //审核备注
    @Column(name = "audit_comment")
    private String auditComment;
    //审核状态改变的时间
    @Column(name = "update_time")
    private Long throughTime;
    //创建时间
    @Column(name = "create_time")
    private Long createTime;

    @Column(name = "title1")
    private String title1;
    @Column(name = "content1")
    private String content1;
    @Column(name = "is_open1")
    private Integer isOpen1;
    @Column(name = "title2")
    private String title2;
    @Column(name = "content2")
    private String content2;
    @Column(name = "is_open2")
    private Integer isOpen2;
    @Column(name = "title3")
    private String title3;
    @Column(name = "content3")
    private String content3;
    @Column(name = "is_open3")
    private Integer isOpen3;

    //权限类型: 1:公开组织 2:隐藏组织
    @Column(name = "permissions_type")
    private Integer permissionsType;
    //能否申请加入: 1:开放 2:关闭
    @Column(name = "is_join")
    private Integer isJoin;
    //性别限制
    @Column(name = "sex_limit")
    private Integer sexLimit;
    //行业限制
    @Column(name = "industry_limit")
    private Integer industryLimit;
    //领域限制
    @Column(name = "domain_limit")
    private Integer domainLimit;
    //省限制
    @Column(name = "province_limit")
    private Integer provinceLimit;
    //市限制
    @Column(name = "city_limit")
    private Integer cityLimit;
    //职业限制
    @Column(name = "professional_limit")
    private Integer professionalLimit;
    //专业限制
    @Column(name = "zy_limit")
    private Integer zyLimit;
    // 组织服务电话
    @Column(name = "telphone")
    private String telphone;

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public Integer getIsOpen1() {
        return isOpen1;
    }

    public void setIsOpen1(Integer isOpen1) {
        this.isOpen1 = isOpen1;
    }

    public Integer getIsOpen2() {
        return isOpen2;
    }

    public void setIsOpen2(Integer isOpen2) {
        this.isOpen2 = isOpen2;
    }

    public Integer getIsOpen3() {
        return isOpen3;
    }

    public void setIsOpen3(Integer isOpen3) {
        this.isOpen3 = isOpen3;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getContent1() {
        return content1;
    }

    public void setContent1(String content1) {
        this.content1 = content1;
    }

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
    }

    public String getContent2() {
        return content2;
    }

    public void setContent2(String content2) {
        this.content2 = content2;
    }

    public String getTitle3() {
        return title3;
    }

    public void setTitle3(String title3) {
        this.title3 = title3;
    }

    public String getContent3() {
        return content3;
    }

    public void setContent3(String content3) {
        this.content3 = content3;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    @AutoID
    @SeqID(name = "SEQ_DEMO")
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


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public String getTargat() {
        return targat;
    }

    public void setTargat(String targat) {
        this.targat = targat;
    }

    public Integer getActivityCount() {
        return activityCount;
    }

    public void setActivityCount(Integer activityCount) {
        this.activityCount = activityCount;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    public Integer getAdminCount() {
        return adminCount;
    }

    public void setAdminCount(Integer adminCount) {
        this.adminCount = adminCount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditComment() {
        return auditComment;
    }

    public void setAuditComment(String auditComment) {
        this.auditComment = auditComment;
    }

    public Long getThroughTime() {
        return throughTime;
    }

    public void setThroughTime(Long throughTime) {
        this.throughTime = throughTime;
    }

    public Integer getPermissionsType() {
        return permissionsType;
    }

    public void setPermissionsType(Integer permissionsType) {
        this.permissionsType = permissionsType;
    }

    public Integer getIsJoin() {
        return isJoin;
    }

    public void setIsJoin(Integer isJoin) {
        this.isJoin = isJoin;
    }

    public Integer getSexLimit() {
        return sexLimit;
    }

    public void setSexLimit(Integer sexLimit) {
        this.sexLimit = sexLimit;
    }

    public Integer getIndustryLimit() {
        return industryLimit;
    }

    public void setIndustryLimit(Integer industryLimit) {
        this.industryLimit = industryLimit;
    }

    public Integer getDomainLimit() {
        return domainLimit;
    }

    public void setDomainLimit(Integer domainLimit) {
        this.domainLimit = domainLimit;
    }

    public Integer getProvinceLimit() {
        return provinceLimit;
    }

    public void setProvinceLimit(Integer provinceLimit) {
        this.provinceLimit = provinceLimit;
    }

    public Integer getCityLimit() {
        return cityLimit;
    }

    public void setCityLimit(Integer cityLimit) {
        this.cityLimit = cityLimit;
    }

    public Integer getProfessionalLimit() {
        return professionalLimit;
    }

    public void setProfessionalLimit(Integer professionalLimit) {
        this.professionalLimit = professionalLimit;
    }

    public Integer getZyLimit() {
        return zyLimit;
    }

    public void setZyLimit(Integer zyLimit) {
        this.zyLimit = zyLimit;
    }

}
