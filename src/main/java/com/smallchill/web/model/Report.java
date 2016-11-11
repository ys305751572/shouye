package com.smallchill.web.model;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 *
 * Created by shilong on 2016/11/11.
 */
@Table(name = "tb_report")
@BindID(name = "id")
@SuppressWarnings("serial")
public class Report extends BaseModel {

    @Column(name = "id")
    private Integer id;

    @Column(name = "rno")
    private String rno = "";

    //类型 1:用户 2:组织 3:内容(活动) 4:内容(非活动) 5:日报 6:杂志
    @Column(name = "type")
    private Integer type;

    @Column(name = "object_id")
    private Integer objectId;

    @Column(name = "object_name")
    private String objectName = "";

    @Column(name = "status")
    private Integer status;

    @Column(name = "admin_id")
    private Integer adminId;

    @Column(name = "report_time")
    private Long reportTime;

    @Column(name = "create_time")
    private Long createTime;

    @Column(name = "update_time")
    private Long updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRno() {
        return rno;
    }

    public void setRno(String rno) {
        this.rno = rno;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Long getReportTime() {
        return reportTime;
    }

    public void setReportTime(Long reportTime) {
        this.reportTime = reportTime;
    }
}
