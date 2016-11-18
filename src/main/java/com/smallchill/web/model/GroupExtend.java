package com.smallchill.web.model;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;
import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;

import javax.persistence.Column;

/**
 * 组织标签
 * Generated by yesong.
 * 2016-10-25 17:46:14
 */
@Table(name = "tb_group_extend")
@BindID(name = "id")
@SuppressWarnings("serial")
public class GroupExtend extends BaseModel {

    @Column(name = "id")
    private Integer id;
    @Column(name = "group_id")
    private Integer groupId;
    @Column(name = "idcard")
    private String idCard;
    @Column(name = "password")
    private String password;
    @Column(name = "code")
    private String code;
    @Column(name = "code_image")
    private String codeImge;
    @Column(name = "license")
    private String license;
    @Column(name = "license_image")
    private String licenseImage;
    @Column(name = "artificial_person_name")
    private String artificialPersonName;
    @Column(name = "artificial_person_idcard")
    private String artificialPersonIdcard;
    @Column(name = "artificial_person_mobile")
    private String artificialPersonMobile;
    @Column(name = "freeze_status")
    private Integer freezeStatus;
    @Column(name = "freeze_time")
    private Long freezeTime = -1L;
    @Column(name = "why1")
    private String why1;
    @Column(name = "approval_admin_id")
    private Integer approvalAdminId;
    @Column(name = "create_admin_id")
    private Integer createAdminId;
    @Column(name = "cost")
    private Integer cost = 0;
    @Column(name = "cost_status")
    private Integer costStatus;
    //1:年费 2:永久
    @Column(name = "cost_type")
    private Integer costType;

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeImge() {
        return codeImge;
    }

    public void setCodeImge(String codeImge) {
        this.codeImge = codeImge;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getLicenseImage() {
        return licenseImage;
    }

    public void setLicenseImage(String licenseImage) {
        this.licenseImage = licenseImage;
    }

    public String getArtificialPersonName() {
        return artificialPersonName;
    }

    public void setArtificialPersonName(String artificialPersonName) {
        this.artificialPersonName = artificialPersonName;
    }

    public String getArtificialPersonIdcard() {
        return artificialPersonIdcard;
    }

    public void setArtificialPersonIdcard(String artificialPersonIdcard) {
        this.artificialPersonIdcard = artificialPersonIdcard;
    }

    public String getArtificialPersonMobile() {
        return artificialPersonMobile;
    }

    public void setArtificialPersonMobile(String artificialPersonMobile) {
        this.artificialPersonMobile = artificialPersonMobile;
    }

    public Long getFreezeTime() {
        return freezeTime;
    }

    public void setFreezeTime(Long freezeTime) {
        this.freezeTime = freezeTime;
    }

    public String getWhy1() {
        return why1;
    }

    public void setWhy1(String why1) {
        this.why1 = why1;
    }

    public Integer getApprovalAdminId() {
        return approvalAdminId;
    }

    public void setApprovalAdminId(Integer approvalAdminId) {
        this.approvalAdminId = approvalAdminId;
    }

    public Integer getCreateAdminId() {
        return createAdminId;
    }

    public void setCreateAdminId(Integer createAdminId) {
        this.createAdminId = createAdminId;
    }

    @AutoID
    @SeqID(name = "SEQ_DEMO")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCostStatus() {
        return costStatus;
    }

    public void setCostStatus(Integer costStatus) {
        this.costStatus = costStatus;
    }

    public Integer getFreezeStatus() {
        return freezeStatus;
    }

    public void setFreezeStatus(Integer freezeStatus) {
        this.freezeStatus = freezeStatus;
    }

    public Integer getCostType() {
        return costType;
    }

    public void setCostType(Integer costType) {
        this.costType = costType;
    }
}
